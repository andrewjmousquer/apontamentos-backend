package com.portal.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portal.enums.FileType;

@Service
public interface IFilesService {

	Path renameIfExists(Path path) throws IOException;

	void deleteIfExists(Path path) throws IOException;

	Path getPhysicalPath(FileType tipoArquivo, String filename);

	URI getVirtualPath(FileType tipoArquivo, String filename) throws URISyntaxException;

	String uploadFile(MultipartFile file, FileType tipoArquivo, String filename) throws Exception;

}