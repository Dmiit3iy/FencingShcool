package org.dmiit3iy.service;


import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.repository.TrainerScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TrainerScheduleServiceImp implements TrainerScheduleService {
    private TrainerService trainerService;
    private TrainerScheduleRepository trainerScheduleRepository;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }



    @Autowired
    public void setTrainerScheduleRepository(TrainerScheduleRepository trainerScheduleRepository) {
        this.trainerScheduleRepository = trainerScheduleRepository;
    }

    //проверить
    @Override
    public TrainerSchedule add(long id, String day, LocalTime start, LocalTime end) {
        Trainer trainer = trainerService.get(id);
        TrainerSchedule trainerSchedule = trainer.getTrainerSchedul();
        if (trainerSchedule == null) {
            trainerSchedule = new TrainerSchedule();
            trainerSchedule.setTrainer(trainer);
        }
        trainerSchedule.setSchedule(day, start, end);
        this.trainerScheduleRepository.save(trainerSchedule);
        return trainerSchedule;
    }

    // получение всего расписания
    @Override
    public TrainerSchedule get(long id) {
        Trainer trainer = this.trainerService.get(id);
        return  trainer.getTrainerSchedul();
    }


    // удаление на вход id и день недели(установить туда null)
    @Override
    public TrainerSchedule delete(long id, String day) {
        Trainer trainer = this.trainerService.get(id);

        TrainerSchedule trainerSchedule = trainer.getTrainerSchedul();
        trainerSchedule.setSchedule(day,null,null);
        trainerScheduleRepository.save(trainerSchedule);
        return trainerSchedule;
    }

    @Override
    public TrainerSchedule update(TrainerSchedule trainerSchedule) {
        TrainerSchedule base = get(trainerSchedule.getId());
        base.setSundayEnd(trainerSchedule.getSundayEnd());
        base.setSundayStart(trainerSchedule.getSundayStart());
        base.setMondayEnd(trainerSchedule.getMondayEnd());
        base.setMondayStart(trainerSchedule.getMondayEnd());
        base.setTuesdayEnd(trainerSchedule.getTuesdayEnd());
        base.setThursdayStart(trainerSchedule.getThursdayStart());
        base.setWednesdayEnd(trainerSchedule.getWednesdayEnd());
        base.setWednesdayStart(trainerSchedule.getWednesdayEnd());
        base.setThursdayEnd(trainerSchedule.getThursdayEnd());
        base.setThursdayStart(trainerSchedule.getThursdayStart());
        base.setFridayEnd(trainerSchedule.getFridayEnd());
        base.setFridayStart(trainerSchedule.getFridayStart());
        base.setSaturdayEnd(trainerSchedule.getSaturdayEnd());
        base.setSaturdayStart(trainerSchedule.getFridayEnd());
        this.trainerScheduleRepository.save(base);
        return trainerSchedule;
    }




}
