package kg.musabaev.onlinetutorback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@ToString(exclude = "password")
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "email")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String surname;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	Gender gender;
	@Column(nullable = false, unique = true)
	String phoneNumber;
	@Column(nullable = false, unique = true)
	String email;
	@Column(nullable = false)
	@JsonIgnore
	String password;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	Role role;
	@Column(nullable = false)
	String avatarUrl;
	@Column(nullable = false)
	String region;
	@Column(nullable = false)
	String education;
	@Builder.Default
	@Column(nullable = false, updatable = false)
	LocalDate createdDate = LocalDate.now();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public enum Gender {
		MALE, FEMALE
	}

	public enum Role {
		ROLE_SPECIALIST, ROLE_STUDENT
	}
}
