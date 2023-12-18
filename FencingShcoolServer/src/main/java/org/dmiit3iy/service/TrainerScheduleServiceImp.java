package org.dmiit3iy.service;


import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.repository.TrainerRepository;
import org.dmiit3iy.repository.TrainerScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
@Service
public class TrainerScheduleServiceImp implements TrainerScheduleService {
    private TrainerRepository trainerRepository;
    private TrainerScheduleRepository trainerScheduleRepository;

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Autowired
    public void setTrainerScheduleRepository(TrainerScheduleRepository trainerScheduleRepository) {
        this.trainerScheduleRepository = trainerScheduleRepository;
    }
//проверить
    @Override
    public TrainerSchedule add(long id, String day, LocalTime start, LocalTime end) {
        Trainer trainer = this.trainerRepository.getById(id);
        TrainerSchedule trainerSchedule = trainer.getTrainerSсhedul();
        if(trainerSchedule == null){
            trainerSchedule = new TrainerSchedule();
            trainerSchedule.setTrainer(trainer);
        }
        trainerSchedule.setScedule(day,start,end);
        this.trainerScheduleRepository.save(trainerSchedule);

        return trainerSchedule;
    }
// удаление на вход id и день недели(установить туда null)
    // получение всего расписания
    @Override
    public TrainerSchedule get(long id) {
        return this.trainerScheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule does not exists!"));
    }

    @Override
    public TrainerSchedule update(TrainerSchedule trainerSchedule) {
        TrainerSchedule base = get(trainerSchedule.getId());
        base.setFridayEnd(trainerSchedule.getFridayEnd());
        base.setFridayStart(trainerSchedule.getFridayStart());
        base.setMondayEnd(trainerSchedule.getMondayEnd());
        base.setMondayStart(trainerSchedule.getMondayStart());
        base.setThursdayEnd(trainerSchedule.getThursdayEnd());
        base.setTuesdayStart(trainerSchedule.getTuesdayStart());
        base.setWednesdayEnd(trainerSchedule.getWednesdayEnd());
        base.setWednesdayEnd(trainerSchedule.getWednesdayEnd());
        base.setMondayEnd(trainerSchedule.getMondayEnd());
        base.setMondayStart(trainerSchedule.getMondayStart());
        base.setSaturdayEnd(trainerSchedule.getSaturdayEnd());
        base.setSaturdayStart(trainerSchedule.getSaturdayStart());
        base.setSundayEnd(trainerSchedule.getSundayEnd());
        base.setSundayStart(trainerSchedule.getSundayStart());
        try {
            this.trainerScheduleRepository.save(base);
            return trainerSchedule;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("This schedule is already exists!");
        }
    }

    @Override
    public TrainerSchedule delete(long id) {
        TrainerSchedule trainerSchedule = get(id);
        this.trainerScheduleRepository.deleteById(id);
        return trainerSchedule;
    }
}
