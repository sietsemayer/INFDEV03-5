package org.hro.infdev03_5.util;


import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class UsernameValidator extends CompoundValidator<String> {
	
	private static final long serialVersionUID = 1L;

	public UsernameValidator() {
	
		add(StringValidator.lengthBetween(5, 15));
		add(new PatternValidator("[A-Za-z0-9_-]+"));
	
	}
}