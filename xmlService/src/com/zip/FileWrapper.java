package com.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.service.HttpPOSTClient;

/**
 * 
 * This class is the Java Bean class
 *
 * 
 */

@ManagedBean
@SessionScoped
public class FileWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	private static UploadedFile file;
	private String content;
	private Map<String, List<List<String>>> myMap;
	private static String pathToFile;
	private static String postUrl;
	@Inject
	private HttpPOSTClient request;

	public void handleFileUpload(FileUploadEvent event) {
		// after I upload the zip file, this method gets called and the targetFile is
		// created
	
		final Properties p = new Properties();
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = null;
		try {
			input = loader.getResourceAsStream("config.properties");
			p.load(input);
			pathToFile = p.getProperty("pathToFile");
			postUrl = p.getProperty("postUrl");
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String filePath = pathToFile.substring(pathToFile.indexOf(":"));
		filePath = filePath.substring(2, filePath.lastIndexOf("}")-1);
		
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		InputStream initialStream = null;
		try {
			file = event.getFile();
			initialStream = file.getInputstream();

			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);
			File copy = new File(filePath);
			// FileUtils.copyInputStreamToFile(initialStream, copy);
			// out.write(buffer);
			OutputStream outStream = new FileOutputStream(copy);
			outStream.write(buffer);
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void processExcelFiles() {

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonResponse = null;
		try {
			jsonResponse = request.sendPOST(pathToFile, postUrl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {			
			myMap = objectMapper.readValue(jsonResponse, LinkedHashMap.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
