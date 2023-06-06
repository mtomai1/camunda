package it.reply.poc.onboarding.arch;

import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException404;
import it.reply.poc.onboarding.model.CompleteRequest;
import it.reply.poc.onboarding.model.CompleteValueMapRequest;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.GetTaskRequest;
import it.reply.poc.onboarding.model.TaskState;
import it.reply.poc.onboarding.model.db.CompositeKey;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import it.reply.poc.onboarding.util.EnvVars;
import it.reply.poc.onboarding.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class UserActionAbstractResolver<I, O> {

	@Autowired
	private RestTemplate client;

	@Autowired
	private EnvVars envVars;

	@Autowired
	private OnboardingRepository onboardingRepository;

	public abstract O execute(I input) throws OnboardingException;

	public abstract String executableFor();

	public Void complete(Map<String, Object> variables, String userId, Event event) throws OnboardingException {
		Optional<TaskState> currentState = getCurrentState(userId);

		if(currentState.isPresent()) {

			Map<String, String> uriVars = new HashMap<>();
			uriVars.put("task_id", currentState.get().getId());

			OnboardingTable onboardingTable = OnboardingTable.builder()
											.compositeKey(CompositeKey.builder()
																			.userid(userId)
																			.state(currentState.get().getTaskDefinitionKey())
																			.build())
											.lastUpdate(LocalDateTime.now().toString())
											.build();

			onboardingRepository.save(onboardingTable);

			String url = Utils.buildUrlWithParameters(envVars.getRestHost() + envVars.getCompleteTask(), uriVars);
			client.postForEntity(url, mapToCompleteRequest(variables, event), Void.class);

			return null;
		}

		throw new OnboardingException404("USER NOT FOUND!");
	}

	public Optional<TaskState> getCurrentState(String userId) {

		GetTaskRequest getTaskRequest = new GetTaskRequest(userId);
		ResponseEntity<TaskState[]> response = client.postForEntity(envVars.getRestHost() + envVars.getGetTaskId(),getTaskRequest, TaskState[].class);

		if(response.hasBody()) {
			TaskState[] body = response.getBody();
			if (body != null && body.length > 0) {
				return Optional.of(body[0]);
			}
		}
		return Optional.empty();
	}

	private CompleteRequest mapToCompleteRequest(Map<String, Object> variables, Event event) {

		Map<String, CompleteValueMapRequest> requestVariables = new HashMap<>();

		Optional.ofNullable(variables).ifPresent(vars -> vars.forEach((k, v) -> requestVariables.put(k, new CompleteValueMapRequest(v))));

		requestVariables.put("onb_event", new CompleteValueMapRequest(event.getValue()));
		requestVariables.put("state", new CompleteValueMapRequest(executableFor()));

		return new CompleteRequest(requestVariables);
	}
}
