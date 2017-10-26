package com.service;

import java.io.File;
import java.util.Arrays;

import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.model.Student;

public class Main {

	/**
	 * 
	 * This class creates XML files from student objects
	 *
	 * 
	 */

	@Inject
	private String pathToFile;

	public void createXml(String name, int age) {

		IDGenerator generator = IDGenerator.getInstance();
		String id = Integer.toString(generator.getId());

		Student student = new Student();
		student.setAge(age);
		student.setName(name);
		student.setId(id);

		Marshaller jaxbMarshaller = null;

		try {

			final JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		generateXMLFile(jaxbMarshaller, id, student);

	}

	public void generateXMLFile(Marshaller jaxbMarshaller, String id, Student student) {

		try {
			String path = pathToFile;
			pathToFile = String.format(pathToFile, id);
			StringTokenizer st = new StringTokenizer(pathToFile, "\\");
			StringBuilder sb = new StringBuilder();
			while (st.hasMoreTokens()) {

				sb.append(st.nextToken() + "\\\\");
			}

			String arr = sb.toString();     // D:\\Students\\Files\\student-%s-data.xml -> I added two backslashes
			String[] strArr = arr.split("\\\\");
			strArr = Arrays.copyOf(strArr, strArr.length - 1);
			String pathToFolder = "";
			for (String s : strArr) {
				pathToFolder += s + "\\"; 	 // D:\\Students\\Files\\
			}
			
			System.out.println(pathToFolder);
			File folder = new File(pathToFolder);
			if (!(folder.exists() && folder.isDirectory())) {     // if the folder does not exists, create it
				folder.mkdir();
			}
			System.out.println(pathToFile);
			File file = new File(pathToFile + ".xml");
			jaxbMarshaller.marshal(student, file);
			jaxbMarshaller.marshal(student, System.out);
			pathToFile = path;

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}