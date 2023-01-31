package com.portal.security;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.portal.dto.UserAndOfficeDTO;
import com.portal.dto.UserLoginDTO;
import com.portal.dto.UserQRAuthDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.UserFactory;
import com.portal.factory.UserQRAuthFactory;
import com.portal.model.UserModel;
import com.portal.service.IUserService;
import com.portal.utils.PortalJwtTokenUtil;
import com.portal.utils.PortalStaticVariables;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Security controller")
public class AuthenticationController {

	@Autowired
	public MessageSource messageSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PortalJwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private IUserService userService;

	@Autowired
	private UserQRAuthFactory qrFactory;

	@Autowired
	private UserFactory userFactory;

	/**
	 * Gera e retorna um novo token JWT.
	 *
	 * @param authenticationDto
	 * @param result
	 * @return ResponseEntity<Response<UserLoginDTO>>
	 */
	@PostMapping
	public ResponseEntity<UserModel> auth(@Valid @RequestBody UserAuthDTO authenticationDto, BindingResult result)
			throws AuthenticationException, AppException, BusException {
		try {
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
					authenticationDto.getUsername(), authenticationDto.getPassword());
			Authentication authentication = authenticationManager.authenticate(authReq);

			if (authentication.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
				UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getUsername());

				if (userDetails != null) {
					String token = jwtTokenUtil.obterToken(userDetails);
					Optional<UserModel> userModel = this.userService
							.findLogin(new UserModel(userDetails.getUsername()));

					if (userModel.isPresent()) {
						this.userService.updatePasswordErrorCount(userModel.get());
						userModel.get().setPassword(null);
						userModel.get().setToken(token);

						return ResponseEntity.ok(userModel.get());
					}
				}
			}

		} catch (Exception e) {
			if (e.getMessage() != null) {
				if (e.getMessage().equals("Bad credentials")) {
					Optional<UserModel> user = this.userService.updateLoginData(authenticationDto.getUsername());
					if (user.isPresent()) {
						if (user.get().getBlocked()) {
							throw new BusException(messageSource.getMessage("error.authentication.errorCount", null,
									LocaleContextHolder.getLocale()));
						}
					}
					throw new BusException(messageSource.getMessage("error.authentication.invalid", null,
							LocaleContextHolder.getLocale()));

				} else if (e.getMessage().equals("User account is locked")) {
					throw new BusException(messageSource.getMessage("error.authentication.blocked", null,
							LocaleContextHolder.getLocale()));

				} else if (e.getMessage().equals("User is disabled")) {
					throw new BusException(messageSource.getMessage("error.authentication.notEnabled", null,
							LocaleContextHolder.getLocale()));

				} else {
					throw new BusException(messageSource.getMessage("error.authentication.genericError", null,
							LocaleContextHolder.getLocale()));
				}
			} else {
				throw new BusException(messageSource.getMessage("error.authentication.genericError", null,
						LocaleContextHolder.getLocale()));
			}
		}

		return null;
	}

	/**
	 * Gera e retorna um novo token JWT.
	 *
	 * @param qrCode
	 * @param result
	 * @return ResponseEntity<Response<UserModel>>
	 */
	@PostMapping(value = "/qr-code")
	public ResponseEntity<UserModel> authQrCode(@Valid @RequestBody UserQRAuthDTO qrCode, BindingResult result)
			throws AuthenticationException, AppException, BusException {
		try {

			UserDetails userDetails = userService.loadUserByQrCode(qrFactory.convertFromInsertDto(qrCode));

			if (userDetails != null) {
				String token = jwtTokenUtil.obterToken(userDetails);
				Optional<UserModel> userModel = this.userService.findLogin(new UserModel(userDetails.getUsername()));

				if (userModel.isPresent()) {
					this.userService.updatePasswordErrorCount(userModel.get());
					userModel.get().setPassword(null);
					userModel.get().setToken(token);

					return ResponseEntity.ok(userModel.get());
				}
			}

		} catch (Exception e) {
			if (e.getMessage() != null) {
				if (e.getMessage().equals("Bad credentials")) {
					Optional<UserModel> user = this.userService
							.updateLoginDataQR(qrFactory.convertFromInsertDto(qrCode));
					if (user.isPresent()) {
						if (user.get().getBlocked()) {
							throw new BusException(messageSource.getMessage("error.authentication.errorCount", null,
									LocaleContextHolder.getLocale()));
						}
					}
					throw new BusException(messageSource.getMessage("error.authentication.invalid", null,
							LocaleContextHolder.getLocale()));

				} else if (e.getMessage().equals("User account is locked")) {
					throw new BusException(messageSource.getMessage("error.authentication.blocked", null,
							LocaleContextHolder.getLocale()));

				} else if (e.getMessage().equals("User is disabled")) {
					throw new BusException(messageSource.getMessage("error.authentication.notEnabled", null,
							LocaleContextHolder.getLocale()));

				} else {
					throw new BusException(messageSource.getMessage("error.authentication.genericError", null,
							LocaleContextHolder.getLocale()));
				}
			} else {
				throw new BusException(messageSource.getMessage("error.authentication.genericError", null,
						LocaleContextHolder.getLocale()));
			}
		}

		return null;
	}

	@GetMapping(path = "/{qrCode}")
	public @ResponseBody ResponseEntity<UserAndOfficeDTO> getUserAndOffice(
			@PathVariable(name = "qrCode", required = true) String qrCode)
			throws AuthenticationException, AppException, BusException {

		Optional<UserModel> userAndOffice = this.userService.getUserAndOffice(qrCode);

		if (userAndOffice.isPresent())
			return ResponseEntity.ok(this.userFactory.convertToUserAndOfficeFromModel(userAndOffice.get()));

		else
			throw new AppException(this.messageSource.getMessage("error.authentication.usernotfound", null,
					LocaleContextHolder.getLocale()));
	}

	/**
	 * Gera um novo token com uma nova data de expiração.
	 *
	 * @param request
	 * @return ResponseEntity<Response<UserLoginDTO>>
	 * @throws BusException
	 * @throws NoSuchMessageException
	 */
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserLoginDTO> gerarRefreshTokenJwt(HttpServletRequest request)
			throws NoSuchMessageException, BusException {
		Optional<String> token = Optional.ofNullable(request.getHeader(PortalStaticVariables.TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(PortalStaticVariables.BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}
		if (!token.isPresent()) {
			throw new BusException(messageSource.getMessage("error.authentication.tokennotpreset", null,
					LocaleContextHolder.getLocale()));
		} else if (!jwtTokenUtil.tokenValido(token.get())) {
			throw new BusException(messageSource.getMessage("error.authentication.tokenInvalid", null,
					LocaleContextHolder.getLocale()));
		}

		String refreshedToken = jwtTokenUtil.refreshToken(token.get());
		return ResponseEntity.ok(new UserLoginDTO(refreshedToken, null));
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		// Criar o client
		var client = HttpClient.newHttpClient();

		// Criar Request
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://sbmtech.atlassian.net/rest/api/3/issue/{issueIdOrKey}")).headers("Accept",
						"application/json", "Authorization", getBasicAuthenticationHeader("postman", "password"))
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		final ObjectNode node = new ObjectMapper().readValue(response.body(), ObjectNode.class);

		System.out.println(response.body());
		System.out.println(node);
	}

	private static final String getBasicAuthenticationHeader(String username, String password) {
		String valueToEncode = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}

}
