package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(
		name = "categories",
		indexes = @Index(
				name = "categories_name_idx",
				columnList = "name",
				unique = true))
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(nullable = false, unique = true)
	String name;

	public Category(String name) {
		this.name = name;
	}
}
