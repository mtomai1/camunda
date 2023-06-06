package it.reply.poc.onboarding.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EnvVars {

	@Value("${statemachine.to-load}")
	private String stateMachineName;

	@Value("${statemachine.rest-host}")
	private String restHost;

	@Value("${statemachine.get-task-id}")
	private String getTaskId;

	@Value("${statemachine.complete-task}")
	private String completeTask;

	@Value("${statemachine.start}")
	private String start;

	@Value("${statemachine.state-with-back}")
	private String[] stateWithBack;

	@Value("${statemachine.state-with-retry}")
	private String[] stateWithRetry;


}
