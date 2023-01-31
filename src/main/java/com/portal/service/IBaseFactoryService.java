package com.portal.service;

import java.util.List;
import java.util.Optional;

import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;

public interface IBaseFactoryService<M, S, D, I> {

	public Optional<D> find(S search) throws AppException, BusException;

	public Optional<D> getById(Integer id) throws AppException, BusException;

	public List<D> list() throws AppException, BusException;

	public List<D> search(S search) throws AppException, BusException;

	public Optional<D> saveOrUpdate(I model, UserProfileDTO userProfile) throws AppException, BusException;

	public Optional<D> save(I model, UserProfileDTO userProfile) throws AppException, BusException;

	public Optional<D> update(I model, UserProfileDTO userProfile) throws AppException, BusException;

	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException;

	public void audit(M model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException;

}