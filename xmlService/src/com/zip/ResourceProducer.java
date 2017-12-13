package com.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.model.Config;
import com.qualifiers.Path;
import com.qualifiers.PostUrl;
import com.qualifiers.Timeout;

public class ResourceProducer {
	
	private static Properties properties;
	
	static {

		final ClassLoader loader = Thread.currentThread().getContextClassLoader();

		properties = new Properties();

		final InputStream inputStream = loader.getResourceAsStream("config.properties");

		try {
			properties.load(inputStream);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Produces
	@PostUrl
	private String producePostUrl() {
		return properties.getProperty("postUrl");
	}
	
	@Produces
	@Path
	private String producePath() {
		return properties.getProperty("path");
	}
	
	@Produces
	@Timeout
	private int produceTimeout() {
		final String timeout = "timeout";
		final EntityManager manager = Persistence.createEntityManagerFactory(properties.getProperty("database")).createEntityManager();
		final Config config = manager.find(Config.class, timeout);
		return config.getTime();
	}
}
