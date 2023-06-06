package it.reply.poc.onboarding.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CompositeKey implements Serializable {

    @Column(name = "userid", nullable = false)
    private String userid;
    @Column(name = "state", nullable = false)
    private String state;
}
