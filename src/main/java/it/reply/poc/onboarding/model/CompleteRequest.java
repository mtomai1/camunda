package it.reply.poc.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRequest {

	private Map<String, CompleteValueMapRequest> variables;

	@Override
	public String toString() {
		return "CompleteRequest{" +
										"variables=" + variables +
										'}';
	}
}
