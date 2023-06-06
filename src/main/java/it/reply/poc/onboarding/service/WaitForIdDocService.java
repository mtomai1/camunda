package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.arch.UserActionAbstractResolver;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.controller.IdDocRequest;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WaitForIdDocService extends UserActionAbstractResolver<Pair<IdDocRequest,String>, Void> {

	@Override
	public Void execute(Pair<IdDocRequest, String> input) throws OnboardingException {
		IdDocRequest idDocRequest = input.getKey();
		if(idDocRequest.getId() == null || idDocRequest.getId().isEmpty()
										|| idDocRequest.getId().equalsIgnoreCase("INVALID_ID")) {
			complete(null, input.getValue(), Event.EV_400_GENERIC_ERROR);

			throw new OnboardingException400("Invalid document ID");
		} else if(LocalDate.now().isAfter(idDocRequest.getExpiration())) {
			complete(null, input.getValue(), Event.EV_400_EXPIRED_DOC);

			throw new OnboardingException400("Document Expired");
		}

		return complete(null, input.getValue(), Event.EV_200_OK);
	}

	@Override
	public String executableFor() {
		return "WAIT_FOR_ID_DOC";
	}
}
