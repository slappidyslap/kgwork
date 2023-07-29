package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.RegisterStudentRequest;
import kg.musabaev.onlinetutorback.dto.response.RegisterUserResponse;
import kg.musabaev.onlinetutorback.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "finishedClasses", ignore = true)
	@Mapping(target = "inProcessClasses", ignore = true)
	Student toModel(RegisterStudentRequest dto);

	RegisterUserResponse toDto(Student model);
}
