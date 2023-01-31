package com.portal.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.portal.exceptions.AppException;
import com.portal.model.PersonModel;

public interface IPersonDAO extends IBaseDAO<PersonModel>{
	
	public List<PersonModel> listAll( Pageable pageable ) throws AppException;
	
	public List<PersonModel> find( PersonModel model, Pageable pageable ) throws AppException;
	
	public List<PersonModel> search( PersonModel model, Pageable pageable ) throws AppException;
	
	public boolean hasPartnerRelationship( Integer perId ) throws AppException;
	
	public boolean hasPartnerPersonRelationship( Integer perId ) throws AppException;
	
	public boolean hasProposalRelationship( Integer perId ) throws AppException;
	
	public boolean hasProposalDetailRelationship( Integer perId ) throws AppException;
	
	public boolean hasCommissionRelationship( Integer perId ) throws AppException;
	
	public boolean hasLeadRelationship( Integer perId ) throws AppException;
	
	public boolean hasHoldingRelationship( Integer perId ) throws AppException;
	
	public boolean hasUserRelationship( Integer perId ) throws AppException;
	
	/**
	 * Usar a função {@link #listAll(Pageable)}
	 */
	@Deprecated
	public List<PersonModel> list() throws AppException;
	
	/**
	 * Usar a função {@link #find(PersonModel, Pageable)}
	 */
	@Deprecated
	public Optional<PersonModel> find( PersonModel model ) throws AppException; 
	
	/**
	 * Usar a função {@link #search(PersonModel, Pageable)}
	 */
	@Deprecated
	public List<PersonModel> search( PersonModel model ) throws AppException;

    List<PersonModel> searchForm(String searchText, Pageable pageable) throws AppException;
}
