package org.dmiit3iy.repository;

import org.dmiit3iy.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {
    List<Training> findTrainingByTrainerId(long id);
    List<Training> findTrainingByApprenticeId(long id);
    List<Training> findTrainingByNumberGymAndAndDate(int numberGym, LocalDate localDate);
    List<Training> findTrainingByTrainerIdAndDate(long id, LocalDate localDate);
}
