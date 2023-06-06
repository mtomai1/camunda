package it.reply.poc.onboarding.delegate;

import it.reply.poc.onboarding.arch.ListnerAbstract;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Slf4j
@Named("EndListener")
public class EndListener extends ListnerAbstract implements JavaDelegate {

	@Autowired
	private OnboardingRepository onboardingRepository;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {

		save(delegateExecution);
	}
}