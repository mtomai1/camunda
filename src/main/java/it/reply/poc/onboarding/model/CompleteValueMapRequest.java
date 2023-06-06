package it.reply.poc.onboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteValueMapRequest {

	private Object value;
	private String type;
	private ValueInfo valueInfo;

	public CompleteValueMapRequest(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CompleteValueMapRequest{" +
										"value='" + value + '\'' +
										'}';
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ValueInfo {

		private String objectTypeName;
		private String serializationDataFormat;
	}
}
