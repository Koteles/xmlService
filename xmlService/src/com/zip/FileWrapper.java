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
import com.http.HttpPOSTClient;
import com.http.RestClient;
import com.model.ZipPath;
import com.qualifiers.Path;
import com.qualifiers.PostUrl;

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
	private String countryCode;
	
	@Inject
	private RestClient postRequest;
	@Inject
	private HttpPOSTClient client;

	@Inject
	@Path
	private static String path;
	
	@Inject
	@PostUrl
	private static String postUrl;
	
	public void handleFileUpload(FileUploadEvent event) {
		// after I upload the zip file, this method gets called and the targetFile is created	
		System.out.println("smth");
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		InputStream initialStream = null;
		try {
			file = event.getFile();
			initialStream = file.getInputstream();

			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);
			File copy = new File(path);
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
		
		ZipPath zip = new ZipPath();
		zip.setPathToFile(path);
		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonResponse = null;

		try {
			jsonResponse = postRequest.sendPost(zip, postUrl);
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
