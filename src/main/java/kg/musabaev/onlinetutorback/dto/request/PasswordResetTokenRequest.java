package kg.musabaev.onlinetutorback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordResetTokenRequest {
    @URL
    String redirectUri;
    @NotNull
    @NotBlank
    String username;
}
