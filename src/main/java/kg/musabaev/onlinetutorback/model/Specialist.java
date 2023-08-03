package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import kg.musabaev.onlinetutorback.domain.StudentRatedSpecialist;
import kg.musabaev.onlinetutorback.util.converter.PeriodInMonthsConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

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
	@Builder.Default
	Double reputation = 0.0;
	@Column(nullable = false)
	@Builder.Default
	Integer ratingsNumber = 0;
	@Column(nullable = false)
	@Builder.Default
	String description = "";
	@Column(nullable = false)
	@Convert(converter = PeriodInMonthsConverter.class)
	Period workExperience;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "specialist")
	@Builder.Default
	List<StudentRatedSpecialist> studentRatedSpecialists = new ArrayList<>();
}
