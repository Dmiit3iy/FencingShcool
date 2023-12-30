package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor

public class Training {

    private long id;


    @NonNull
    private int numberGym;
    //@JsonIgnore
    @NonNull

    private Trainer trainer;
    @JsonIgnore
    @NonNull

    private Apprentice apprentice;


    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;


    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime timeStart;

    @Override
    public String toString() {
        return "Дата: " + date+ " "+timeStart +" тренер: "+ trainer.getSurname()+" "+trainer.getName().substring(0,1)+
                "."+trainer.getSurname().substring(0,1)+". зал: "+numberGym;

    }
}
