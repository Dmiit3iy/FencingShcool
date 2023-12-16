package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
@Table
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private int numberGym;
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
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
