package kg.musabaev.onlinetutorback.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Period;

@Converter
public class PeriodInMonthsConverter implements AttributeConverter<Period, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Period attribute) {
		return attribute.getMonths();
	}

	@Override
	public Period convertToEntityAttribute(Integer period) {
		return Period.ofYears(period);
	}
}
