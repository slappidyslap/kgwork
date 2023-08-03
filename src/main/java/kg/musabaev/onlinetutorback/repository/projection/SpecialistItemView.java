package kg.musabaev.onlinetutorback.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kg.musabaev.onlinetutorback.config.jackson.PeriodInMonthsSerializer;

import java.time.Period;

public interface SpecialistItemView extends UserItemView {
	Double getReputation();
	String getDescription();
	@JsonProperty("workExperienceInMonths")
	@JsonSerialize(using = PeriodInMonthsSerializer.class)
	Period getWorkExperience();
}
