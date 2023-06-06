package it.reply.poc.onboarding.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(
					HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {


		String cachedBody = new String(reqBody, StandardCharsets.UTF_8);
		ClientHttpResponse response = ex.execute(req, reqBody);
		response = new BufferedClientHttpResponse(response);
		logRequest(req, cachedBody);
		logResponse(response);

		return response;
	}

	private void logRequest(HttpRequest request, String body) {

			String reqLog = "\n\n========================= EXTERNAL CALL REQUEST ==========================\n" +
							"URI          : ".concat(request.getURI().toString()).concat("\n") +
							"Method       : ".concat(request.getMethodValue()).concat("\n") +
							"Headers      : ".concat(Objects.requireNonNull(String.valueOf(request.getHeaders())).concat("\n")) +
							"Request body : ".concat(body).concat("\n") +
							"========================== REQUEST END ============================\n\n";
			log.info(reqLog);
	}

	private void logResponse(ClientHttpResponse response) throws IOException {

			String respLog = "\n\n========================= EXTERNAL CALL RESPONSE ==========================\n" +
							"Status code   : ".concat(response.getStatusCode().toString()).concat("\n") +
							"Status text   : ".concat(response.getStatusText()).concat("\n") +
							"Headers       : ".concat(String.valueOf(response.getHeaders())).concat("\n") +
							"Response body : ".concat(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)).concat("\n") +
							"========================== RESPONSE END ============================\n\n";
			log.info(respLog);
	}
}
