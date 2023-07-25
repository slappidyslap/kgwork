package kg.musabaev.onlinetutorback.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import kg.musabaev.onlinetutorback.OnlineTutorBackApplication;
import kg.musabaev.onlinetutorback.util.validator.UrlOrNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateClassBaseRequest {
	@NotNull
	String title;
	@NotNull
	String description;
	@PositiveOrZero
	@NotNull
	Integer price;
	@JsonSetter(nulls = Nulls.SKIP)
	@UrlOrNull
	String coverUrl = OnlineTutorBackApplication.defaultCoverUrl;
}