package com.energysolution.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energysolution.mapper.PhotoAIMapper;

@Service
public class PhotoAIServiceImp implements PhotoAIService{
	@Autowired
	PhotoAIMapper photoAIMapper;
	
	@Override
	public void insertPhoto(Map<String, Object> map) {
		System.out.println("=====Photo Service====");
		photoAIMapper.insertPhoto(map);
	}

}
