package com.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;

@ManagedBean
@SessionScoped
public class StudentWrapper implements Serializable {

	private HtmlInputText inputComponent = new HtmlInputText();
	private static final long serialVersionUID = 1L;
	private String name;
	private int age = 1;
	
	@PostConstruct
    public void init(){
      inputComponent.setValue(age);
    }
	
	public String addXML() {

		Main.createXml(name, age);
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

	public HtmlInputText getInputComponent() {
		return inputComponent;
	}

	public void setInputComponent(HtmlInputText inputComponent) {
		this.inputComponent = inputComponent;
	}

}
