package com.abhaya.vehicle.tracking;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Test {
	public static void main(String[] args) throws IOException {
		uploadSingleFile();
	}

	private static void uploadSingleFile() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		ClassPathResource jsaCoverImgFile = new ClassPathResource("jsa-cover.png");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", jsaCoverImgFile);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		String serverUrl = "http://127.0.0.1:8099/v1/driver/addPhoto/4";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		System.out.println("Response code: " + response.getBody());
	}
}
