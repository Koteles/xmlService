package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import javax.enterprise.inject.Produces;

/**
 * 
 * This class has a method that produces the path to where the XML files will be stored
 * 
 */

public class PathToFileProducer {
	
	
	@Produces
	public String producePath() {
		
		String pathToFile = null;
		
		final Properties p = new Properties();
		
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();

		InputStream input = null;
		
		try {

			input = loader.getResourceAsStream("config.properties");

			p.load(input);

			pathToFile = p.getProperty("filenameFormat"); // D:\Students\Files\student-%s-data.xml -> is with only one backslash

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return pathToFile;
	}
}

