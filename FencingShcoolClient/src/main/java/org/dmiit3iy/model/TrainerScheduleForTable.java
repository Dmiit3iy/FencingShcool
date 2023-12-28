package org.dmiit3iy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerScheduleForTable {
    private String day;
    private String dayEng;
    private LocalTime start;
    private LocalTime end;
}
