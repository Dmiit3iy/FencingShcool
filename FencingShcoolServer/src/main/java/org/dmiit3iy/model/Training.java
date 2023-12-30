package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"apprentice_id", "date", "timeStart"}))
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private int numberGym;
   // @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "apprentice_id", nullable = false)
    private Apprentice apprentice;

    @Column(nullable = false)
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(nullable = false)
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeStart;

}
