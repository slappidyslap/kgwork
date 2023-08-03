package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.RegisterSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateSpecialistRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.model.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Period;

@Mapper(componentModel = "spring")
public interface SpecialistMapper {

	@Mapping(target = "reputation", constant = "0.0")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "ratingsNumber", ignore = true)
	@Mapping(target = "workExperience", source = "workExperienceInMonths", qualifiedByName = "monthsToPeriod")
	Specialist toModel(RegisterSpecialistRequest dto);

	RegisterUserResponse toDto(Specialist newSpecialist);

	@Mapping(target = "reputation", constant = "0.0")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "ratingsNumber", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "workExperience", source = "workExperienceInMonths", qualifiedByName = "monthsToPeriod")
	void update(UpdateSpecialistRequest dto, @MappingTarget Specialist model);

	@Named("monthsToPeriod")
	static Period monthsToPeriod(Integer months) {
		return Period.ofYears(months);
	}
}
