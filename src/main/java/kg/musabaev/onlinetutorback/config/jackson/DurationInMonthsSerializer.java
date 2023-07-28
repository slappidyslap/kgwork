package kg.musabaev.onlinetutorback.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationInMonthsSerializer extends JsonSerializer<Duration> {

	@Override
	public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.get(ChronoUnit.MONTHS));
	}
}
