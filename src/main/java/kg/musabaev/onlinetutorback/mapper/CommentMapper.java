package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.request.NewCommentRequest;
import kg.musabaev.onlinetutorback.dto.request.UpdateCommentRequest;
import kg.musabaev.onlinetutorback.dto.response.NewCommentResponse;
import kg.musabaev.onlinetutorback.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "baseClass", ignore = true)
	@Mapping(target = "userId", ignore = true) // FIXME
	@Mapping(target = "uploadedDate", ignore = true)
	Comment toModel(NewCommentRequest dto);

	NewCommentResponse toDto(Comment comment);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "baseClass", ignore = true)
	@Mapping(target = "userId", ignore = true) // FIXME
	@Mapping(target = "uploadedDate", ignore = true)
	void update(UpdateCommentRequest dto, @MappingTarget Comment model);
}
