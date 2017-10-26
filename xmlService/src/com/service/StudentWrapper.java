 package com.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.inject.Inject;

import com.validation.ValidAge;

/**

 * This class is the Java Bean class

 *

 */

@ManagedBean
@SessionScoped
public class StudentWrapper implements Serializable {

	private HtmlInputText ageInput = new HtmlInputText();
	private HtmlInputText nameInput = new HtmlInputText();
	private static final long serialVersionUID = 1L;
	private String name = "Student name";
	
	//custom bean validation constraint annotation
	@ValidAge
	private int age = 18;
	
	@Inject
	private Main main;
	
	@PostConstruct
    public void init(){
		ageInput.setValue(age);
		nameInput.setValue(name);
    }
	
	public String addXML() {		
		main.createXml(name, age);
		return "success";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public HtmlInputText getAgeInput() {
		return ageInput;
	}

	public void setAgeInput(HtmlInputText ageInput) {
		this.ageInput = ageInput;
	}

	public HtmlInputText getNameInput() {
		return nameInput;
	}

	public void setNameInput(HtmlInputText nameInput) {
		this.nameInput = nameInput;
	}

}
