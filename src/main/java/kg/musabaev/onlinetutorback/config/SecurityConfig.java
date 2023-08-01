package kg.musabaev.onlinetutorback.config;

import kg.musabaev.onlinetutorback.filter.TokenFilter;
import kg.musabaev.onlinetutorback.repository.UserRepo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SecurityConfig {

	UserRepo userRepo;
	TokenFilter tokenFilter;

	public SecurityConfig(UserRepo userRepo, @Lazy TokenFilter tokenFilter) {
		this.userRepo = userRepo;
		this.tokenFilter = tokenFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling(customizer -> customizer
						.accessDeniedHandler((req, res, e) -> res.setStatus(FORBIDDEN.value()))
						.authenticationEntryPoint(handle500statusAuthEntryPoint()))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers(HttpMethod.POST, "/users/specialists").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/students").permitAll()
						.requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/classes/**").permitAll()
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/api-docs/**", "/swagger-ui/**", "/actuator/**").permitAll()
						.anyRequest().authenticated())
				.build();
	}

	@Bean
	public AuthenticationEntryPoint handle500statusAuthEntryPoint() {
		return (req, res, e) -> {
			if (res.getStatus() == INTERNAL_SERVER_ERROR.value())
				res.setStatus(INTERNAL_SERVER_ERROR.value());
			else res.setStatus(UNAUTHORIZED.value());
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		log.debug("Fetching user...");
		return username -> userRepo.findByEmail(username)
				.orElseThrow(() -> {
					log.debug("User not found!");
					return new ResponseStatusException(UNAUTHORIZED);
				});
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}