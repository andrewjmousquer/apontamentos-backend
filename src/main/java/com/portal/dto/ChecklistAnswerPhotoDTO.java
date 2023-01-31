package com.portal.dto;

import java.util.Date;

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
public class ChecklistAnswerPhotoDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String url;

	private Date creationDate;

}
