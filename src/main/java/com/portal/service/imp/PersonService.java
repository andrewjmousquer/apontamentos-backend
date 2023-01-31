package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.dao.IPersonDAO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.Contact;
import com.portal.model.PersonModel;
import com.portal.service.IAuditService;
import com.portal.service.IContactService;
import com.portal.service.IPersonService;
import com.portal.validators.ValidationHelper;
import com.portal.validators.ValidationHelper.OnSave;
import com.portal.validators.ValidationHelper.OnUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PersonService implements IPersonService {

	@Autowired
	private Validator validator;

	@Autowired
	private IPersonDAO dao;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private IContactService contactService;

	@Autowired
	public MessageSource messageSource;

	@Autowired
	private ObjectMapper objectMapper;

	private static final Pageable DEFAULT_PAGINATION = PageRequest.of(0, Integer.MAX_VALUE,
			Sort.Direction.fromString("DESC"), "per_id");

	/**
	 * Lista todos as pessoas. Nesse método carregamos por padrão a lista de tipos
	 * da pessoa, isso por que é um relacionamento obrigatório.
	 * 
	 * @param pageable configuração da paginação e ordenação, se nulo usamos os
	 *                 valores padrões: PageRequest.of( 0, Integer.MAX_VALUE,
	 *                 Sort.Direction.fromString( "DESC" ), "per_id");
	 */
	@Override
	public List<PersonModel> listAll(Pageable pageable) throws AppException, BusException {

		try {

			if (pageable == null) {
				pageable = DEFAULT_PAGINATION;
			}

			return this.dao.listAll(pageable);

		} catch (Exception e) {
			log.error("Erro no processo de listar as pessoas.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.listall",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Lista todos as pessoas.
	 *
	 * Esse método é uma sobrecarga de {@link #listAll(Pageable)}
	 * 
	 * @param pageable configuração da paginação e ordenação, se nulo usamos os
	 *                 valores padrões: PageRequest.of( 0, Integer.MAX_VALUE,
	 *                 Sort.Direction.fromString( "DESC" ), "per_id");
	 */
	@Override
	public List<PersonModel> list() throws AppException, BusException {

		List<PersonModel> listPerson = this.listAll(null);

		listPerson.forEach(person -> {
			try {
				person.setContacts(this.contactService.findByPerson(person.getId()));
			} catch (AppException | BusException e) {
				e.printStackTrace();
			}
		});

		return listPerson;
	}

	/**
	 * Busca produtos que respeitem os dados do objeto. Aqui os campos String são
	 * buscados com o '='
	 * 
	 * Nesse método carregamos por padrão a lista de tipos da pessoa, isso por que é
	 * um relacionamento obrigatório.
	 * 
	 * @param model    objeto produtos para ser buscado
	 * @param pageable configuração da paginação e ordenação, se nulo usamos os
	 *                 valores padrões: PageRequest.of( 0, Integer.MAX_VALUE,
	 *                 Sort.Direction.fromString( "DESC" ), "per_id");
	 */
	@Override
	public List<PersonModel> find(PersonModel model, Pageable pageable) throws AppException, BusException {
		try {
			if (pageable == null) {
				pageable = DEFAULT_PAGINATION;
			}

			return this.dao.find(model, pageable);

		} catch (Exception e) {
			log.error("Erro no processo de buscar as pessoas.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.find",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Busca produtos que respeitem os dados do objeto. Aqui os campos String são
	 * buscados com o '='
	 * 
	 * Esse é método é uma sobrecarga de {@link #search(Person, Pageable)} será
	 * usada a paginação padrão: PageRequest.of( 0, Integer.MAX_VALUE,
	 * Sort.Direction.fromString( "DESC" ), "per_id")
	 * 
	 * @param model objeto produto para ser buscado
	 */
	@Override
	public Optional<PersonModel> find(PersonModel model) throws AppException, BusException {
		List<PersonModel> models = this.find(model, null);
		return Optional.ofNullable((models != null ? models.get(0) : null));
	}

	/**
	 * Busca produtos que respeitem os dados do objeto. Aqui os campos String são
	 * buscados com o 'LIKE'
	 * 
	 * Nesse método carregamos por padrão a lista de tipos da pessoa, isso por que é
	 * um relacionamento obrigatório.
	 * 
	 * @param model    objeto produtos para ser buscado
	 * @param pageable configuração da paginação e ordenação, se nulo usamos os
	 *                 valores padrões: PageRequest.of( 0, Integer.MAX_VALUE,
	 *                 Sort.Direction.fromString( "DESC" ), "per_id");
	 */
	@Override
	public List<PersonModel> search(PersonModel model, Pageable pageable) throws AppException, BusException {
		try {
			if (pageable == null) {
				pageable = DEFAULT_PAGINATION;
			}

			List<PersonModel> persons = this.dao.search(model, pageable);
			fillContact(persons);

			return persons;

		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro no processo de procurar as pessoas.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Busca produtos que respeitem os dados do objeto. Aqui os campos String são
	 * buscados com o 'LIKE'
	 * 
	 * Esse é método é uma sobrecarga de {@link #search(Person, Pageable)} será
	 * usada a paginação padrão: PageRequest.of( 0, Integer.MAX_VALUE,
	 * Sort.Direction.fromString( "DESC" ), "per_id")
	 * 
	 * @param model objeto produto para ser buscado
	 */
	@Override
	public List<PersonModel> search(PersonModel model) throws AppException, BusException {
		return this.search(model, null);
	}

	/**
	 * Busca uma pessoa pelo seu ID
	 * 
	 * Nesse método carregamos por padrão a lista de tipos da pessoa, isso por que é
	 * um relacionamento obrigatório.
	 * 
	 * LEGADO: Para manter a compatibilidade esse método mantém o carregamento de
	 * contato.
	 * 
	 * @param id ID do produto
	 */
	@Override
	public Optional<PersonModel> getById(Integer id) throws AppException, BusException {

		try {

			if (id == null) {
				throw new BusException("ID de busca inválido.");
			}

			Optional<PersonModel> person = dao.getById(id);
			if (person.isPresent()) {
				this.fillContact(person.get());
			}

			return person;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma pessoa pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getbyid",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Método auxiliar que decide com base no ID se a entidade deve ser salva ou
	 * atualizada. Se não tiver ID é save, caso contrário é update.
	 * 
	 * @param model       objeto que deve ser salvo.
	 * @param userProfile dados do usuário logado.
	 */
	@Override
	public Optional<PersonModel> saveOrUpdate(PersonModel model, UserProfileDTO userProfile)
			throws AppException, BusException {
		if (model.getId() != null && model.getId() > 0) {
			return this.update(model, userProfile);
		} else {
			return this.save(model, userProfile);
		}
	}

	/**
	 * Salva um novo objeto.
	 * 
	 * @param model       objeto que deve ser salvo.
	 * @param userProfile dados do usuário logado.
	 */
	@Override
	public Optional<PersonModel> save(PersonModel model, UserProfileDTO userProfile) throws AppException, BusException {
		try {

			this.validateEntity(model, OnSave.class);

			Optional<PersonModel> saved = this.dao.save(model);

			if (!saved.isPresent()) {
				throw new BusException("Não houve retorno do ID da nova pessoa, impedindo de continuar.");
			} else {

				if (model.getContacts() != null) {

					model.getContacts().forEach(contact -> {
						contact.setPerson(saved.get());
						try {
							this.contactService.save(contact, userProfile);
						} catch (AppException | BusException e) {
							e.printStackTrace();
						}
					});
				}
			}

			this.audit((saved.isPresent() ? saved.get() : null), AuditOperationType.PERSON_INSERTED, userProfile);

			return saved;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de cadastro do pessoa: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.save",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Atualiza uma pessoa
	 * 
	 * @param model       objeto produto que deve ser salvo.
	 * @param userProfile dados do usuário logado.
	 */
	@Override
	public Optional<PersonModel> update(PersonModel model, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {
			this.validateEntity(model, OnUpdate.class);

			// PER-U4
			Optional<PersonModel> modelDB = this.getById(model.getId());
			if (!modelDB.isPresent()) {
				throw new BusException("A pessoa a ser atualizada não existe.");
			}

			Optional<PersonModel> saved = this.dao.update(model);

			if (!saved.isPresent()) {
				throw new BusException("Não houve retorno do ID da pessoa atualizada, impedindo de continuar.");
			} else {

				if (model.getContacts() != null) {

					model.getContacts().forEach(contact -> {
						contact.setPerson(saved.get());
						try {
							this.contactService.saveOrUpdate(contact, userProfile);
						} catch (AppException | BusException e) {
							e.printStackTrace();
						}
					});
				}
			}

			this.audit((saved.isPresent() ? saved.get() : null), AuditOperationType.PERSON_UPDATED, userProfile);

			return saved;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de atualização do pessoa: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Efetua a exclusão de uma pessoa
	 * 
	 * @param id          ID do produto
	 * @param userProfile dados do usuário logado.
	 */
	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		try {

			if (id == null) {
				throw new BusException("ID de exclusão inválido.");
			}

			Optional<PersonModel> entityDB = this.getById(id);
			if (!entityDB.isPresent()) {
				throw new BusException("A pessoa a ser excluída não existe.");
			}

			this.validatePartnerRelationship(id);
			this.validatePartnerPersonRelationship(id);
			this.validateProposalRelationship(id);
			this.validateProposalDetailRelationship(id);
			this.validateCommissionRelationship(id);
			this.validateLeadRelationship(id);
			this.validateHoldingRelationship(id);
			this.validateUserRelationship(id);

			// REGRA: PER-D10
			List<Contact> contactsDB = this.contactService.findByPerson(id);
			if (contactsDB != null) {
				for (Contact contact : contactsDB) {
					this.contactService.delete(contact.getId(), userProfile);
				}
			}

			this.dao.delete(id);

			this.audit((entityDB.isPresent() ? entityDB.get() : null), AuditOperationType.PERSON_DELETED, userProfile);

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de exclusão do produto.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.delete",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void audit(PersonModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {
			this.auditService.save(objectMapper.writeValueAsString(model), operationType, userProfile);
		} catch (JsonProcessingException e) {
			throw new AppException(this.messageSource.getMessage("error.audit", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<PersonModel> fillContact(List<PersonModel> persons) throws AppException, BusException {
		if (persons != null && !persons.isEmpty()) {
			persons.forEach(person -> {
				try {
					this.fillContact(person);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		return persons;
	}

	@Override
	public PersonModel fillContact(PersonModel person) throws AppException, BusException {
		if (person != null) {
			person.setContacts(this.contactService.findByPerson(person.getId()));
		}
		return person;
	}

	@Override
	public List<PersonModel> searchForm(String searchText, Pageable pageable) throws AppException {
		try {
			if (pageable == null) {
				pageable = DEFAULT_PAGINATION;
			}

			List<PersonModel> personList = this.dao.searchForm(searchText, pageable);

			personList.forEach(person -> {
				try {
					person.setContacts(this.contactService.findByPerson(person.getId()));
				} catch (AppException | BusException e) {
					e.printStackTrace();
				}
			});

			return personList;

		} catch (Exception e) {
			log.error("Erro no processo de procurar pessoas.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida a entidade como um todo, passando por regras de formatação e
	 * obrigatoriedade
	 *
	 * Regra: PER-I1, PER-I2, PER-U1, PER-U2
	 *
	 * @param model entidade a ser validada
	 * @param group grupo de validação que será usado
	 * @throws AppException
	 * @throws BusException
	 */
	private void validateEntity(PersonModel model, Class<?> group) throws AppException, BusException {
		ValidationHelper.generateException(validator.validate(model, group));
	}

	/**
	 * Valida se existe algum relacionamento com parceiro.
	 * 
	 * REGRA: PER-D1
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validatePartnerRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasPartnerRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com parceiro.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com parceiro.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e parceiro. [partner]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com pessoas do parceiro.
	 * 
	 * REGRA: PER-D2
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validatePartnerPersonRelationship(Integer perId) throws AppException, BusException {

		try {
			if (perId != null) {
				boolean exists = this.dao.hasPartnerPersonRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com pessoas do parceiro.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com pessoas do parceiro.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e parceiro. [partner_person]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	/**
	 * Valida se existe algum relacionamento com a proposta.
	 * 
	 * REGRA: PER-D3
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateProposalRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasProposalRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com a proposta.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com a proposta.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa a proposta. [proposal_person_client]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com detalhes a proposta.
	 * 
	 * REGRA: PER-D4
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateProposalDetailRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasProposalRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com os detalhes da proposta.");
				}

			} else {
				throw new BusException(
						"ID da pessoa inválido para checar o relacionamento com os detalhes da proposta.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e detahes da proposta. [proposal_detail]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com comissões.
	 * 
	 * REGRA: PER-D5
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateCommissionRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasCommissionRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com comissões.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com as comissões.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e detahes da proposta. [proposal_commission]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com lead.
	 * 
	 * REGRA: PER-D6
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateLeadRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasCommissionRelationship(perId);
				if (exists) {
					throw new BusException("Não é possível excluir a pessoa pois existe um relacionamento com o lead.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com o lead.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e lead. [lead]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com conglomerado.
	 * 
	 * REGRA: PER-D7
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateHoldingRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasHoldingRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com o conglomerado.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com o lead.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e conglomerado. [holding]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Valida se existe algum relacionamento com usuário.
	 * 
	 * REGRA: PER-D8
	 * 
	 * @param perId ID da pessoa que deve ser verificada
	 * @throws AppException Em caso de erro sistêmico
	 * @throws BusException Em caso de erro relacionado a regra de negócio
	 */
	private void validateUserRelationship(Integer perId) throws AppException, BusException {
		try {
			if (perId != null) {
				boolean exists = this.dao.hasUserRelationship(perId);
				if (exists) {
					throw new BusException(
							"Não é possível excluir a pessoa pois existe um relacionamento com usuário.");
				}

			} else {
				throw new BusException("ID da pessoa inválido para checar o relacionamento com usuário.");
			}

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao carregar o relacionamento entre pessoa e usuário. [user]", e);
			throw new AppException(this.messageSource.getMessage("error.generic",
					new Object[] { PersonModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

}