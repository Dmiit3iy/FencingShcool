package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

public class User {

    private long id;

    @NonNull
    private String login;

    @NonNull
    private String password;


    @NonNull
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate regDate = LocalDate.now();

}
