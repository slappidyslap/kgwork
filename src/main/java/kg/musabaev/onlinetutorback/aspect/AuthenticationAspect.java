package kg.musabaev.onlinetutorback.aspect;

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
		UserDetails authenticatedPrincipal = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		Long userId = ((User) authenticatedPrincipal).getId();
		if (!userId.equals(jp.getArgs()[0])) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	public static boolean isNotAuthenticatedUser(String actualUserUsername) {
		UserDetails authenticatedPrincipal = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		return !authenticatedPrincipal.getUsername().equals(actualUserUsername);
	}
}
