package org.dmiit3iy.repository;

import org.dmiit3iy.model.TrainerSсhedul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerSсhedulRepository extends JpaRepository<TrainerSсhedul, Long> {
}
