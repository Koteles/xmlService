package com.validation;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**

 * Validator class for the "name" field of the managed bean

 *

 */

@FacesValidator("com.validation.NameValidator")
public class NameValidator implements Validator {

	private static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z ]{2,12}");
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		String name = (String) value;
		if(name == null || name.equals("")) {
			throw new ValidatorException(new FacesMessage("Please enter a valid name for the student"));
		} else {
			checkPattern(name);
		}
		
	}
	private void checkPattern(String name) {
		if(!NAME_PATTERN.matcher(name).matches()) {
			throw new ValidatorException(new FacesMessage("Please enter a valid name for the student"));
		}
	}

	
}
