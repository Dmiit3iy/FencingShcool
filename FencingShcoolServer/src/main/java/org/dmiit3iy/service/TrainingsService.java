package org.dmiit3iy.service;

import org.dmiit3iy.model.Training;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TrainingsService {
    void add(long idTrainer, long idApprentice, Training training);
    void add(long idTrainer, long idApprentice, int numberGym, LocalDate date, LocalTime startTime);
    Training get(long id);
    List<Training> getByTrainerId(long id);
    List<Training> getByApprenticeId(long id);
    Training delete(long id);

}
