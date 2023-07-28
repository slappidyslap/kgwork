package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import kg.musabaev.onlinetutorback.util.converter.PeriodInMonthsConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Period;

@Entity
@Table(name = "specialists")
@DiscriminatorValue("specialist_user")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Specialist extends User {
	@Column(nullable = false)
	Double reputation;
	@Column(nullable = false)
	String description;
	@Column(nullable = false)
	@Convert(converter = PeriodInMonthsConverter.class)
	Period workExperience;
}
