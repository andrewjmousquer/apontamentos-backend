package com.portal.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertOrEditChecklistAnswerDTO {

	private Integer id;

	private Integer question;

	private String comment;

	private Integer task;

	private Integer answer;

	private List<MultipartFile> files;
}
