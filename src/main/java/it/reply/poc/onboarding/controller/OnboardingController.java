package it.reply.poc.onboarding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.reply.poc.onboarding.exception.OnboardingException;
import it.reply.poc.onboarding.model.controller.BundleCodeRequest;
import it.reply.poc.onboarding.model.controller.IdDocRequest;
import it.reply.poc.onboarding.model.controller.IssueCardRequest;
import it.reply.poc.onboarding.model.controller.PersonalDataRequest;
import it.reply.poc.onboarding.service.BackService;
import it.reply.poc.onboarding.service.IssueCardService;
import it.reply.poc.onboarding.service.RetryService;
import it.reply.poc.onboarding.service.StartService;
import it.reply.poc.onboarding.service.WaitForBundleCodeService;
import it.reply.poc.onboarding.service.WaitForIdDocService;
import it.reply.poc.onboarding.service.WaitPersonalDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboarding")
@Slf4j
public class OnboardingController {

	@Autowired
	private WaitForBundleCodeService waitForBundleCodeService;
	@Autowired
	private WaitForIdDocService waitForIdDocService;
	@Autowired
	private WaitPersonalDataService waitPersonalDataService;
	@Autowired
	private IssueCardService issueCardService;
	@Autowired
	private BackService backService;
	@Autowired
	private RetryService retryService;


	@Autowired
	private StartService startService;

	@Operation(summary = "Start")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/start")
	public ResponseEntity<Void> start(@Parameter(description = "id of user") @RequestParam String userId,
																																			@Parameter(description = "language", example = "IT") @RequestParam String language) throws OnboardingException {

		return new ResponseEntity<>(startService.execute(userId, language), HttpStatus.OK);
	}

	@Operation(summary = "Wait For Bundle Code")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/bundle-code")
	public ResponseEntity<Void> bundleCode(@Parameter(description = "id of user") @RequestParam String userId,
																																								@RequestBody BundleCodeRequest bundleCodeRequest) throws OnboardingException {

		return new ResponseEntity<>(waitForBundleCodeService.execute(Pair.of(bundleCodeRequest, userId)), HttpStatus.OK);
	}


	@Operation(summary = "Wait For ID Doc")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/store-id-doc")
	public ResponseEntity<Void> storeIdDoc(@Parameter(description = "id of user") @RequestParam String userId,
																																								@RequestBody IdDocRequest idDocRequest) throws OnboardingException {

		return new ResponseEntity<>(waitForIdDocService.execute(Pair.of(idDocRequest, userId)), HttpStatus.OK);
	}

	@Operation(summary = "Wait Personal Data")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/store-personal-data")
	public ResponseEntity<Void> storePersonalData(@Parameter(description = "id of user") @RequestParam String userId,
																																															@RequestBody PersonalDataRequest personalDataRequest) throws OnboardingException {

		return new ResponseEntity<>(waitPersonalDataService.execute(Pair.of(personalDataRequest, userId)), HttpStatus.OK);
	}

	@Operation(summary = "Issue Card")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/issue-card")
	public ResponseEntity<Void> issueCard(@Parameter(description = "id of user") @RequestParam String userId,
																																							@RequestBody IssueCardRequest IssueCardRequest) throws OnboardingException {

		return new ResponseEntity<>(issueCardService.execute(Pair.of(IssueCardRequest, userId)), HttpStatus.OK);
	}

	@Operation(summary = "Back")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/back")
	public ResponseEntity<Void> back(@Parameter(description = "id of user") @RequestParam String userId) throws OnboardingException {

		return new ResponseEntity<>(backService.execute(userId), HttpStatus.OK);
	}

	@Operation(summary = "Retry")
	@ApiResponses(value = {
									@ApiResponse(responseCode = "200", description = "Completed!", content = { @Content(mediaType = "application/json", schema = @Schema()) }),
									@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
									@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/retry")
	public ResponseEntity<Void> retry(@Parameter(description = "id of user") @RequestParam String userId) throws OnboardingException {

		return new ResponseEntity<>(retryService.execute(userId), HttpStatus.OK);
	}

}
