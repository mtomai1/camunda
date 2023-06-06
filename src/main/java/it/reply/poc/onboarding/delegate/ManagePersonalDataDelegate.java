package it.reply.poc.onboarding.delegate;

import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.LinkedHashMap;

@Slf4j
@SuppressWarnings("unchecked")
public class ManagePersonalDataDelegate implements JavaDelegate {


	public void execute(DelegateExecution execution) throws Exception {
		log.info("Processing request ManagePersonalDataDelegate '");
		LinkedHashMap<String, String> personalDataRequest = (LinkedHashMap<String, String>) execution.getVariable("personalDataRequest");

		if (personalDataRequest.get("name").equalsIgnoreCase("INVALID")) {
			execution.setVariable("onb_event", Event.EV_400_GENERIC_ERROR.getValue());
			throw new OnboardingException400("Personal Data with errors!");
		}

		else {
			execution.setVariable("onb_event", Event.EV_200_OK.getValue());
		}

	}
}
