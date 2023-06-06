package it.reply.poc.onboarding.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "onboarding")
public class OnboardingTable {

	@EmbeddedId
	private CompositeKey compositeKey;

	@Column(name = "last_update", nullable = false)
	private String lastUpdate;
}
