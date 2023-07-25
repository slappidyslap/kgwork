package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(name = "group_classes")
@DiscriminatorValue("group_class")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class GroupClass extends BaseClass {
    @Column(nullable = false)
    Instant startDateTime;
    @Column(nullable = false)
    Instant finishDateTime;
}
