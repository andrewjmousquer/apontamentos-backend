package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.dao.IServiceOrderDAO;
import com.portal.dto.ServiceOrderDTO;
import com.portal.dto.ServiceOrderDashboardDTO;
import com.portal.dto.TaskWithTimeDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ServiceOrderFactory;
import com.portal.model.ServiceOrderModel;
import com.portal.service.IAuditService;
import com.portal.service.IServiceOrderService;
import com.portal.service.ITaskService;
import com.portal.validators.ValidationHelper;
import com.portal.validators.ValidationHelper.OnUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ServiceOrderService implements IServiceOrderService {

	@Autowired
	private Validator validator;

	@Autowired
	private IServiceOrderDAO iServiceOrderDAO;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServiceOrderFactory factory;

	@Autowired
	private ITaskService taskService;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Optional<ServiceOrderModel> find(ServiceOrderModel model) throws AppException, BusException {
		try {
			return this.iServiceOrderDAO.find(model);

		} catch (Exception e) {
			log.error("Erro no processo de buscar ordem de serviço.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.find",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ServiceOrderModel> getById(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			Optional<ServiceOrderModel> serviceOrder = this.iServiceOrderDAO.getById(id);

			return serviceOrder;
		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma ordem de serviço pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ServiceOrderModel> list() throws AppException, BusException {
		List<ServiceOrderModel> serviceOrderList = this.iServiceOrderDAO.list();
		return serviceOrderList;
	}

	@Override
	public List<ServiceOrderModel> search(ServiceOrderModel model) throws AppException, BusException {
		try {
			return this.iServiceOrderDAO.search(model);

		} catch (Exception e) {
			log.error("Erro no processo de procurar ordem de serviço.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ServiceOrderModel> saveOrUpdate(ServiceOrderModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (model.getId() != null && model.getId() > 0) {
			return this.update(model, userProfile);
		} else {
			return this.save(model, userProfile);
		}
	}

	@Override
	public Optional<ServiceOrderModel> save(ServiceOrderModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		try {

			Optional<ServiceOrderModel> saved = this.iServiceOrderDAO.save(model);

			this.audit((saved.isPresent() ? saved.get() : null), AuditOperationType.SERVICE_ORDER_INSERTED,
					userProfile);
			return this.getById(saved.get().getId());

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de cadastro Ordem de Serviço: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.save",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ServiceOrderModel> update(ServiceOrderModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		try {
			this.validateEntity(model, OnUpdate.class);

			Optional<ServiceOrderModel> updated = this.iServiceOrderDAO.update(model);

			if (updated.isPresent()) {
				model.setId(updated.get().getId());

			}

			this.audit((updated.isPresent() ? updated.get() : null), AuditOperationType.SERVICE_ORDER_UPDATED,
					userProfile);

			return this.getById(updated.get().getId());

		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro no processo de atualização: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	private void validateEntity(ServiceOrderModel model, Class<?> group) throws AppException, BusException {
		ValidationHelper.generateException(validator.validate(model, group));

	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de exclusão inválido");

			Optional<ServiceOrderModel> entityDB = this.getById(id);
			if (!entityDB.isPresent()) {
				throw new BusException("Ordem de serviço a ser excluído não existe.");
			}

			this.iServiceOrderDAO.delete(id);

			this.audit((entityDB.isPresent() ? entityDB.get() : null), AuditOperationType.SERVICE_ORDER_DELETED,
					userProfile);

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de exclusão da ordem de serviço.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.delete",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public ServiceOrderDashboardDTO getDashboardById(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			Optional<ServiceOrderModel> serviceOrder = this.iServiceOrderDAO.getById(id);

			ServiceOrderDTO serviceOrderDto = this.factory.convertFromModel(serviceOrder.get());

			List<TaskWithTimeDTO> tasks = this.taskService.getAllByServiceOrderId(id);

			ServiceOrderDashboardDTO toReturn = new ServiceOrderDashboardDTO();

			toReturn.setServiceOrder(serviceOrderDto);

			toReturn.setTasks(tasks);

			return toReturn;
		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma ordem de serviço pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ServiceOrderModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void audit(ServiceOrderModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {
			this.auditService.save(objectMapper.writeValueAsString(model), operationType, userProfile);
		} catch (JsonProcessingException e) {
			throw new AppException(this.messageSource.getMessage("error.audit", null, LocaleContextHolder.getLocale()));
		}

	}

}
