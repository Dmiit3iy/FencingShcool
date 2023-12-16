package org.dmiit3iy.service;

import org.dmiit3iy.model.Trainer;

import java.util.List;

public interface TrainerService {
    void add(Trainer trainer);
    List<Trainer> get();
    Trainer get(long id);
    Trainer update(Trainer trainer);
    Trainer delete(long id);
}
