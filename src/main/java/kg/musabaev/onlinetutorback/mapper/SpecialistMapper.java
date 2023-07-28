package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.model.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Period;

@Mapper(componentModel = "spring")
public interface SpecialistMapper {

	@Mapping(target = "reputation", constant = "0.0")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "workExperience", source = "workExperienceInMonths", qualifiedByName = "monthsToPeriod")
	Specialist toModel(RegisterSpecialistRequest dto);

	RegisterUserResponse toDto(Specialist newSpecialist);

	@Named("monthsToPeriod")
	static Period monthsToPeriod(Integer months) {
		return Period.ofYears(months);
	}
}
