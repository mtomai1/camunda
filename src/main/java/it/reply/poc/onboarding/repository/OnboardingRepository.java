package it.reply.poc.onboarding.repository;

import it.reply.poc.onboarding.model.db.CompositeKey;
import it.reply.poc.onboarding.model.db.OnboardingTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<OnboardingTable, CompositeKey> {

	@Query(nativeQuery = true, value="SELECT * FROM onboarding WHERE userid= ?1")
	Optional<OnboardingTable> findOneByUserid(String userid);
}
