package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.dmiit3iy.util.Constants;


import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TrainerSchedule {

    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime mondayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime mondayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tuesdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tuesdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wednesdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wednesdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thursdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thursdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime fridayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime fridayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime saturdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime saturdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sundayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sundayEnd;



    @ToString.Exclude
    @NonNull
    @JsonIgnore
    private Trainer trainer;

    public List<TrainerScheduleForTable> convertScheduleForTable() throws NoSuchFieldException, IllegalAccessException {
        List<TrainerScheduleForTable> trainerScheduleForTableList = new ArrayList<>();
        for (int i = 0; i < Constants.dayWeekEng.length; i++) {
            Field field1 = this.getClass().getDeclaredField(Constants.dayWeekEng[i] + "Start");
            LocalTime start = (LocalTime) field1.get(this);
            Field field2 = this.getClass().getDeclaredField(Constants.dayWeekEng[i] + "End");
            LocalTime end = (LocalTime) field2.get(this);
            if(start!=null&&end!=null){
                TrainerScheduleForTable trainerScheduleForTable
                        = new TrainerScheduleForTable(Constants.dayWeekRus[i], Constants.dayWeekEng[i],start,end);
                trainerScheduleForTableList.add(trainerScheduleForTable);
            } else {
                TrainerScheduleForTable trainerScheduleForTable= new TrainerScheduleForTable();
                trainerScheduleForTableList.add(trainerScheduleForTable);
            }
        }
        return trainerScheduleForTableList;
    }


}
