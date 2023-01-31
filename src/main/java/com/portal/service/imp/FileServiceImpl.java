package com.portal.service.imp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.portal.enums.FileType;
import com.portal.exceptions.BusException;
import com.portal.service.IFilesService;

@Service
public class FileServiceImpl implements IFilesService {

	@Value("${files.upload-folder}")
	private String uploadFolder;

	@Value("${files.base-url}")
	private String baseUrl;

	@Override
	public String uploadFile(MultipartFile file, FileType FileType, String filename) throws Exception {

		if (file.isEmpty()) {
			throw new BusException("O envio do arquivo é obrigatório");
		}

		// upload new image to the server
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		Path path = getPhysicalPath(FileType, filename + "." + extension);
		path = renameIfExists(path);
		String photoPath = uploadFile(file, path);
		return photoPath;

	}

	private String uploadFile(MultipartFile file, Path path) throws Exception {

		if (file.isEmpty()) {
			throw new BusException("O envio do arquivo é obrigatório");
		}

		Path directory = path.getParent();
		if (!Files.exists(directory)) {
			new File(directory.toString()).mkdirs();
		}

		byte[] bytes = file.getBytes();
		Files.write(path, bytes);

		return FilenameUtils.getName(path.toString());
	}

	@Override
	public Path renameIfExists(Path path) throws IOException {

		File oldFile = new File(path.toString());
		if (oldFile.exists()) {
			String filename = FilenameUtils.getBaseName(path.toString());
			String extension = FilenameUtils.getExtension(path.toString());
			String uniqueID = RandomStringUtils.randomAlphanumeric(4);
			Path result = Path.of(path.getParent() + File.separator + filename + uniqueID + "." + extension);
			return result;
		}

		return path;
	}

	@Override
	public void deleteIfExists(Path path) throws IOException {

		File oldFile = new File(path.toString());
		if (oldFile.exists()) {
			Files.delete(path);
		}
	}

	@Override
	public Path getPhysicalPath(FileType FileType, String filename) {

		String fullPath = uploadFolder + FileType + File.separator + filename;

		Path pathToReturn = Path.of(fullPath);

		Path directory = pathToReturn.getParent();
		if (!Files.exists(directory)) {
			new File(directory.toString()).mkdirs();
		}
		return pathToReturn;
	}

	@Override
	public URI getVirtualPath(FileType FileType, String filename) throws URISyntaxException {

		URI apiBaseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

		URI relativeUri = null;

		relativeUri = new URI(apiBaseUri + "/" + "file" + "/" + FileType.toString() + "/" + filename);

		return relativeUri;
	}

}
