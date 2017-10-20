package com.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**

 * This class is the constraint implementation class for the "age" field

 *

 */

public class StudentAgeValidator implements ConstraintValidator<ValidAge, Integer> {

	@Override
	public void initialize(ValidAge arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Integer age, ConstraintValidatorContext arg1) {

		if(age < 17 || age > 80) {
			return false;
		}
		
		return true;
	}
	

}
