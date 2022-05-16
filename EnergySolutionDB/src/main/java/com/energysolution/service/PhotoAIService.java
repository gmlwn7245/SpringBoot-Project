package com.energysolution.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoAIService {
	// 고지서 사진 저장
	public HashMap<String, Object> insertPhoto(List<MultipartFile> file);
		
}
