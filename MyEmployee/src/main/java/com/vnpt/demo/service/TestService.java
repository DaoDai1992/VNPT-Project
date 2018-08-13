package com.vnpt.demo.service;

import java.net.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vnpt.demo.model.Patient;

@Service
public class TestService {
	@Value("${his.service.url}")
	private String hisServiceUrl;
	
	public String getAuthentication(){
		RestTemplate restTemplate = new RestTemplate();
		String uuid="";
		String params[] = {"{?=call prc_login(?2S,?3S)}","BVBDHCM.ADMIN","Test@2018"};
		// create request body
		JSONObject request = new JSONObject();

		
		try {
			request.put("func", "doLogin");
			request.put("uuid", "");
			request.put("params", params);
			// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
			ResponseEntity<String> response = restTemplate.postForEntity( hisServiceUrl, entity, String.class );
			JSONObject jsonObj = new JSONObject(response.getBody().toString());
			JSONObject jsonResult = new JSONObject(jsonObj.getString("result"));
			uuid = jsonResult.getString("UUID");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uuid;
	}
	
	public String getListPatient(){
		Patient pat = new Patient();
		String uuid = getAuthentication();
		StringBuilder urlService = new StringBuilder();
		RestTemplate restTemplate = new RestTemplate();
		
		JSONObject obj = new JSONObject();
		JSONObject jsonOption1 = new JSONObject();
		JSONObject jsonOption2 = new JSONObject();
		JSONObject jsonOption3 = new JSONObject();
		JSONArray array = new JSONArray();
		


		String option[] = {"{?=call prc_login(?2S,?3S)}","BVBDHCM.ADMIN","Test@2018"};
		try {
			jsonOption1.put("name", "[0]");
			jsonOption1.put("value", "2");
			jsonOption2.put("name", "[1]");
			jsonOption2.put("value", "-1");
			jsonOption3.put("name", "[2]");
			jsonOption3.put("value", "-1");
			array.put(jsonOption1);
			array.put(jsonOption2);
			array.put(jsonOption3);
			
			obj.put("func", "ajaxExecuteQueryPaging");
			obj.put("code", "thu@nnc");
			obj.put("uuid", uuid);
			obj.put("params", new String[]{"DMC.03"});
			obj.put("options", array);
			
			
			urlService.append(hisServiceUrl).append("?postData=").append(obj.toString());
			ResponseEntity<String> response = restTemplate.getForEntity(urlService.toString(), String.class );
			JSONObject jsonObj = new JSONObject(response.getBody().toString());

			jsonObj.get("rows");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
		
	}
	
}
