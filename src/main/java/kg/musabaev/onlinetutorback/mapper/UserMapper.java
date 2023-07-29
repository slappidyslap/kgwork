package kg.musabaev.onlinetutorback.mapper;

import kg.musabaev.onlinetutorback.dto.response.AuthenticateOrRefreshResponse;
import kg.musabaev.onlinetutorback.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	AuthenticateOrRefreshResponse.UserInfo toDto(User user);
}
