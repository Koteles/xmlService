package com.validation;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**

 * Validator class for the "age" field of the managed bean. This class is not used for the moment, bean validation is used instead

 *

 */

@FacesValidator("com.validation.AgeValidator")
public class AgeValidator implements Validator {

	private static final Pattern AGE_PATTERN = Pattern.compile("[0-9]{1,3}");

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String age = value.toString();
		checkPattern(age);
	}

	private void checkPattern(String age) {

		if (AGE_PATTERN.matcher(age).matches()) {
			System.out.println(age);
			checkIfValid(age);
		}
		
		else if (!AGE_PATTERN.matcher(age).matches()) {	//it will enter here if it is a negative number

			throw new ValidatorException(new FacesMessage("A student has to be at least 18 and at most 80"));
			
		}
	}

	private void checkIfValid(String age) {

		int studentAge = Integer.parseInt(age);
		if (studentAge < 18 || studentAge > 80) {
			throw new ValidatorException(new FacesMessage("A student has to be at least 18 and at most 80"));

		}
	}

}
