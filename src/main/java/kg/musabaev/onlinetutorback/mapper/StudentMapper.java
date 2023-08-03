package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "finishedClasses", ignore = true)
	@Mapping(target = "inProcessClasses", ignore = true)
	@Mapping(target = "ratedSpecialists", ignore = true)
	Student toModel(RegisterStudentRequest dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "finishedClasses", ignore = true)
	@Mapping(target = "inProcessClasses", ignore = true)
	@Mapping(target = "ratedSpecialists", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	void update(UpdateStudentRequest dto, @MappingTarget Student model);

	RegisterUserResponse toDto(Student model);
}
