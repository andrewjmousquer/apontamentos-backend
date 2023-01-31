package com.portal.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.portal.enums.DocumentType;
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
public class Document {
	
	@EqualsAndHashCode.Include
	@Null(groups = {OnSave.class})
	@NotNullNotZero(groups = {OnUpdate.class})
	private Integer id;
	
//	@NotBlank(groups = {OnUpdate.class, OnSave.class})
	@Size(max = 150, groups = {OnUpdate.class, OnSave.class})
	private String fileName;

	@NotBlank(groups = {OnUpdate.class, OnSave.class})
	@Size(max = 255, groups = {OnUpdate.class, OnSave.class})
	private String filePath;
	
	@Size(max = 50, groups = {OnUpdate.class, OnSave.class})
	private String contentType;

	@Size(max = 255, groups = {OnUpdate.class, OnSave.class})
	private String description; 
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	private LocalDateTime createDate;
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	private DocumentType type;
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	private UserModel user;
	
	@NotNull(groups = {OnUpdate.class, OnSave.class})
	private MultipartFile fileContent;
}
