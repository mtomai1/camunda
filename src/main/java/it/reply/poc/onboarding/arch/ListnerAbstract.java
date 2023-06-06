package it.reply.poc.onboarding.arch;

import it.reply.poc.onboarding.model.db.CompositeKey;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Locale;

public abstract class ListnerAbstract {

	@Autowired
	private OnboardingRepository onboardingRepository;

	public void save(DelegateExecution delegateExecution) {

										OnboardingTable onboardingTable = OnboardingTable.builder()
										.compositeKey(CompositeKey.builder()
																		.userid(delegateExecution.getBusinessKey())
																		.state(delegateExecution.getActivityInstanceId().split(":")[0].toUpperCase(Locale.ROOT))
																		.build())
										.lastUpdate(LocalDateTime.now().toString())
										.build();

		onboardingRepository.save(onboardingTable);
	}

}
