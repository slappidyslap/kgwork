package kg.musabaev.onlinetutorback.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter
public class DurationInSecondsConverter implements AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration attribute) {
		return attribute.getSeconds();
	}

	@Override
	public Duration convertToEntityAttribute(Long duration) {
		return Duration.ofSeconds(duration);
	}
}
