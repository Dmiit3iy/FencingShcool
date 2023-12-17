package org.dmiit3iy.service;


import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.repository.ApprenticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ApprenticeServiceImp implements ApprenticeService {
    private ApprenticeRepository apprenticeRepository;

    @Autowired
    public void setApprenticeRepository(ApprenticeRepository apprenticeRepository) {
        this.apprenticeRepository = apprenticeRepository;
    }

    @Override
    public void add(Apprentice apprentice) {
        try {
            apprenticeRepository.save(apprentice);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This apprentice already added!");
        }
    }

    @Override
    public List<Apprentice> get() {
        return apprenticeRepository.findAll();
    }

    @Override
    public Apprentice get(long id) {
        return apprenticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Apprentice does not exists!"));
    }

    @Override
    public Apprentice update(Apprentice apprentice) {
        Apprentice base = apprenticeRepository.getById(apprentice.getId());
        base.setName(apprentice.getName());
        base.setPatronymic(apprentice.getPatronymic());
        base.setPhoneNumber(apprentice.getPhoneNumber());
        base.setSurname(apprentice.getSurname());
        try {
            apprenticeRepository.save(base);
            return apprentice;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This apprentice is already exists!");
        }
    }

    @Override
    public Apprentice delete(Long id) {
        Apprentice apprentice = get(id);
        apprenticeRepository.deleteById(id);
        return apprentice;
    }
}
