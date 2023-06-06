package it.reply.poc.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskRequest {

	private String processInstanceBusinessKey;

	@Override
	public String toString() {
		return "GetTaskRequest{" +
										"processInstanceBusinessKey='" + processInstanceBusinessKey + '\'' +
										'}';
	}
}
