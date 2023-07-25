package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(
        name = "group_classes",
        indexes = @Index(
                name = "group_classes_title_idx",
                columnList = "title",
                unique = true))
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
