package org.dmiit3iy.service;

import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.Training;
import org.dmiit3iy.repository.ApprenticeRepository;
import org.dmiit3iy.repository.TrainerRepository;
import org.dmiit3iy.repository.TrainingRepository;
import org.dmiit3iy.util.Overlapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainingsServiceImp implements TrainingsService {
    private TrainingRepository trainingRepository;
    private TrainerService trainerService;

    @Autowired
    public void setApprenticeService(ApprenticeService apprenticeService) {
        this.apprenticeService = apprenticeService;
    }

    private ApprenticeService apprenticeService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setTrainingRepository(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    @Override
    public Training add(long idTrainer, long idApprentice, int numberGym, LocalDate date, LocalTime startTime) {
        Trainer trainer = this.trainerService.get(idTrainer);
        Apprentice apprentice = this.apprenticeService.get(idApprentice);
        if (trainer.getTrainerSchedul().isOverLapping(date, startTime)
                && isGymNotFull(numberGym, date, startTime)
                && !isTrainerBusy(idTrainer, date, startTime)) {
            try {
                Training training = new Training(numberGym, trainer, apprentice, date, startTime);
                return trainingRepository.save(training);
            } catch (DataIntegrityViolationException e) {
                throw new IllegalArgumentException("This training is already added");
            }
        } else
            throw new IllegalArgumentException("There is currently no way to sign up for a training session.");
    }

    @Override
    public Training get(long id) {
        return this.trainingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Training not found"));
    }

    @Override
    public List<Training> getByTrainerId(long id) {
        return this.trainingRepository.findTrainingByTrainerId(id);
    }

    @Override
    public List<Training> getByApprenticeId(long id) {
        return this.trainingRepository.findTrainingByApprenticeId(id);
    }


    @Override
    public Training delete(long id) {
        Training training = get(id);
        this.trainingRepository.deleteById(id);
        return training;
    }

    @Override
    public List<LocalTime> getTime(long idTrainer, LocalDate date, int numGym) {
        List<LocalTime> timeList = new ArrayList<>();
        Trainer trainer = this.trainerService.get(idTrainer);
        String dateOfTheWeek = date.getDayOfWeek().toString().toLowerCase();
        TrainerSchedule trainerSchedule = trainer.getTrainerSchedul();
        LocalTime[] localTimes = trainerSchedule.getTimePeriod(dateOfTheWeek);
        LocalTime start = localTimes[0];
        System.out.println(start);
        System.out.println(localTimes[1]);
        while (start.isBefore(localTimes[1])) {
            //если свободно то добавлять нет 3 трен и 10 человек в зале
            if (!isTrainerBusy(trainer.getId(), date, start) && isGymNotFull(numGym, date, start)) {
                timeList.add(start);
            }
            start = start.plusMinutes(90);
        }


        return timeList;
    }

    @Override
    public Boolean getAnyFreeTime(long idTrainer, LocalDate date) {
        List<LocalTime> timeList = new ArrayList<>();
        Trainer trainer = this.trainerService.get(idTrainer);
        String dateOfTheWeek = date.getDayOfWeek().toString().toLowerCase();
        TrainerSchedule trainerSchedule = trainer.getTrainerSchedul();
        LocalTime[] localTimes = trainerSchedule.getTimePeriod(dateOfTheWeek);
        LocalTime start = localTimes[0];
        System.out.println(start);
        System.out.println(localTimes[1]);
        while (start.isBefore(localTimes[1])) {
            //если свободно то добавлять нет 3 трен и 10 человек в зале
            if (!isTrainerBusy(trainer.getId(), date, start)) {
                timeList.add(start);
            }
            start = start.plusMinutes(90);
        }

        if (timeList.isEmpty()) {
            return false;
        } else return true;
    }


//    /**
//     * Метод для определения того, что тренировка приходится в рабочее время тренера
//     *
//     * @param idTrainer
//     * @param date
//     * @param time
//     * @return
//     */
//    public boolean isWorkingTime(long idTrainer, LocalDate date, LocalTime time) {
//
//        LocalTime timeEndOfTraining = time.plusMinutes(90);
//        String day = date.getDayOfWeek().toString().toLowerCase();
//        TrainerSchedule trainerSchedule = this.trainerService.get(idTrainer).getTrainerSchedul();
//        try {
//            Field field1 = trainerSchedule.getClass().getDeclaredField(day + "Start");
//            field1.setAccessible(true);
//            LocalTime start = (LocalTime) field1.get(trainerSchedule);
//            Field field2 = trainerSchedule.getClass().getDeclaredField(day + "End");
//            field2.setAccessible(true);
//            LocalTime end = (LocalTime) field2.get(trainerSchedule);
//            if (!isOverlapping(start, end, time, timeEndOfTraining)) {
//                throw new IllegalArgumentException("Are you trying to make an appointment outside of business hours");
//            }
//            return true;
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new IllegalArgumentException(e.getMessage());
//        }
//    }

    /**
     * Метод для расчета наполняемости зала
     *
     * @param numberOfGym
     * @param date
     * @param time
     * @return
     */
    public boolean isGymNotFull(int numberOfGym, LocalDate date, LocalTime time) {
        int countOfTraining = 0;
        LocalTime timeEndOfTraining = time.plusMinutes(90);
        List<Training> trainingList = this.trainingRepository.findTrainingByNumberGymAndAndDate(numberOfGym, date);
        for (Training training : trainingList) {
            if (Overlapping.isOverlapping(training.getTimeStart(), training.getTimeStart().plusMinutes(90),
                    time, timeEndOfTraining))
                countOfTraining++;
        }
        if (countOfTraining >= 10) {
            throw new IllegalArgumentException("At this time, 10 apprentices are already engaged in the gym");
        }
        return true;
    }


    public boolean isTrainerBusy(long idTrainer, LocalDate date, LocalTime time) {
        int countOfApprentice = 0;
        LocalTime timeEndOfTraining = time.plusMinutes(90);
        List<Training> trainingList = this.trainingRepository.findTrainingByTrainerIdAndDate(idTrainer, date);
        for (Training training : trainingList) {
            if (Overlapping.isOverlapping(training.getTimeStart(),
                    training.getTimeStart().plusMinutes(90), time, timeEndOfTraining))
                countOfApprentice++;
        }
        if (countOfApprentice >= 3) {
            //throw new IllegalArgumentException("The coach has already enrolled 3 apprentice");
            return true;
        }
        return false;
    }

//    /**
//     * Метод для определения пересечений временных отрезков
//     *
//     * @param start1
//     * @param end1
//     * @param start2
//     * @param end2
//     * @return
//     */
//    public static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
//        return start1.isBefore(end2) && start2.isBefore(end1);
//    }

}
