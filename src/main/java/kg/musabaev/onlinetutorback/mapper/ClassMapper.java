package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.NewGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.NewIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateGroupClassRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateIndividualClassRequest;
import kg.musabaev.onlinetutorback.dto.response.NewClassResponse;
import kg.musabaev.onlinetutorback.model.GroupClass;
import kg.musabaev.onlinetutorback.model.IndividualClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Duration;

@Mapper(componentModel = "spring")
public interface ClassMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "category", ignore = true)
	@Mapping(target = "author", ignore = true)
	GroupClass toModel(NewGroupClassRequest dto);

	NewClassResponse toDto(GroupClass clazz);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "category", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(source = "durationInSeconds", target = "duration", qualifiedByName = "secondsToDuration")
	IndividualClass toModel(NewIndividualClassRequest dto);

	NewClassResponse toDto(IndividualClass clazz);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "category", ignore = true)
	@Mapping(source = "durationInSeconds", target = "duration", qualifiedByName = "secondsToDuration")
	void update(UpdateIndividualClassRequest source, @MappingTarget IndividualClass dest);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "category", ignore = true)
	void update(UpdateGroupClassRequest source, @MappingTarget GroupClass dest);

	@Named("secondsToDuration")
	static Duration secondsToDuration(Long seconds) {
		return Duration.ofSeconds(seconds);
	}
}
