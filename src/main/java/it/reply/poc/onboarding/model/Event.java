package it.reply.poc.onboarding.model;

public enum Event {

	EV_200_OK("200_OK"),
	EV_400_INVALID_BUNDLE("400_INVALID_BUNDLE_CODE"),
	EV_400_EXPIRED_DOC("400_EXPIRED_DOC"),
	EV_400_GENERIC_ERROR("400_GENERIC_ERROR"),
	EV_200_RETRY("200_RETRY"),
	EV_200_BACK("200_BACK");

	private final String value;

	Event(String value) {

		this.value = value;
	}

	public String getValue() {

		return value;
	}
}
