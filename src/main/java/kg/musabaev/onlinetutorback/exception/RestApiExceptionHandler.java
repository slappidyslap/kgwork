package kg.musabaev.onlinetutorback.exception;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			@NotNull MethodArgumentNotValidException ex,
			@NotNull HttpHeaders headers,
			@NotNull HttpStatusCode status,
			@NotNull WebRequest request) {
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@ExceptionHandler({
			ClassNotFoundException.class, CommentNotFoundException.class,
			UserNotFoundException.class, CategoryNotFoundException.class})
	@ResponseStatus(NOT_FOUND)
	Map<String, String> handleNotFound(Exception e) {
		return Map.of("exception", e.getMessage());
	}

	@ExceptionHandler(PropertyReferenceException.class)
	@ResponseStatus(BAD_REQUEST)
	void handlePropertyReferenceException() {
	}
}
