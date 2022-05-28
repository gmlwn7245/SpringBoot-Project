package com.energysolution.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.energysolution.dto.UserDTO;
import com.energysolution.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	TestService testService;
	
	@Autowired
    private JavaMailSender javaMailSender;
    
	@GetMapping("")
	public @ResponseBody String Root() {		
		return "This is Root";
	}
	
	
	// 메일 전송
    @GetMapping("/mail")
    public String index() throws MessagingException, UnsupportedEncodingException {

        String to = "gmlwn7245@naver.com";
        String from = "gmlwn@test.com";
        String subject = "test";

        StringBuilder body = new StringBuilder();
        body.append("<html> <body><h1>임시 비밀번호는 다음과 같습니다. </h1>");
        body.append("<div>테스트 입니다2. <img src=\"cid:flower.jpg\"> </div> </body></html>");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

        mimeMessageHelper.setFrom(from,"EnergySolution_TempPW_manager");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body.toString(), true);

        javaMailSender.send(message);

        return "하이";
    }
	
	// Python과 요청 사용
	@GetMapping("/PythonConn")
	public @ResponseBody String testPythonConn() {
		System.out.println("test====> /PythonConn");
		
		String url = "http://localhost:5000";
		
		//RestTemplate 객체 생성
		RestTemplate template = new RestTemplate();		
		String response = template.getForObject(url+"/getTest", String.class);
		System.out.println(response);
		
		//response = template.getForObject(url+"/getWeather", String.class);
		//System.out.println(response);
		
		return response;
	}
	
	@PostMapping("/TestGetImage")
	public @ResponseBody Map<String, Object> getTestPhoto(@RequestParam("file") MultipartFile file) {
		System.out.println("getImage 요청");
		
		//Convert to Blob
		Map<String, Object> param = new HashMap<String, Object>();
		String fileName = file.getOriginalFilename();
		byte[] bytes;		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			if (file != null) {
				//Convert to Blob
				bytes = file.getBytes();
				try {
					Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
					param.put("file", blob);
					param.put("file_name", fileName);
					param.put("file_size", blob.length());
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				
				//photoAIService.insertPhoto(file);
				System.out.println(file);
				resultMap.put("result", "success");
			} else {
				resultMap.put("result", "fail");
			}
		} catch (Exception e) {
			resultMap.put("result", "fail");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/scanPhotoTest")
	public @ResponseBody String testphototest(@RequestParam("file") MultipartFile file){
		
		System.out.println("====testPhoto====");
		if(file!=null) {
			System.out.println("success");
			return "success";
		}
		System.out.println("fail");
		return "fail";
	}
	
}
