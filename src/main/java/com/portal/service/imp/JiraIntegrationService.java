package com.portal.service.imp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portal.dto.TaskDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.SearchTaskDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ServiceOrderFactory;
import com.portal.factory.TaskFactory;
import com.portal.integration.jira.IntegrationJIRA;
import com.portal.integration.jira.IntegrationListJIRA;
import com.portal.model.ParameterModel;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageModel;
import com.portal.model.StageMovementModel;
import com.portal.model.TaskModel;
import com.portal.service.IAuditService;
import com.portal.service.IJiraIntegrationService;
import com.portal.service.IParameterService;
import com.portal.service.IStageService;
import com.portal.service.ITaskService;
import com.portal.utils.PortalStaticVariables;

import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Slf4j
public class JiraIntegrationService implements IJiraIntegrationService {

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ServiceOrderService serviceOrderService;

	@Autowired
	private IStageService stageService;

	@Autowired
	private ServiceOrderFactory serviceOrderFactory;

	@Autowired
	private TaskFactory taskFactory;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IParameterService parameterService;

	private Optional<ParameterModel> getJirabaseURI() throws AppException, BusException {
		return this.parameterService.find(new ParameterModel(PortalStaticVariables.JIRA_BASE_URI_PARAMETER));
	}

	private Optional<ParameterModel> getJiraUserName() throws AppException, BusException {
		return this.parameterService.find(new ParameterModel(PortalStaticVariables.JIRA_USERNAME_PARAMETER));
	}

	private Optional<ParameterModel> getJiraPassword() throws AppException, BusException {
		return this.parameterService.find(new ParameterModel(PortalStaticVariables.JIRA_PASSWORD_PARAMETER));
	}

	@Override
	public ServiceOrderModel getServiceOrderByNumberOsOrChassi(ServiceOrderModel serviceOrder, UserProfileDTO requester)
			throws IOException, InterruptedException, NoSuchMessageException, AppException, BusException {

		HttpClient client = HttpClient.newHttpClient();

		// Cria URI
		URI uri = this.generateUriSearch(serviceOrder);

		// Cria HTTP request object
		HttpRequest request = HttpRequest.newBuilder().uri(uri).GET()
				.header("Authorization", this.makeAuthorizationHeader()).header("Content-Type", "application/json")
				.build();

		IntegrationListJIRA integrationResponse;

		try {

			// Envia HTTP request
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

			integrationResponse = objectMapper.readValue(response.body(), IntegrationListJIRA.class);

		} catch (Exception e) {

			throw new AppException(this.messageSource.getMessage("error.jira.integration.notfound", null,
					LocaleContextHolder.getLocale()));
		}

		if (integrationResponse.getTotal() > 0) {

			if (serviceOrder.getId() == null || serviceOrder.getId().equals(0)) {

				ServiceOrderModel toSave = serviceOrderFactory
						.convertFromIntegrationJira(integrationResponse.getIssues().get(0));

				serviceOrder = serviceOrderService.save(toSave, requester).get();

			}

			this.generateTaskByIntegration(integrationResponse, serviceOrder, requester);

		} else {
			throw new AppException(this.messageSource.getMessage("error.jira.integration.notfound", null,
					LocaleContextHolder.getLocale()));
		}

		return serviceOrder;
	}

	private URI generateUriSearch(ServiceOrderModel serviceOrder) throws AppException, BusException {

		Optional<ParameterModel> baseUri = getJirabaseURI();

		String searchUrl;

		if (serviceOrder.getId() != null) {

			searchUrl = "search?jql=project%20%3D%2010128%20AND%20(%27OS%2FPD%5BShort%20text%5D%27%20~%20'" // NUMBER OS
					+ serviceOrder.getNumber() + "'%20OR%20%20'JSM-Chassi%5BShort%20text%5D'%20~%20'" // 6 ULTIMOS
					+ serviceOrder.getNumber() + "'%20OR%20%20'CHASSI%5BShort%20text%5D'%20~%20'" // CHASSI COMPLETO
					+ serviceOrder.getNumber() + "'%20)%20and%20statusCategory%20!%3D%20Done";
		} else {

			searchUrl = "search?jql=project%20%3D%2010128%20AND%20(%27JSM-Chassi%5BShort%20text%5D%27%20~%20%27"
					+ serviceOrder.getNumber() + "%27%20OR%20%20%27CHASSI%5BShort%20text%5D%27%20~%20%27"
					+ serviceOrder.getNumber() + "%27%20)%20and%20statusCategory%20!%3D%20Done";

		}

		return URI.create(baseUri.get().getValue() + searchUrl);

	}

	private void generateTaskByIntegration(IntegrationListJIRA integrationResponse, ServiceOrderModel so,
			UserProfileDTO requester) throws AppException, BusException {

		try {

			List<StageModel> stagesToFinded = stageService.list();

			for (IntegrationJIRA item : integrationResponse.getIssues()) {

				if (item.getFields().getStatus().getName().contains("Concluído"))
					continue;

				Optional<StageModel> stageFinded = stagesToFinded != null
						? stagesToFinded.stream()
								.filter(e -> e.getStatusJiraID().toString()
										.equals(item.getFields().getStatus().getId()))
								.findFirst()
						: Optional.empty();

				if (stageFinded.isPresent()
						&& stageFinded.get().getTask() != item.getFields().getIssuetype().getSubtask()) {

					TaskModel taskToSave = taskFactory.convertFromJiraIntegration(item, stageFinded.get(), so);

					Optional<TaskDTO> taskExistent = taskService.find(
							new SearchTaskDTO(taskToSave.getServiceOrder().getId(), taskToSave.getStage().getId()));

					if (taskExistent.isEmpty()) {
						taskService.save(taskToSave, requester);
					}

				}

			}

		} catch (Exception e) {
			throw new AppException(this.messageSource.getMessage("error.jira.integration.task", null,
					LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public void transitJiraStatus(TaskModel model, StageMovementModel movement, UserProfileDTO userRequester)
			throws NoSuchMessageException, AppException {

		try {
			HttpClient client = HttpClient.newHttpClient();
			Optional<ParameterModel> baseUri = getJirabaseURI();

			// Cria JSON para enviar requisição
			StringBuilder bodyJSON = new StringBuilder();
			bodyJSON.append("{");
			bodyJSON.append("\"transition\":");
			bodyJSON.append(" {").append("\"id\":").append(movement.getJiraID()).append(" }");
			bodyJSON.append("}");

			// Cria URI
			URI uri = URI.create(new StringBuilder().append(baseUri.get().getValue()).append("issue/")
					.append(model.getNumberJira()).append("/transitions").toString());

			// Criar Request
			HttpRequest request = HttpRequest.newBuilder()
					.POST(HttpRequest.BodyPublishers.ofString(bodyJSON.toString())).uri(uri)
					.header("Authorization", this.makeAuthorizationHeader()).header("Content-Type", "application/json") // add
																														// request
																														// header
					.build();

			// Envia HTTP request
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 204) {

				final ObjectNode node = new ObjectMapper().readValue(response.body(), ObjectNode.class);

				final String erros = node.get("errorMessages").toString();

				throw new AppException(erros);
			}

			try {
				this.getServiceOrderByNumberOsOrChassi(model.getServiceOrder(), userRequester);
			} catch (Exception e) {
				log.info("[WARNING]: " + e.getMessage());
			}

		} catch (Exception e) {
			e.getMessage();
			throw new AppException(e.getMessage());
		}

	}

	@Override
	public void transitJiraSpecialStatus(TaskModel model, StageMovementModel movement, UserProfileDTO userRequester,
			String description) throws NoSuchMessageException, AppException {

		try {

			HttpClient client = HttpClient.newHttpClient();
			Optional<ParameterModel> baseUri = getJirabaseURI();

			// Cria JSON para enviar requisição
			StringBuilder bodyJSON = new StringBuilder();
			bodyJSON.append("{");
			bodyJSON.append("\"fields\":");
			bodyJSON.append(" {");
			bodyJSON.append("\"customfield_11102\":").append("[").append(description).append("]");
			bodyJSON.append(" }, ");
			bodyJSON.append("\"transition\":");
			bodyJSON.append(" {").append("\"id\":").append(movement.getJiraID()).append(" }");
			bodyJSON.append("}");

			// Cria URI
			URI uri = URI.create(new StringBuilder().append(baseUri).append("issue/").append(model.getNumberJira())
					.append("/transitions").toString());

			// Criar Request
			HttpRequest request = HttpRequest.newBuilder()
					.POST(HttpRequest.BodyPublishers.ofString(bodyJSON.toString())).uri(uri)
					.header("Authorization", this.makeAuthorizationHeader()).header("Content-Type", "application/json") // add
																														// request
																														// header
					.build();

			// Envia HTTP request
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != 204) {

				final ObjectNode node = new ObjectMapper().readValue(response.body(), ObjectNode.class);

				final String erros = node.get("errorMessages").toString();

				throw new AppException(erros);
			}

			this.getServiceOrderByNumberOsOrChassi(model.getServiceOrder(), userRequester);

		} catch (Exception e) {
			e.getMessage();
			throw new AppException(e.getMessage());
		}

	}

	@Override
	public void sendFileJira(String path, String jiraKEY) throws AppException {

		try {

			Optional<ParameterModel> baseUri = getJirabaseURI();
			Optional<ParameterModel> username = getJiraUserName();
			Optional<ParameterModel> password = getJiraPassword();

			URI uri = URI.create(baseUri.get().getValue() + "/issue/" + jiraKEY + "/attachments");

			kong.unirest.HttpResponse<JsonNode> response = Unirest.post(uri.toString())
					.basicAuth(username.get().getValue(), password.get().getValue())
					.header("Accept", "application/json").header("X-Atlassian-Token", "no-check")
					.field("file", new File(path)).asJson();

			System.out.println(response.getBody());

		} catch (Exception e) {
			e.getMessage();
			throw new AppException(e.getMessage());
		}
	}

	private String makeAuthorizationHeader() throws AppException, BusException {

		Optional<ParameterModel> username = getJiraUserName();
		Optional<ParameterModel> password = getJiraPassword();

		// Concatena Username e Password
		String plainCredentials = username.get().getValue() + ":" + password.get().getValue();
		// Codifica para Base 64
		String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
		// Cria o Header codificado
		String authorizationHeader = "Basic " + base64Credentials;

		return authorizationHeader;
	}

	public void audit(IntegrationJIRA model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {
			this.auditService.save(objectMapper.writeValueAsString(model), operationType, userProfile);
		} catch (JsonProcessingException e) {
			throw new AppException(this.messageSource.getMessage("error.audit", null, LocaleContextHolder.getLocale()));
		}
	}

	public static void main(String[] args) {
		ServiceOrderModel serviceOrder = new ServiceOrderModel();
		serviceOrder.setNumber("245123");
		String teste = "search?jql=project%20%3D%2010128%20AND%20(%27JSM-Chassi%5BShort%20text%5D%27%20~%20%27"
				+ serviceOrder.getNumber() + "%27%20OR%20%20%27CHASSI%5BShort%20text%5D%27%20~%20%27"
				+ serviceOrder.getNumber() + "%27%20)%20and%20statusCategory%20!%3D%20Done";
		System.out.println(teste);
	}

}
