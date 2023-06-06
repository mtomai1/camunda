
package it.reply.poc.onboarding.exception;


public class OnboardingException500 extends OnboardingException {

	private static final long serialVersionUID = 7005175496151435863L;

	public OnboardingException500(String message) {

		super(message, null);
	}

	public OnboardingException500(String message, Throwable rootCause) {

		super(message, rootCause);
	}

}
