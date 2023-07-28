package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(exclude = {"owner", "token"})
@EqualsAndHashCode(of = "token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false, updatable = false, unique = true)
	User owner;

	@Column(unique = true, nullable = false)
	String token;

	@Column(nullable = false)
	Instant expiryDate;
}
