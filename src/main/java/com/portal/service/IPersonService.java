package com.portal.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.PersonModel;

public interface IPersonService extends IBaseService<PersonModel> {

	public List<PersonModel> listAll(Pageable pageable) throws AppException, BusException;

	public List<PersonModel> find(PersonModel model, Pageable pageable) throws AppException, BusException;

	public List<PersonModel> search(PersonModel model, Pageable pageable) throws AppException, BusException;

	public List<PersonModel> fillContact(List<PersonModel> models) throws AppException, BusException;

	public PersonModel fillContact(PersonModel model) throws AppException, BusException;

    List<PersonModel> searchForm(String searchText, Pageable pageable) throws AppException;
}
