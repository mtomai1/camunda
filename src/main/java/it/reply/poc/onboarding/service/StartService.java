package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.exception.OnboardingException500;
import it.reply.poc.onboarding.model.db.CompositeKey;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import it.reply.poc.onboarding.util.EnvVars;
import it.reply.poc.onboarding.model.StartRequest;
import it.reply.poc.onboarding.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class StartService {

	@Autowired
	private RestTemplate client;
	@Autowired
	private EnvVars envVars;

	@Autowired
	private OnboardingRepository onboardingRepository;

	public Void execute (String userId, String locale) throws OnboardingException {
		Optional<OnboardingTable> oneByUserid = onboardingRepository.findOneByUserid(userId);
		if(oneByUserid.isEmpty()) {

			String toLoad = envVars.getStateMachineName();
			if(locale != null && !locale.isEmpty() && !locale.equalsIgnoreCase("IT")) {
				toLoad = envVars.getStateMachineName().concat("_" + locale.toUpperCase(Locale.ROOT));
			}

			Map<String, String> uriVars = new HashMap<>();
			uriVars.put("key", toLoad);

			String url = Utils.buildUrlWithParameters(envVars.getRestHost() + envVars.getStart(), uriVars);

			StartRequest startRequest = new StartRequest(userId);

			try {
				client.postForEntity(url, startRequest, Void.class);
			} catch (HttpStatusCodeException exception) {
				if(exception.getStatusCode().is4xxClientError()) {
					throw new OnboardingException400("Language not supported");
				}

			}

			OnboardingTable onboardingTable = OnboardingTable.builder()
											.compositeKey(CompositeKey.builder()
																			.userid(userId)
																			.state("START")
																			.build())
											.lastUpdate(LocalDateTime.now().toString())
											.build();

			onboardingRepository.save(onboardingTable);

			return null;
		}

		throw new OnboardingException500("USER ALREADY PRESENT");
	}
}
