package it.reply.poc.onboarding.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.reply.poc.onboarding.Application;
import it.reply.poc.onboarding.model.TaskState;
import it.reply.poc.onboarding.model.controller.BundleCodeRequest;
import it.reply.poc.onboarding.model.controller.IdDocRequest;
import it.reply.poc.onboarding.model.controller.IssueCardRequest;
import it.reply.poc.onboarding.model.controller.PersonalDataRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.ExpectedCount.twice;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OnboardingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RestTemplate client;

	private MockRestServiceServer mockServer;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public void init() throws JsonProcessingException {
		mockServer = MockRestServiceServer.createServer(client);
		TaskState taskState = new TaskState("id", "name", "assignee",
										"created", "executionId", "owner",
										"processDefinitionId", "processInstanceId", "taskDefinitionKey");
		TaskState[] response = {taskState};

		//Test 1
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
										.andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
																		.contentType(MediaType.APPLICATION_JSON));
		mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK));

		//Test 5
		mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
																		.contentType(MediaType.APPLICATION_JSON));
		mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK));

		//Test 6
		response[0].setTaskDefinitionKey("WAIT_PERSONAL_DATA");
		mockServer.expect(twice(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
																		.contentType(MediaType.APPLICATION_JSON));
		mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK));

		//Test 7
		response[0].setTaskDefinitionKey("ISSUE_CARD_EXCEPTION");
		mockServer.expect(twice(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK).body(objectMapper.writeValueAsString(response))
																		.contentType(MediaType.APPLICATION_JSON));
		mockServer.expect(once(), requestTo(matchesPattern(".*.localhost.*")))
										.andExpect(method(HttpMethod.POST))
										.andRespond(withStatus(HttpStatus.OK));
	}

	@Test
	@Order(1)
	public void start_200() throws Exception {
		mockMvc.perform(post("/onboarding/start")
																		.queryParam("userId", "Matteo")
																		.queryParam("language", "IT"))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(2)
	public void bundleCode_200() throws Exception {
		mockMvc.perform(post("/onboarding/bundle-code")
																		.queryParam("userId", "Matteo")
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(objectMapper.writeValueAsString(new BundleCodeRequest("1234"))))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(3)
	public void storeIdDoc_200() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		mockMvc.perform(post("/onboarding/store-id-doc")
																		.queryParam("userId", "Matteo")
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(objectMapper.writeValueAsString(new IdDocRequest("1234", LocalDate.of(2023,5, 10)))))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(4)
	public void storePersonalData_200() throws Exception {
		mockMvc.perform(post("/onboarding/store-personal-data")
																		.queryParam("userId", "Matteo")
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(objectMapper.writeValueAsString(new PersonalDataRequest("Matteo", "Tomai"))))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(5)
	public void issueCard_200() throws Exception {
		mockMvc.perform(post("/onboarding/issue-card")
																		.queryParam("userId", "Matteo")
																		.contentType(MediaType.APPLICATION_JSON)
																		.content(objectMapper.writeValueAsString(new IssueCardRequest("1234"))))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(6)
	public void back_200() throws Exception {
		mockMvc.perform(post("/onboarding/back")
																		.queryParam("userId", "Matteo"))
										.andExpect(status().is2xxSuccessful());
	}

	@Test
	@Order(7)
	public void retry_200() throws Exception {
		mockMvc.perform(post("/onboarding/retry")
																		.queryParam("userId", "Matteo"))
										.andExpect(status().is2xxSuccessful());
	}

}
