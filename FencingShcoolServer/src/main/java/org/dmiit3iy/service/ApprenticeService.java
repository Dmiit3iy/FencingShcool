package org.dmiit3iy.service;

import org.dmiit3iy.model.Apprentice;

import java.util.List;

public interface ApprenticeService {
    void add(Apprentice apprentice);
    List<Apprentice> get();
    Apprentice get(long id);
    Apprentice update(Apprentice apprentice);
    Apprentice delete(Long id);
}
