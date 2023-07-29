package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString(exclude = {"baseClass", "userId"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	Long id;
	@Column(nullable = false)
	Long userId; // FIXME
	@Column(nullable = false, length = 500)
	@EqualsAndHashCode.Include
	String body;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "base_class_id")
	BaseClass baseClass;
	@Builder.Default
	@Column(nullable = false)
	LocalDate uploadedDate = LocalDate.now();

	@EqualsAndHashCode.Include
	private String getClassTitle() {
		return baseClass.getTitle();
	}
}
