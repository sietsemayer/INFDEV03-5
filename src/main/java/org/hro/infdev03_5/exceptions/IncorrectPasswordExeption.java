package org.hro.infdev03_5.exceptions;

public class IncorrectPasswordExeption extends Exception {
	 public IncorrectPasswordExeption(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public IncorrectPasswordExeption(String message) {
	        super(message);
	    }

}
