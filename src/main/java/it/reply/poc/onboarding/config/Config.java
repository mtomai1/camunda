package it.reply.poc.onboarding.config;

import it.reply.poc.onboarding.interceptors.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Autowired
	private LoggingInterceptor loggingInterceptor;

	@Bean
	@Autowired
	public RestTemplate restTemplate() {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(loggingInterceptor);
		return restTemplate;
	}
}
