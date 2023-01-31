package com.portal.utils;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class PortalControllerUtils {

	public static final String APPLICATION_IMAGE = "application/image";
	public static final String APPLICATION_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String APPLICATION_XLS = "application/vnd.ms-excel";
	public static final String APPLICATION_ZIP = "application/zip";

	public static ResponseEntity<InputStreamResource> createDownload(byte[] content, String contentType,
			String fileName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentLength(content.length);
		headers.add("attachment", fileName);
		headers.set("attachment", fileName);
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setAccept(Arrays.asList(MediaType.parseMediaType(contentType)));
		headers.setCacheControl(CacheControl.noCache().mustRevalidate().getHeaderValue());
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(new ByteArrayInputStream(content)));
	}

}
