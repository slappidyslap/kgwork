package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddOrDeleteClassInStudentList {
	@NotNull
	@Positive
	Long classId;
}
