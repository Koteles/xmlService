package com.zip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "userData", eager = true)
@SessionScoped
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<String> dataList;
	private UploadedFile file;
	//private List<String> countryCodes;
	public String data = "1";
	private ArrayList<String> countryCodes;
	
	@PostConstruct
	public void post() {
		System.out.println("Mesaj");
	}

	public void getIso() {

		/*List<String> list = new ArrayList<String>();
		list = Arrays.asList(countryCodes);*/
		System.out.println(countryCodes);
	}

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("ceva");
		System.out.println(dataList);
		dataList = new TreeSet<>();
		dataList.add("bob");
		dataList.add("john");
		dataList.add("john");
		dataList.add("john");
		System.out.println(dataList);
	}

	public void stateChangeListener(ValueChangeEvent event) {

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Set<String> getDataList() {
		return dataList;
	}

	public void setDataList(Set<String> dataList) {
		this.dataList = dataList;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public ArrayList<String> getCountryCodes() {
		return countryCodes;
	}

	public void setCountryCodes(ArrayList<String> countryCodes) {
		this.countryCodes = countryCodes;
	}





}
