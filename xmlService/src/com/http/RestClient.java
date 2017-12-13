package com.http;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.ZipPath;
import com.qualifiers.Timeout;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class RestClient {

	@Inject
	@Timeout
	private static int timeout;

	public String sendPost(ZipPath targetFile, String postUrl) {

		ClientConfig config = new DefaultClientConfig();

		config.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, timeout);
		config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, timeout);

		Client client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter("admin", "admin"));

		WebResource webResource = client.resource(postUrl);

		ObjectMapper objectMapper = new ObjectMapper();

		String json = null;
		try {
			json = objectMapper.writeValueAsString(targetFile);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientResponse response = null;
			response = webResource.header("Accept", "application/json")
					.header("Content-type", "application/json").post(ClientResponse.class, json);	
	
	System.out.println("Response Code : " + response.getStatus());
	return response.getEntity(String.class);
	}
}
