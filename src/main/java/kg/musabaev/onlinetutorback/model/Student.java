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
	@OneToMany(fetch = FetchType.LAZY)
	@Builder.Default
	List<BaseClass> finishedClasses = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY)
	@Builder.Default
	List<BaseClass> inProcessClasses = new ArrayList<>();
}
