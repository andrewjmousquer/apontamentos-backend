package com.portal.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.portal.dto.PersonDTO;
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
public class PersonModel {

	@EqualsAndHashCode.Include
	@NotNull
	private Integer id;

	@Size(max = 255, groups = { OnUpdate.class, OnSave.class })
	@NotBlank(groups = { OnUpdate.class, OnSave.class })
	private String name;

	@Size(max = 255, groups = { OnUpdate.class, OnSave.class })
	private String jobTitle;

	@JsonManagedReference
	private List<Contact> contacts;

	public PersonModel(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public PersonModel(String name) {
		this.name = name;
	}

	public PersonModel(Integer id, String name, String jobTitle) {
		this.id = id;
		this.name = name;
		this.jobTitle = jobTitle;

	}

	public static PersonModel toEntity(PersonDTO personDTO) {
		if (personDTO == null) {
			return null;
		}

		return PersonModel.builder().id(personDTO.getId()).name(personDTO.getName()).jobTitle(personDTO.getJobTitle())
				.contacts(personDTO.getContacts()).build();
	}

	public static List<PersonModel> toEntity(List<PersonDTO> list) {
		if (list == null) {
			return null;
		}
		return list.stream().map(PersonModel::toEntity).collect(Collectors.toList());
	}

}