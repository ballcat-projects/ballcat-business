package org.ballcat.business.infra.service;

import org.ballcat.file.core.FileClient;
import org.ballcat.oss.OssTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lingting 2021/5/27 11:14
 */
@Component
public class FileService {

	private OssTemplate ossTemplate;

	private final FileClient fileClient;

	public FileService(ApplicationContext context) {
		try {
			ossTemplate = context.getBean(OssTemplate.class);
		}
		catch (Exception ignore) {
			ossTemplate = null;
		}

		// oss 为空或者未配置
		if (ossTemplate == null) {
			fileClient = context.getBean(FileClient.class);
		}
		else {
			fileClient = null;
		}
	}

	public String upload(InputStream stream, String relativePath, Long size) throws IOException {
		if (fileClient != null) {
			return fileClient.upload(stream, relativePath);
		}

		String bucket = ossTemplate.getOssProperties().getBucket();
		ossTemplate.putObject(bucket, relativePath, stream, size);
		return relativePath;
	}

}
