package org.hro.infdev03_5.exceptions;

public class MaximumCharactersExeption extends Exception {
	public MaximumCharactersExeption(String message, Throwable cause) {
        super(message, cause);
    }
    public MaximumCharactersExeption(String message) {
        super(message);
    }


}
