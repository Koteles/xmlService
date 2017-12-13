package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.ZipPath;
import com.qualifiers.Timeout;


public class HttpPOSTClient {

	@Inject
	@Timeout
	private static int timeout;

	public String sendPost(ZipPath targetFile, String postUrl) throws IOException {

		URIBuilder builder = null;
		URI uri = null;
		try {
			builder = new URIBuilder(postUrl);
			uri = builder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(timeout);
		HttpPost post = new HttpPost(uri);
		
		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).build();
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		 
		String auth = "admin:admin";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("ISO-8859-1")));
		String authHeader = "Basic " + new String(encodedAuth);
		
		post.setHeader("Accept", "application/json");
		post.setHeader("Authorization", authHeader);
		post.setHeader("Content-type", "application/json");
		
		ObjectMapper objectMapper = new ObjectMapper();
	
		String json = objectMapper.writeValueAsString(targetFile);
		StringEntity postingString = new StringEntity(json);	
		
		post.setEntity(postingString);		
		
		HttpResponse response = null;
		try {
		 response = httpClient.execute(post);
		}
		catch(SocketTimeoutException e) {
			System.out.println("Timeout exception");
		}
		
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);}
		
		return result.toString(); 		
		
	}

}