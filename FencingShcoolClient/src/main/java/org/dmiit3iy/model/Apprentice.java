package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Apprentice {

    private long id;


    @NonNull
    private String surname;


    @NonNull
    private String name;


    @NonNull
    private String patronymic;

    @NonNull

    private String phoneNumber;

    @JsonIgnore
    @ToString.Exclude
    private List<Training> trainingList;

    @Override
    public String toString() {
        return
                "Фамилия: " + surname +
                        " Имя: " + name +
                        " Отчество:" + patronymic +
                        " номер телефона: " + phoneNumber;
    }
}
