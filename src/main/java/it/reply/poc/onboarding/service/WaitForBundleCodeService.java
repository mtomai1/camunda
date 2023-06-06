package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.arch.UserActionAbstractResolver;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.Event;
import it.reply.poc.onboarding.model.controller.BundleCodeRequest;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WaitForBundleCodeService extends UserActionAbstractResolver<Pair<BundleCodeRequest,String>, Void> {

	@Override
	public Void execute(Pair<BundleCodeRequest, String> input) throws OnboardingException {
		BundleCodeRequest bundleCodeRequest = input.getKey();

		if(bundleCodeRequest.getBundleCode() == null || bundleCodeRequest.getBundleCode().isEmpty()
										|| bundleCodeRequest.getBundleCode().equalsIgnoreCase("INVALID_BUNDLE")) {
			complete(null, input.getValue(), Event.EV_400_INVALID_BUNDLE);

			throw new OnboardingException400("Invalid bundle code");
		}

		Map<String, Object> variables = new HashMap<>();
		variables.put("bundle", bundleCodeRequest.getBundleCode());

		return complete(variables, input.getValue(), Event.EV_200_OK);

	}

	@Override
	public String executableFor() {
		return "WAIT_FOR_BUNDLE_CODE";
	}
}
