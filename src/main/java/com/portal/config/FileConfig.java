package com.portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

	@Value("${files.upload-folder}")
	private String uploadFolder;

	@Value("${files.virtual-path}")
	private String filesVirtualPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String resourceHandler = String.format("/%s%s/", filesVirtualPath, "/**/**");

		registry.addResourceHandler(resourceHandler).addResourceLocations("file:///" + uploadFolder);

		registry.addResourceHandler("/static/**/**").addResourceLocations("classpath:/static/").setCachePeriod(0);

	}

}
