package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Main {
 
    /**
     * Create a new Student.
     * 
     * @param name
     * @param age
     */
    public static void createXml(String name, int age, int id) {
    	
    	Student stu = new Student();
    	stu.setAge(age);
    	stu.setName(name);
    	stu.setId(id);
    	
    	XStream xstream = new XStream(new DomDriver());
    	xstream.alias("students", Student.class);
    	String xml = xstream.toXML(stu);
    	System.out.println(xml);
    	generateXMLFile(xml, id);
    }
    
    public static void generateXMLFile(String xml, int id)  {
    	
    	Properties p = new Properties();   	
    	PrintWriter print = null;
    	
    	InputStream input = null;
    	try {
    		input = new FileInputStream("D:\\dataConfig.properties");
    		p.load(input);
    		String path = p.getProperty("path");
    		String pathToFiles = p.getProperty("pathToFiles");
    		
    		File folder = new File(pathToFiles);
    		if(!(folder.exists() && folder.isDirectory())) { 	//if the folder does not exists, create it
    			
    			folder.mkdir();
    			
    		}
    		
    		File file = new File(path+id+".xml");
    		
    		print = new PrintWriter(file);
    		
    		print.write(xml);
    		print.flush();
    		print.close();
    	}
    
    	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		print.close();
    	}
    } 
    
    
}