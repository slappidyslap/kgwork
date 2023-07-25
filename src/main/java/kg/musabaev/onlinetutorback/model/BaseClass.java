package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"specialistId", "title"})
public class BaseClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String title;
	@Column(nullable = false, length = 500)
	@Builder.Default
	String description = "";
	Long specialistId; // FIXME
	@Column(nullable = false)
	Integer price;
	@Column(nullable = false, updatable = false)
	@Builder.Default
	LocalDate createdDate = LocalDate.now();
	@Column(nullable = false)
	String coverUrl;
}
