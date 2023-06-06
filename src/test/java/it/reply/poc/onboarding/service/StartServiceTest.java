package it.reply.poc.onboarding.service;

import it.reply.poc.onboarding.Application;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.exception.OnboardingException500;
import it.reply.poc.onboarding.model.db.CompositeKey;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.ExpectedCount.twice;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StartServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private OnboardingRepository onboardingRepository;

    @Autowired
    private StartService startService;

    private MockRestServiceServer mockServer;

    @BeforeAll
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        //Test 1-2
        mockServer.expect(twice(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));

        //Test 3
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(1)
    public void startService_200() throws Exception {
        given(onboardingRepository.findOneByUserid(anyString())).willReturn(Optional.empty());

        startService.execute("Matteo", "IT");
    }

    @Test
    @Order(2)
    public void startService_200_notIT() throws Exception {
        given(onboardingRepository.findOneByUserid(anyString())).willReturn(Optional.empty());

        startService.execute("Matteo", "FR");
    }

    @Test
    @Order(3)
    public void startService_500() throws Exception {
        given(onboardingRepository.findOneByUserid(anyString()))
                .willReturn(Optional.of(new OnboardingTable(new CompositeKey("ID", "STATE"), "DATA")));

        assertThrows(OnboardingException500.class, () -> startService.execute("Matteo", "IT"));
    }

    @Test
    @Order(4)
    public void startService_400() throws Exception {
        given(onboardingRepository.findOneByUserid(anyString())).willReturn(Optional.empty());

        assertThrows(OnboardingException400.class, () -> startService.execute("Matteo", "IT"));
    }


}
