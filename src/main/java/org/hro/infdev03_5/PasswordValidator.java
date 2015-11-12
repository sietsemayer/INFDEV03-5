package org.hro.infdev03_5;


import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class PasswordValidator extends CompoundValidator<String> {
	
	private static final long serialVersionUID = 1L;

	public PasswordValidator() {
	
		add(StringValidator.lengthBetween(5, 15));
		add(new PatternValidator("[A-Za-z0-9_-]+"));
	
	}
}