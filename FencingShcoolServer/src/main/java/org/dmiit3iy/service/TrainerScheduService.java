package org.dmiit3iy.service;

import org.dmiit3iy.model.TrainerSсhedul;

public interface TrainerScheduService {
    void add(long id, TrainerSсhedul trainerSсhedul);
    TrainerSсhedul get(long id);
    TrainerSсhedul update(TrainerSсhedul trainerSсhedul);
    TrainerSсhedul delete(long id);
}
