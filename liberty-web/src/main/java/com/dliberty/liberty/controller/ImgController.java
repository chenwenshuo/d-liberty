package com.dliberty.liberty.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dliberty.liberty.entity.DocFile;
import com.dliberty.liberty.sso.service.FileService;

@RestController
@RequestMapping("/img")
public class ImgController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	FileService fileService;

	@PostMapping("/upload")
	public String upload(HttpServletRequest request,HttpServletResponse response) {
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = req.getFile("file");
		if (multipartFile == null) {
			return "请选择需要上传的图片";
		}
		String fileId = fileService.putFile(multipartFile, "dliberty", "images");
		return fileId;
	}

	@GetMapping("/{fileKey}")
	public String get(@PathVariable("fileKey") String fileKey, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("获取图片流");
		DocFile file = fileService.getFile(fileKey);
		if (file != null) {
			try {
				InputStream inputStream = file.getInputStream();
				BufferedImage bi = ImageIO.read(inputStream);
				ImageIO.write(bi, file.getFileType(), response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
