package com.converters;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**

 * Custom converter implementation for the "age" field

 *

 */

@FacesConverter("com.converters.AgeConvertion")
public class AgeConversion implements Converter {

	private static final Pattern AGE_PATTERN = Pattern.compile("[0-9]{1,3}");
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.equals("")) {
			return null;
		}
		if (!AGE_PATTERN.matcher(value).matches()) {
            throw new ConverterException(
                    new FacesMessage("Please enter a valid age"));
        }
		
		Integer age = Integer.parseInt(value);
		
		return age;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {

		return value.toString();
	}

}
