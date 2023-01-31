package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.portal.config.BaseDAO;
import com.portal.dao.IPersonDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.PersonMapper;
import com.portal.model.PersonModel;
import com.portal.utils.PortalNumberUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PersonDAO extends BaseDAO implements IPersonDAO {

	@Override
	public List<PersonModel> find(PersonModel model, Pageable pageable) throws AppException {
		try {
			boolean hasFilter = false;

			if (pageable == null) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.fromString("DESC"), "per_id");
			}

			Order order = Order.asc("per_id");
			if (pageable.getSort() != null && pageable.getSort().get().findFirst().isPresent()) {
				order = pageable.getSort().get().findFirst().orElse(order);
			}

			StringBuilder query = new StringBuilder();

			query.append("SELECT * ");
			query.append("FROM person per ");
			query.append("WHERE :hasFilter ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append(" AND per.per_id = :id ");
					params.addValue("id", model.getId());
					hasFilter = true;
				}

				if (model.getName() != null && !model.getName().equals("")) {
					query.append(" AND per.name = :name ");
					params.addValue("name", model.getName());
					hasFilter = true;
				}

				if (model.getJobTitle() != null && !model.getJobTitle().equals("")) {
					query.append(" AND per.job_title = :jobTitle ");
					params.addValue("jobTitle", model.getJobTitle());
					hasFilter = true;
				}

			}

			params.addValue("hasFilter", PortalNumberUtils.booleanToInt(hasFilter));

			query.append("ORDER BY " + order.getProperty() + " " + order.getDirection().name() + " ");
			query.append("LIMIT " + pageable.getPageSize() + " ");
			query.append("OFFSET " + pageable.getPageNumber());

			log.trace("[QUERY] person.find: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().query(query.toString(), params, new PersonMapper());

		} catch (Exception e) {
			log.error("Erro ao buscar as pessoas.", e);
			throw new AppException("Erro ao buscar as pessoas.", e);
		}
	}

	/**
	 * @deprecated Usar a função {@link #find(PersonModel, Pageable)}
	 */
	@Override
	public Optional<PersonModel> find(PersonModel model) throws AppException {
		List<PersonModel> models = this.find(model, null);
		return Optional.ofNullable((models != null ? models.get(0) : null));
	}

	@Override
	public List<PersonModel> search(PersonModel model, Pageable pageable) throws AppException {
		try {
			boolean hasFilter = false;

			if (pageable == null) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.fromString("DESC"), "per_id");
			}

			Order order = Order.asc("per_id");
			if (pageable.getSort() != null && pageable.getSort().get().findFirst().isPresent()) {
				order = pageable.getSort().get().findFirst().orElse(order);
			}

			StringBuilder query = new StringBuilder();

			query.append("SELECT * ");
			query.append("FROM person per ");
			query.append("WHERE :hasFilter ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append(" AND per.per_id = :id ");
					params.addValue("id", model.getId());
					hasFilter = true;
				}

				if (model.getName() != null && !model.getName().equals("")) {
					query.append(" AND per.name LIKE :name ");
					params.addValue("name", this.mapLike(model.getName()));
					hasFilter = true;
				}

				if (model.getJobTitle() != null && !model.getJobTitle().equals("")) {
					query.append(" AND per.job_title LIKE :jobTitle ");
					params.addValue("jobTitle", this.mapLike(model.getJobTitle()));
					hasFilter = true;
				}

			}

			query.append("ORDER BY " + order.getProperty() + " " + order.getDirection().name() + " ");
			query.append("LIMIT " + pageable.getPageSize() + " ");
			query.append("OFFSET " + pageable.getPageNumber());

			params.addValue("hasFilter", PortalNumberUtils.booleanToInt(hasFilter));

			log.trace("[QUERY] person.search: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().query(query.toString(), params, new PersonMapper());

		} catch (Exception e) {
			log.error("Erro ao procurar as pessoas.", e);
			throw new AppException("Erro ao procurar as pessoas.", e);
		}
	}

	/**
	 * @deprecated Usar a função {@link #search(Bank, Pageable)}
	 */
	@Override
	public List<PersonModel> search(PersonModel model) throws AppException {
		return this.search(model, null);
	}

	@Override
	public List<PersonModel> searchForm(String searchText, Pageable pageable) throws AppException {
		try {
			boolean haFilter = true;

			if (pageable == null) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.fromString("DESC"), "per_id");
			}

			Order order = Order.asc("per_id");
			if (pageable.getSort() != null && pageable.getSort().get().findFirst().isPresent()) {
				order = pageable.getSort().get().findFirst().orElse(order);
			}

			StringBuilder query = new StringBuilder();

			query.append("SELECT * FROM person as per WHERE :hasFilter ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (searchText != null) {
				query.append(" and per.name like :text ");
				query.append(" or per.rg like :text ");
				query.append(" or per.cpf like :text ");
				query.append(" or per.cnpj like :text ");
				query.append(" or per.rne like :text ");

				params.addValue("text", this.mapLike(searchText));
			}

			query.append("ORDER BY " + order.getProperty() + " " + order.getDirection().name() + " ");
			query.append("LIMIT " + pageable.getPageSize() + " ");
			query.append("OFFSET " + pageable.getPageNumber());

			params.addValue("hasFilter", PortalNumberUtils.booleanToInt(haFilter));

			log.trace("[QUERY] person.find: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().query(query.toString(), params, new PersonMapper());

		} catch (Exception e) {
			log.error("Erro ao buscar os veículos.", e);
			throw new AppException("Erro ao buscar os veículos.", e);
		}
	}

	@Override
	public List<PersonModel> listAll(Pageable pageable) throws AppException {
		try {
			if (pageable == null) {
				pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.fromString("DESC"), "per_id");
			}

			Order order = Order.asc("per_id");
			if (pageable.getSort() != null && pageable.getSort().get().findFirst().isPresent()) {
				order = pageable.getSort().get().findFirst().orElse(order);
			}

			String query = "SELECT * " + "FROM person per " + "ORDER BY " + order.getProperty() + " "
					+ order.getDirection().name() + " " + "LIMIT " + pageable.getPageSize() + " " + "OFFSET "
					+ pageable.getPageNumber();

			log.trace("[QUERY] person.listAll: {} [PARAMS]: {}", query);

			return this.getJdbcTemplatePortal().query(query, new PersonMapper());

		} catch (Exception e) {
			log.error("Erro ao listar as pessoas.", e);
			throw new AppException("Erro ao listar as pessoas.", e);
		}
	}

	/**
	 * @deprecated Usar a função {@link #listAll(Pageable)}
	 */
	@Override
	public List<PersonModel> list() throws AppException {
		return this.listAll(null);
	}

	@Override
	public Optional<PersonModel> getById(Integer id) throws AppException {
		try {
			String query = "SELECT * " + "FROM person per " + "WHERE per.per_id = :id " + "LIMIT 1";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] person.getById: {} [PARAMS]: {}", query, params.getValues());

			return Optional.ofNullable(this.getJdbcTemplatePortal().queryForObject(query, params, new PersonMapper()));

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();

		} catch (Exception e) {
			log.error("Erro ao consultar uma pessoa.", e);
			throw new AppException("Erro ao consultar uma pessoa.", e);
		}
	}

	@Override
	public Optional<PersonModel> save(PersonModel model) throws AppException {
		try {
			String query = "INSERT INTO person (per_id, name, job_title) " + "VALUES (NULL, :name, :jobTitle)";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("jobTitle", model.getJobTitle());

			log.trace("[QUERY] person.save: {} [PARAMS]: {}", query, params.getValues());

			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.getJdbcTemplatePortal().update(query, params, keyHolder);

			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar a pessoa: {}", model, e);
			throw new AppException("Erro ao tentar salvar a pessoa.", e);
		}
	}

	@Override
	public Optional<PersonModel> update(PersonModel model) throws AppException {
		try {
			String query = "UPDATE person SET name=:name, job_title=:jobTitle " + "WHERE per_id = :perId";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("jobTitle", model.getJobTitle());
			params.addValue("perId", model.getId());

			this.getJdbcTemplatePortal().update(query, params);
			return Optional.ofNullable(model);
		} catch (Exception e) {
			log.error("Erro ao tentar atualizar a pessoa: {}", model, e);
			throw new AppException("Erro ao tentar atualizar a pessoa.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			String query = "DELETE FROM person WHERE per_id = :id";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] person.delete: {} [PARAMS]: {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query, params);

		} catch (Exception e) {
			log.error("Erro ao excluir a pessoa.", e);
			throw new AppException("Erro ao excluir a pessoa.", e);
		}
	}

	@Override
	public boolean hasPartnerRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( "
					+ "SELECT entity_per_id FROM partner WHERE entity_per_id = :perId LIMIT 1 " + ") " + "THEN TRUE "
					+ "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasPartnerRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com parceiro.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com parceiro.", e);
		}
	}

	@Override
	public boolean hasPartnerPersonRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( "
					+ "SELECT per_id FROM partner_person WHERE per_id = :perId LIMIT 1 " + ") " + "THEN TRUE "
					+ "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasPartnerPersonRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com parceiros.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com parceiros.", e);
		}
	}

	@Override
	public boolean hasProposalRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( "
					+ "SELECT per_id FROM proposal_person_client WHERE per_id = :perId LIMIT 1 " + ") " + "THEN TRUE "
					+ "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasProposalRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com proposta.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com proposta.", e);
		}
	}

	@Override
	public boolean hasProposalDetailRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( " + "SELECT seller_per_id, intern_sale_per_id "
					+ "FROM proposal_detail " + "WHERE seller_per_id = :perId "
					+ "OR intern_sale_per_id = :perId LIMIT 1 " + ") " + "THEN TRUE " + "ELSE FALSE "
					+ "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasProposalDetailRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com detalhes da proposta.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com detalhes da proposta.", e);
		}
	}

	@Override
	public boolean hasCommissionRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( "
					+ "SELECT per_id FROM proposal_commission WHERE per_id = :perId LIMIT 1 " + ") " + "THEN TRUE "
					+ "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasCommissionRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com a comissão.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com a comissão.", e);
		}
	}

	@Override
	public boolean hasLeadRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( " + "SELECT client_per_id, seller_per_id " + "FROM lead "
					+ "WHERE client_per_id = :perId " + "OR seller_per_id = :perId " + "LIMIT 1 " + ") " + "THEN TRUE "
					+ "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasLeadRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com lead.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com lead.", e);
		}
	}

	@Override
	public boolean hasHoldingRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( " + "SELECT per_id FROM holding WHERE per_id = :perId LIMIT 1 "
					+ ") " + "THEN TRUE " + "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasHoldingRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com conglomerado.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com conglomerado.", e);
		}
	}

	@Override
	public boolean hasUserRelationship(Integer perId) throws AppException {
		try {
			String query = "SELECT CASE WHEN EXISTS ( " + "SELECT per_id FROM user WHERE per_id = :perId LIMIT 1 "
					+ ") " + "THEN TRUE " + "ELSE FALSE " + "END AS `exists` ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("perId", perId);

			log.trace("[QUERY] person.hasUserRelationship: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().queryForObject(query, params, (rs, rowNum) -> rs.getBoolean("exists"));

		} catch (Exception e) {
			log.error("Erro ao verificar a existência de relacionamento com usuário do sistema.", e);
			throw new AppException("Erro ao verificar a existência de relacionamento com usuário do sistema.", e);
		}
	}
}
