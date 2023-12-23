package org.dmiit3iy.service;

import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.Training;
import org.dmiit3iy.repository.ApprenticeRepository;
import org.dmiit3iy.repository.TrainerRepository;
import org.dmiit3iy.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TrainingsServiceImp implements TrainingsService {
    private TrainingRepository trainingRepository;
    private TrainerRepository trainerRepository;
    private ApprenticeRepository apprenticeRepository;

    @Autowired
    public void setTrainingRepository(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Autowired
    public void setApprenticeRepository(ApprenticeRepository apprenticeRepository) {
        this.apprenticeRepository = apprenticeRepository;
    }

    @Override
    public Training add(long idTrainer, long idApprentice, int numberGym, LocalDate date, LocalTime startTime) {
        Trainer trainer = this.trainerRepository.getById(idTrainer);
        Apprentice apprentice = this.apprenticeRepository.getById(idApprentice);
        if (isWorkingTime(idTrainer, date, startTime) && isGymNotFull(numberGym, date, startTime) && isTrainerBusy(idTrainer, date, startTime)) {
            try {
                Training training = new Training(numberGym, trainer, apprentice, date, startTime);
                return trainingRepository.save(training);
            } catch (DataIntegrityViolationException e) {
                throw new IllegalArgumentException("This training is already added");
            }
        } else throw new IllegalArgumentException("There is currently no way to sign up for a training session.");
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

    /**
     * Метод для определения того, что тренировка приходится в рабочее время тренера
     *
     * @param idTrainer
     * @param date
     * @param time
     * @return
     */
    public boolean isWorkingTime(long idTrainer, LocalDate date, LocalTime time) {

        LocalTime timeEndOfTraining = time.plusMinutes(90);
        String day = date.getDayOfWeek().toString().toLowerCase();
        TrainerSchedule trainerSchedule = this.trainerRepository.getById(idTrainer).getTrainerSсhedul();
        try {
            Field field1 = trainerSchedule.getClass().getDeclaredField(day + "Start");
            field1.setAccessible(true);
            LocalTime start = (LocalTime) field1.get(trainerSchedule);
            Field field2 = trainerSchedule.getClass().getDeclaredField(day + "End");
            field2.setAccessible(true);
            LocalTime end = (LocalTime) field2.get(trainerSchedule);
            if (!isOverlapping(start, end, time, timeEndOfTraining)) {
                throw new IllegalArgumentException("Are you trying to make an appointment outside of business hours");
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

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
            if (isOverlapping(training.getTimeStart(), training.getTimeStart().plusMinutes(90), time, timeEndOfTraining))
                countOfTraining++;
        }
        if (countOfTraining > 10) {
            throw new IllegalArgumentException("At this time, 10 apprentices are already engaged in the gym");
        }
        return true;
    }


    public boolean isTrainerBusy(long idTrainer, LocalDate date, LocalTime time) {
        int countOfApprentice = 0;
        LocalTime timeEndOfTraining = time.plusMinutes(90);
        List<Training> trainingList = this.trainingRepository.findTrainingByTrainerIdAndDate(idTrainer, date);
        for (Training training : trainingList) {
            if (isOverlapping(training.getTimeStart(), training.getTimeStart().plusMinutes(90), time, timeEndOfTraining))
                countOfApprentice++;
        }
        if (countOfApprentice >= 3) {
            throw new IllegalArgumentException("The coach has already enrolled 3 apprentice");
        }
        return true;
    }

    /**
     * Метод для определения пересечений временных отрезков
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

}
