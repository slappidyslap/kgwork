package kg.musabaev.onlinetutorback.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Period;

public class PeriodInMonthsSerializer extends JsonSerializer<Period> {

	@Override
	public void serialize(Period value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.getMonths());
	}
}
