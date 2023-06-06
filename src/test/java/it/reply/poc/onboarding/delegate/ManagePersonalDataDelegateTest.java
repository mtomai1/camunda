package it.reply.poc.onboarding.delegate;

import it.reply.poc.onboarding.Application;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagePersonalDataDelegateTest {

	private final ManagePersonalDataDelegate managePersonalDataDelegate = new ManagePersonalDataDelegate();

	@MockBean
	private OnboardingRepository onboardingRepository;

	@Test
	@Order(1)
	public void execute_200() throws Exception {
		LinkedHashMap<String, String> vars = new LinkedHashMap<>();
		vars.put("name", "Matteo");

		DelegateExecution delegateExecution = new DelegateExecutionFake().withBusinessKey("KEY")
										.withActivityInstanceId("ID:123451234")
										.withVariable("personalDataRequest", vars);

		given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

		managePersonalDataDelegate.execute(delegateExecution);
	}

	@Test
	@Order(2)
	public void execute_400() throws Exception {
		Map<String, String> personalData = new LinkedHashMap<>();
		personalData.put("name", "INVALID" );

		DelegateExecution delegateExecution = new DelegateExecutionFake().withBusinessKey("KEY")
										.withActivityInstanceId("ID:123451234").withVariable("personalDataRequest", personalData);

		given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

		assertThrows(OnboardingException400.class, () -> managePersonalDataDelegate.execute(delegateExecution));
	}
}
