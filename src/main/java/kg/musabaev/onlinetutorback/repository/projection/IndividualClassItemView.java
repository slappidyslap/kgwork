package kg.musabaev.onlinetutorback.repository.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kg.musabaev.onlinetutorback.config.jackson.DurationInSecondsSerializer;

import java.time.Duration;

public interface IndividualClassItemView extends BaseClassItemView {
	@JsonProperty("durationInSeconds")
	@JsonSerialize(using = DurationInSecondsSerializer.class)
	Duration getDuration();
}
