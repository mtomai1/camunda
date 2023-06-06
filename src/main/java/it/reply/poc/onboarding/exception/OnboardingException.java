
package it.reply.poc.onboarding.exception;


public class OnboardingException extends Exception {

	private static final long serialVersionUID = 7342144059194695900L;
	private final String message;
	private final Throwable rootCause;

	public OnboardingException(String message) {

		super(message);
		this.message = message;
		this.rootCause = null;
	}

	public OnboardingException(String message, Throwable rootCause) {

		super(message, rootCause);
		this.message = message;
		this.rootCause = rootCause;
	}

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	public String getExceptionMessage() {

		return message;
	}

	public Throwable getRootCause() {

		return rootCause;
	}

}
