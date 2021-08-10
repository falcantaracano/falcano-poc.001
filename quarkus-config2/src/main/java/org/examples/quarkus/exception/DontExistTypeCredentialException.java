package org.examples.quarkus.exception;

public class DontExistTypeCredentialException extends Exception {

	private static final long serialVersionUID = -6485225339254351842L;

	public DontExistTypeCredentialException (String message) {
		super(message);
	}

}
