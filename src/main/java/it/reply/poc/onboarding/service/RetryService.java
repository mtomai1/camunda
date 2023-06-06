package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.arch.UserActionAbstractResolver;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.TaskState;
import it.reply.poc.onboarding.util.EnvVars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RetryService extends UserActionAbstractResolver<String, Void> {

	@Autowired
	private EnvVars envVars;

	@Override
	public Void execute(String input) throws OnboardingException {
		Optional<TaskState> currentState = getCurrentState(input);

		if(currentState.isPresent()) {
			if (Arrays.asList(envVars.getStateWithRetry()).contains(currentState.get().getTaskDefinitionKey())) {
				complete(null, input, Event.EV_200_RETRY);
			}
			else {
				throw new OnboardingException400("You can not retry from this state");
			}

		}

		return null;
	}

	@Override
	public String executableFor() {
		return "";
	}
}
