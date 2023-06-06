package it.reply.poc.onboarding.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.reply.poc.onboarding.Application;
import it.reply.poc.onboarding.exception.OnboardingException400;
import it.reply.poc.onboarding.exception.OnboardingException404;
import it.reply.poc.onboarding.model.TaskState;
import it.reply.poc.onboarding.model.controller.IdDocRequest;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import it.reply.poc.onboarding.repository.OnboardingRepository;
import org.apache.commons.lang3.tuple.Pair;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WaitForIdDocServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private OnboardingRepository onboardingRepository;

    @Autowired
    private WaitForIdDocService waitForIdDocService;

    private MockRestServiceServer mockServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void init() throws JsonProcessingException {

        TaskState taskState = new TaskState("id", "name", "assignee",
                "created", "executionId", "owner",
                "processDefinitionId", "processInstanceId", "taskDefinitionKey");
        TaskState[] response = {taskState};

        mockServer = MockRestServiceServer.createServer(restTemplate);

        //Test 1
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));

        //Test 2
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));

        //Test 3
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));


        //Test 4
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON));
        mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK));
    }

    @Test
    @Order(1)
    public void waitforiddoc_200() throws Exception {
        given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

        IdDocRequest idDocRequest = new IdDocRequest("12345", LocalDate.now());
        waitForIdDocService.execute(Pair.of(idDocRequest, "Matteo"));
    }

    @Test
    @Order(2)
    public void waitforiddoc_400_invalid_id() throws Exception {
        given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

        IdDocRequest idDocRequest = new IdDocRequest("INVALID_ID", LocalDate.now());
        assertThrows(OnboardingException400.class, () -> waitForIdDocService.execute(Pair.of(idDocRequest, "Matteo")));

        IdDocRequest idDocRequestEmpty = new IdDocRequest("", LocalDate.now());
        assertThrows(OnboardingException400.class, () -> waitForIdDocService.execute(Pair.of(idDocRequestEmpty, "Matteo")));

        IdDocRequest idDocRequestNull = new IdDocRequest(null, LocalDate.now());
        assertThrows(OnboardingException400.class, () -> waitForIdDocService.execute(Pair.of(idDocRequestNull, "Matteo")));
    }

    @Test
    @Order(3)
    public void waitforiddoc_400_expired() throws Exception {
        given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

        IdDocRequest idDocRequest = new IdDocRequest("12345", LocalDate.of(2021, 4,9));

        assertThrows(OnboardingException400.class, () -> waitForIdDocService.execute(Pair.of(idDocRequest, "Matteo")));
    }

    @Test
    @Order(4)
    public void waitpersonaldata_404_user_not_found() throws Exception {
        given(onboardingRepository.save(any())).willReturn(new OnboardingTable());

        IdDocRequest idDocRequest = new IdDocRequest("12345", LocalDate.now());

        assertThrows(OnboardingException404.class, () -> waitForIdDocService.execute(Pair.of(idDocRequest, "Matteo")));


    }
}
