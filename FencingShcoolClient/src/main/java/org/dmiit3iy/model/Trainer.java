package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Trainer {

    private long id;


    @NonNull
    private String surname;


    @NonNull
    private String name;


    @NonNull
    private String patronymic;


    @NonNull
    private double experience;

    @JsonIgnore
    @ToString.Exclude

    private List<Training> trainingList;

    @JsonIgnore
    @ToString.Exclude
    private TrainerSchedule trainerSchedule;

}
