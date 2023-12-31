package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "base_classes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "class_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@ToString(exclude = {"author"})
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"author", "title"}) // FIXME
public class BaseClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String title;
	@Column(nullable = false, length = 500)
	@Builder.Default
	String description = "";
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "author_id", nullable = false)
	Specialist author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	Category category;
	@Column(nullable = false)
	Integer price;
	@Column(nullable = false, updatable = false)
	@Builder.Default
	LocalDate createdDate = LocalDate.now();
	@Column(nullable = false)
	String coverUrl;
}
