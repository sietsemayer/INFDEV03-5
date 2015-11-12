package org.hro.infdev03_5.exceptions;

public class EmptySQLResultExeption extends Exception {
	public EmptySQLResultExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptySQLResultExeption(String message) {
		super(message);
	}

}
