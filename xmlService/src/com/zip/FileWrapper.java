package com.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 * 
 * This class is the Java Bean class
 *
 * 
 * 
 */

@ManagedBean
@SessionScoped
public class FileWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadedFile file;
	private String content;
	private Map<String, List<List<String>>> myMap;

	@Inject
	private ExcelReading reading;

	public void processXLSX() {

		myMap = new LinkedHashMap<String, List<List<String>>>();

		File f = new File(file.getFileName());
		try {
			ZipFile zipFile = new ZipFile(f);
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
			
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
			
				InputStream stream = zipFile.getInputStream(zipEntry);
				String fileName = zipEntry.getName();
				myMap.put(fileName, reading.xlsx(stream, fileName));
			

			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void process() throws IOException {
		File f = new File(file.getFileName());
	
		String names = "";
		try {
			ZipFile zipFile = new ZipFile(f);
			Enumeration<?> enu = zipFile.entries();
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();
				InputStream stream = zipFile.getInputStream(zipEntry);

				String result = IOUtils.toString(stream, StandardCharsets.UTF_8.name());

				String name = zipEntry.getName();

				names += name + System.lineSeparator() + result + System.lineSeparator();

			}
			zipFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		setContent(names);
		System.out.println(names);
	}

	public Map<String, List<List<String>>> getMyMap() {
		return myMap;
	}

	public void setMyMap(Map<String, List<List<String>>> myMap) {
		this.myMap = myMap;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
