package org.dmiit3iy.service;

import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImp implements TrainerService {

    private TrainerRepository trainerRepository;

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void add(Trainer trainer) {
        try {
            trainerRepository.save(trainer);
        } catch (DataIntegrityViolationException  e) {
            throw new IllegalArgumentException("This trainer is already added");
        }
    }

    @Override
    public List<Trainer> get() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer get(long id) {
        return trainerRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Trainer does not exists!"));
    }

    @Override
    public Trainer update(Trainer trainer) {
        Trainer base = get(trainer.getId());
        base.setName(trainer.getName());
        base.setSurname(trainer.getSurname());
        base.setExperience(trainer.getExperience());
        base.setPatronymic(trainer.getPatronymic());

        return this.trainerRepository.save(base);
    }

    @Override
    public Trainer delete(long id) {
        Trainer trainer = get(id);
        trainerRepository.deleteById(id);
        return trainer;
    }
}
