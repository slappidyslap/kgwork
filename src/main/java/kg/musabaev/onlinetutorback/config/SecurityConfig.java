package kg.musabaev.onlinetutorback.config;

import kg.musabaev.onlinetutorback.filter.CsrfCookieFilter;
import kg.musabaev.onlinetutorback.filter.TokenFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SecurityConfig {

	UserDetailsService userDetailsService;
	TokenFilter tokenFilter;
	CsrfCookieFilter csrfCookieFilter;
	CorsConfigurationSource corsConfigurationSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
						.ignoringRequestMatchers("/auth/authenticate"))
				.cors(customizer -> customizer
						.configurationSource(corsConfigurationSource))
				.sessionManagement(customizer -> customizer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(customizer -> customizer
						.accessDeniedHandler((req, res, e) -> res.setStatus(FORBIDDEN.value()))
						.authenticationEntryPoint(handle500statusAuthEntryPoint()))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(csrfCookieFilter, BasicAuthenticationFilter.class)
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers(HttpMethod.POST, "/users/specialists").permitAll()
						.requestMatchers(HttpMethod.POST, "/users/students").permitAll()
						.requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/classes/**").permitAll()
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/api-docs/**", "/swagger-ui/**", "/actuator/**", "/csrf").permitAll()
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
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
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