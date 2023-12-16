package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apprentices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Apprentice {
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

    @NonNull
    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "apprentice")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Training> trainingList;
}
