package com.energysolution.controller;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.energysolution.dto.UserDTO;
import com.energysolution.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	TestService testService;

	//인터넷 브라우저 요청은 get 요청만 가능
	@GetMapping("main")
	public String mainController() {
		System.out.println("test====> url : /main");
		return "This is main";
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
	
	@GetMapping("/TestscanPhoto")
	public ModelAndView getTestPhoto() throws IOException {
		ModelAndView mv = new ModelAndView("Image");
		System.out.println("getImage Test 요청");
		
		String imgPath = "C:\\Users\\82109\\Desktop\\zzang.jpg";
		mv.addObject("image",imgPath);
		/*
		File file = new File(imgPath);
		
		Blob blob = null;
	    FileInputStream inputStream = null;
	  
	    try {
	        byte[] byteArray = new byte[(int) file.length()];
	        inputStream = new FileInputStream(file);
	        inputStream.read(byteArray);
	        
	        blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        } catch (Exception e) {
	            inputStream = null;
	        } finally {
	            inputStream = null;
	        }
	    }
		
		HashMap<String, Object> maps = new HashMap<String, Object>();
		maps.put("index", "1");
		maps.put("blob", blob);
		testService.InsertImage(maps);
		
		*/
		return mv;
	}
	
	@GetMapping("get")
	public JSONObject getData(@RequestParam int id) {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("id", id);
		data.put("message", "Get 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	@PostMapping("post")
	public JSONObject postData() {	//MessageConverter의 Jackson Lib가 자동으로 mapping
		System.out.println("Post 요청");
		
		/*
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		resultJSON.put("result", "success");
		resultJSON.put("message", "Post 성공");
		
		data.put("UserId", userDTO.getUserId());
		data.put("Password", userDTO.getPassword());
		data.put("Email", userDTO.getEmail());
		data.put("Name", userDTO.getName());
		
		resultJSON.put("data", data);
		
		System.out.println(userDTO.getUserId());*/
		
		return null;
	}
	
	@PutMapping("put")
	public JSONObject putData() {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("message", "Put 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
	
	@DeleteMapping("delete")
	public JSONObject deleteData() {
		
		JSONObject resultJSON = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("result", "success");
		data.put("message", "Delete 성공");
		
		resultJSON.put("data", data);
		
		return resultJSON;
	}
}
