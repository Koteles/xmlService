package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpPOSTClient {


	public String sendPOST(String targetFile, String postUrl) throws IOException {

		String auth = "admin:admin";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPost post = new HttpPost(postUrl);
		post.setHeader("Accept", "application/json");
		post.setHeader("Authorization", authHeader);
		
		StringEntity postingString = new StringEntity(targetFile);	
		
		post.setEntity(postingString);
		
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);		}
		return result.toString(); 		
		
	}

}