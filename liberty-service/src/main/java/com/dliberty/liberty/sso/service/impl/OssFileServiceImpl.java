package com.dliberty.liberty.sso.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.dliberty.liberty.entity.DocFile;
import com.dliberty.liberty.lang.data.StringUtils;
import com.dliberty.liberty.service.DocFileService;
import com.dliberty.liberty.sso.service.FileService;

/**
 * 文件服务
 * @author LG
 *不支持事物
 */
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class OssFileServiceImpl implements FileService {

	private static final Logger logger = LoggerFactory.getLogger(OssFileServiceImpl.class);
	
	@Autowired
	DocFileService docFileService;
	
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss.bucketName}")
	private String bucketName;
	
	@Override
	public String putFile(String local, String module1, String module2) {
		File file = new File(local);
		if (file.exists()) {
			return putFile(file, module1, module2);
		}
		return null;
	}

	@Override
	public String putFile(File file, String module1, String module2) {
		if (!file.exists()) {
			return null;
		}
		try {
			InputStream input = new FileInputStream(file);
			return putFile(input, module1, module2,file.getName());
		} catch(Exception e) {
			logger.info("文件上传发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		return null;
	}
	
	@Override
	public String putFile(MultipartFile file, String module1, String module2) {
		try {
			return putFile(file.getInputStream(), module1, module2,file.getOriginalFilename());
		} catch(Exception e) {
			logger.info("文件上传发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		return null;
	}

	@Override
	public String putFile(InputStream local, String module1, String module2,String fileName) {
		try {
			String key = saveDocFile(module1, module2, fileName);
			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 使用访问OSS。
			ossClient.putObject(bucketName, key, local);
			// 关闭ossClient。
			ossClient.shutdown();
			return key;
		} catch(Exception e) {
			logger.info("文件上传发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		return null;
	}
	
	@Override
	public String putFile(String url, String module1, String module2, String fileName) {
		try {
			String key = saveDocFile(module1, module2, fileName);
			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 使用访问OSS。
			// 上传网络流。
			InputStream inputStream = new URL(url).openStream();
			ossClient.putObject(bucketName, key, inputStream);
			// 关闭ossClient。
			ossClient.shutdown();
			return key;
		} catch(Exception e) {
			logger.info("文件上传发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		return null;
	}
	
	public String saveDocFile(String module1, String module2,String fileName) {
		DocFile file = new DocFile();
		file.setFileName(fileName);
		String key = "doc_file" + System.currentTimeMillis();
		String encodeToString = Base64Utils.encodeToString(key.getBytes());
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		//String path = "dliberty/image/" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + "/"  + module1 + "/" + module2 + "/"+ encodeToString + "." + suffix;
		file.setFileKey(encodeToString+ "." + suffix);
		//file.setFilePath(path);
		file.setFileType(suffix);
		docFileService.save(file);
		return file.getFileKey() ;
	}

	@Override
	public void delFile(String fileKey) {
		try {
			DocFile docFile = docFileService.selectByFileKey(fileKey);
			if (docFile == null) {
				return;
			}
			String objectName = docFile.getFilePath();
			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 删除Object。
			ossClient.deleteObject(bucketName, objectName);
			// 关闭Client。
			ossClient.shutdown();
			docFile.setIsDeleted("1");
			docFileService.update(docFile);
		} catch(Exception e) {
			logger.warn("删除oss文件发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		
	}

	@Override
	public DocFile getFile(String fileKey) {
		if (StringUtils.isEmpty(fileKey)) {
			return null;
		}
		try {
			DocFile docFile = docFileService.selectByFileKey(fileKey);
			if (docFile == null) {
				return null;
			}
			String objectName = docFile.getFilePath();
			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			//ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
			OSSObject ossObject = ossClient.getObject(bucketName, objectName);
			// 读取文件内容。
			InputStream inputStream = ossObject.getObjectContent();
			docFile.setInputStream(inputStream);
			// 关闭Client。
			ossClient.shutdown();
			return docFile;
		} catch(Exception e) {
			logger.warn("oss文件下载发生异常{}",e.getMessage());
			e.getStackTrace();
		}
		
		return null;
	}


}
