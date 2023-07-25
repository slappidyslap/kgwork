package kg.musabaev.onlinetutorback.dto.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import kg.musabaev.onlinetutorback.OnlineTutorBackApplication;
import kg.musabaev.onlinetutorback.util.validator.UrlOrNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class NewClassBaseRequest {
	@NotNull
	String title;
	@NotNull
	String description;
	@NotNull
	@NotEmpty
	List<Integer> categoryIds;
	@Positive
	@NotNull
	Long specialistId;
	@PositiveOrZero
	@NotNull
	Integer price;
	@JsonSetter(nulls = Nulls.SKIP)
	@UrlOrNull
	String coverUrl = OnlineTutorBackApplication.defaultCoverUrl;
}
