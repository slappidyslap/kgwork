package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@DiscriminatorValue("student_user")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Student extends User {

	Integer age;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Builder.Default
	List<BaseClass> finishedClasses = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Builder.Default
	List<BaseClass> inProcessClasses = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "students_rated_specialists",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "specialist_id"))
	@Builder.Default
	List<Specialist> ratedSpecialists = new ArrayList<>();
}
