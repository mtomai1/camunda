package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.arch.UserActionAbstractResolver;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.controller.IssueCardRequest;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class IssueCardService extends UserActionAbstractResolver<Pair<IssueCardRequest, String>, Void> {


	@Override
	public Void execute(Pair<IssueCardRequest, String> input) throws OnboardingException {

		IssueCardRequest issueCardRequest = input.getKey();

		if (issueCardRequest.getPin() == null || issueCardRequest.getPin().isEmpty()
										|| issueCardRequest.getPin().equalsIgnoreCase("INVALID_PIN")) {
			complete(null, input.getValue(), Event.EV_400_GENERIC_ERROR);

			throw new OnboardingException400("Invalid PIN");

		}

		else return complete(null, input.getValue(), Event.EV_200_OK);
	}

	@Override
	public String executableFor() {
		return "ISSUE_CARD";
	}
}
