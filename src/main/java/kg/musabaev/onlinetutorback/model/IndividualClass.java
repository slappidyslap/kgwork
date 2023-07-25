package kg.musabaev.onlinetutorback.model;

import jakarta.persistence.*;
import kg.musabaev.onlinetutorback.util.converter.DurationInSecondsConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@Entity
@Table(name = "individual_classes")
@DiscriminatorValue("individual_class")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class IndividualClass extends BaseClass {
    @Column(nullable = false)
    @Convert(converter = DurationInSecondsConverter.class)
    Duration duration;
}
