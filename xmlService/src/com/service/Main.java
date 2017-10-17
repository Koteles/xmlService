package com.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.model.Student;

public class Main {

	/**
	 * Create a new Student.
	 * 
	 * @param name
	 * @param age
	 */
	public static void createXml(String name, int age) {

		IDGenerator generator = IDGenerator.getInstance();
		String id = Integer.toString(generator.getId());
		generator.setId(generator.getId() + 1);

		Student stu = new Student();
		stu.setAge(age);
		stu.setName(name);
		stu.setId(id);

		Marshaller jaxbMarshaller = null;

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		generateXMLFile(jaxbMarshaller, id, stu);

	}

	public static void generateXMLFile(Marshaller jaxbMarshaller, String id, Student student) {

		Properties p = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		InputStream input = null;

		try {

			input = loader.getResourceAsStream("config.properties");

			p.load(input);

			String pathToFile = p.getProperty("filenameFormat"); // D:\Students\Files\student-%s-data.xml -> is with
																	// only one backslash

			pathToFile = String.format(pathToFile, id);

			StringTokenizer st = new StringTokenizer(pathToFile, "\\");

			StringBuilder sb = new StringBuilder();

			while (st.hasMoreTokens()) {

				sb.append(st.nextToken() + "\\\\");
			}

			String arr = sb.toString(); // D:\\Students\\Files\\student-%s-data.xml -> I added two backslashes

			String[] strArr = arr.split("\\\\");

			strArr = Arrays.copyOf(strArr, strArr.length - 1);

			String pathToFolder = "";

			for (String s : strArr) {

				pathToFolder += s + "\\"; // D:\\Students\\Files\\

			}

			File folder = new File(pathToFolder);

			if (!(folder.exists() && folder.isDirectory())) { // if the folder does not exists, create it

				folder.mkdir();

			}

			File file = new File(pathToFile + ".xml");
			jaxbMarshaller.marshal(student, file);
			jaxbMarshaller.marshal(student, System.out);

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}