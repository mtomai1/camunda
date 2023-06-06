package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.arch.UserActionAbstractResolver;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.controller.PersonalDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WaitPersonalDataService extends UserActionAbstractResolver<Pair<PersonalDataRequest,String>, Void> {

	@Override
	public Void execute(Pair<PersonalDataRequest, String> input) throws OnboardingException {
		PersonalDataRequest personalDataRequest = input.getKey();
		log.info(personalDataRequest.toString());

		Map<String, Object> vars = new HashMap<>();
		vars.put("personalDataRequest", personalDataRequest);

		complete(vars, input.getValue(), Event.EV_200_OK);

		return null;
	}

	@Override
	public String executableFor() {
		return "WAIT_PERSONAL_DATA";
	}
}
