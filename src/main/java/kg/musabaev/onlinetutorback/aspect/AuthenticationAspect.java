package kg.musabaev.onlinetutorback.aspect;

import kg.musabaev.onlinetutorback.repository.BaseClassRepo;
import kg.musabaev.onlinetutorback.repository.CommentRepo;
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

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.CommentController)")
	void commentController() {
	}

	@Pointcut("within(kg.musabaev.onlinetutorback.controller.ClassController)")
	void classController() {
	}

	@Before("""
			classController() && execution(* updateGroupClass(..)) ||
			classController() && execution(* updateIndividualClass(..))""")
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

	public static boolean isNotAuthenticatedUser(String actualUserUsername) {
		UserDetails authenticatedPrincipal = (UserDetails) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		return !authenticatedPrincipal.getUsername().equals(actualUserUsername);
	}
}
