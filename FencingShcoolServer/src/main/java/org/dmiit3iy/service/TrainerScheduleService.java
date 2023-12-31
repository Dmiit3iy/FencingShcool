package org.dmiit3iy.service;

import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainerScheduleService {

    TrainerSchedule add(long id, String day, LocalTime start, LocalTime end);

    TrainerSchedule get(long id);

    // TrainerSchedule update(TrainerSchedule trainerSchedule);
    TrainerSchedule delete(long id, String day);

    TrainerSchedule update(TrainerSchedule trainerSchedule);


}
