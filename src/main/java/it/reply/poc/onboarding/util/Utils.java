package it.reply.poc.onboarding.util;

import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

public final class Utils {

	private Utils() {
	}

	public static String buildUrlWithParameters(String template, Map<String, String> parameters) {
		DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(template);
		defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		return defaultUriBuilderFactory.builder().build(parameters).toASCIIString();
	}



}
