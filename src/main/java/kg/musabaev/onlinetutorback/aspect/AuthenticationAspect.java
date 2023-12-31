package kg.musabaev.onlinetutorback.aspect;

import kg.musabaev.onlinetutorback.dto.request.PasswordResetTokenRequest;
import kg.musabaev.onlinetutorback.model.User;
import kg.musabaev.onlinetutorback.repository.BaseClassRepo;
import kg.musabaev.onlinetutorback.repository.CommentRepo;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
@Aspect
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationAspect {

	BaseClassRepo classRepo;
	CommentRepo commentRepo;
	UserRepo userRepo;

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.CommentController)")
	void commentController() {
	}

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.ClassController)")
	void classController() {
	}

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.UserController)")
	void userController() {
	}

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.PasswordResetController)")
	void passwordResetController() {
	}

	@Before("""
			classController() && execution(* updateGroupClass(..)) ||
			classController() && execution(* updateIndividualClass(..)) ||
			classController() && execution(* deleteGroupClass(..)) ||
			classController() && execution(* deleteIndividualClass(..))""")
	void beforeMethodsInClassControllerVerifyAuthUserIsAuthor(JoinPoint jp) {
		if (isNotAuthenticatedUser(classRepo.findAuthorEmailByClassId((Long) jp.getArgs()[0]).get())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@Before("""
			commentController() && execution(* updateComment(..)) ||
			commentController() && execution(* deleteComment(..))""")
	void beforeMethodsInCommentControllerVerifyAuthUserIsAuthor(JoinPoint jp) {
		if (isNotAuthenticatedUser(commentRepo.findAuthorEmailByCommentId((Long) jp.getArgs()[0]).get())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@Before("""
			userController() && execution(* updateSpecialist(..)) ||
			userController() && execution(* updateStudent(..)) ||
			userController() && execution(* deleteSpecialist(..)) ||
			userController() && execution(* addToFinishedClassesOfStudent(..)) ||
			userController() && execution(* addToInProcessClassesOfStudent(..))""")
	void beforeMethodsInUserControllerVerifyAuthUserIsAuthor(JoinPoint jp) {
		UserDetails authenticatedPrincipal = getAuthenticatedPrincipal();
		Long userId = ((User) authenticatedPrincipal).getId();
		if (!userId.equals(jp.getArgs()[0])) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@Before("""
			passwordResetController() && execution(* generatePasswordResetToken(..))""")
	void beforeMethodsPasswordResetControllerVerifyAuthUserIsOwner1(JoinPoint jp) {
		UserDetails authenticatedPrincipal = getAuthenticatedPrincipal();
		String email = ((User) authenticatedPrincipal).getEmail();
		String actualEmail = ((PasswordResetTokenRequest) jp.getArgs()[0]).getUsername();
		if (Objects.equals(email, actualEmail)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@Before("""
			passwordResetController() && execution(* resetPassword(..))""")
	void beforeMethodsPasswordResetControllerVerifyAuthUserIsOwner2(JoinPoint jp) {
		UserDetails authenticatedPrincipal = getAuthenticatedPrincipal();
		Long userId = ((User) authenticatedPrincipal).getId();
		if (!userId.equals(jp.getArgs()[0])) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public static boolean isNotAuthenticatedUser(String actualUserUsername) {
		UserDetails authenticatedPrincipal = getAuthenticatedPrincipal();
		return !authenticatedPrincipal.getUsername().equals(actualUserUsername);
	}

	public static UserDetails getAuthenticatedPrincipal() {
		return (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
	}
}
