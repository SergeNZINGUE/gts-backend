package com.gts.backgts.repository;

import com.gts.backgts.entites.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CamionRepository extends JpaRepository<Camion, Long> {
    boolean existsByCodeEngin(String codeEngin);
    boolean existsByImmatriculationEngin(String immatriculationEngin);
}
