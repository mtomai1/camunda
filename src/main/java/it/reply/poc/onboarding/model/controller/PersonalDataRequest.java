package it.reply.poc.onboarding.model.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataRequest {

	private String name;
	private String surname;

	@Override
	public String toString() {
		return "PersonalDataRequest{" +
										"name='" + name + '\'' +
										", surname='" + surname + '\'' +
										'}';
	}
}
