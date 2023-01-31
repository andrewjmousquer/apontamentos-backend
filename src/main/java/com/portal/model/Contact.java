package com.portal.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.portal.validators.NotNullNotZero;
import com.portal.validators.ValidationHelper.OnSave;
import com.portal.validators.ValidationHelper.OnUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contact{

	@EqualsAndHashCode.Include
	@Null(groups = {OnSave.class})
	@NotNullNotZero(groups = {OnUpdate.class})
	private Integer id;
	
	@Size(max = 150, groups = {OnUpdate.class, OnSave.class})
	private String complement;
	
	@NotBlank(groups = {OnUpdate.class, OnSave.class})
	@Size(max = 150, groups = {OnUpdate.class, OnSave.class})
	private String value;
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	private ClassifierModel type;
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	@JsonBackReference
	private PersonModel person;


}