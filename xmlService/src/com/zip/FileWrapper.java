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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.service.HttpURLConnectionExample;

/**
 * 
 * This class is the Java Bean class
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
	private static UploadedFile file;
	private String content;
	private Map<String, List<List<String>>> myMap;
	private static File targetFile;
	
	@Inject
	private HttpURLConnectionExample request;

	@Inject
	private ExcelReading reading;

	public void handleFileUpload(FileUploadEvent event) {
		
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);

		file = event.getFile();
		targetFile = new File("src/main/resources/targetFile.tmp");
		try {
			FileUtils.copyInputStreamToFile(file.getInputstream(), targetFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void processExcelFiles() {

		myMap = new LinkedHashMap<String, List<List<String>>>();
		
		try {
			ZipFile zipFile = new ZipFile(targetFile);
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
