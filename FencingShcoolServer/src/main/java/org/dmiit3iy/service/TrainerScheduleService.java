package org.dmiit3iy.service;

import org.dmiit3iy.model.TrainerSchedule;

import java.time.LocalTime;

public interface TrainerScheduleService {

   TrainerSchedule add(long id, String day, LocalTime start, LocalTime end);
    TrainerSchedule get(long id);
    TrainerSchedule update(TrainerSchedule trainerSchedule);
    TrainerSchedule delete(long id);
}
