package org.dmiit3iy.service;

import org.dmiit3iy.model.Training;

public interface TrainingsService {
    void add(long idTrainer, long idApprentice, Training training);
    Training get(long id);
    Training getByTrainerId(long id);
    Training getByApprenticeId(long id);
    Training delete(long id);

}
