package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trainers", uniqueConstraints = {@UniqueConstraint(columnNames = {"surname", "name", "patronymic"})})
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String surname;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String patronymic;

    @Column(nullable = false)
    @NonNull
    private double experience;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Training> trainingList;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trainer")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    @PrimaryKeyJoinColumn
    private TrainerSchedule trainerSchedul;

}
