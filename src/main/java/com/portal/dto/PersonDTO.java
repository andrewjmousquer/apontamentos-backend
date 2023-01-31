package com.portal.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.portal.model.Contact;
import com.portal.model.PersonModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String name;

	private String jobTitle;

	@JsonManagedReference
	private List<Contact> contacts;

	public static PersonDTO toDTO(PersonModel person) {
		if (person == null) {
			return null;
		}

		return PersonDTO.builder().id(person.getId()).name(person.getName()).jobTitle(person.getJobTitle())
				.contacts(person.getContacts()).build();
	}

	public static List<PersonDTO> toDTO(List<PersonModel> persons) {
		if (persons == null) {
			return null;
		}
		return persons.stream().map(PersonDTO::toDTO).collect(Collectors.toList());
	}
}
