package org.dmiit3iy.repository;

import org.dmiit3iy.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {
    Training findTrainingByTrainerId(long id);
    Training findTrainingByApprenticeId(long id);
}
