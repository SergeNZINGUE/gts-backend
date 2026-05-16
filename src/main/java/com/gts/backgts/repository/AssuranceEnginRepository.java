package com.gts.backgts.repository;

import com.gts.backgts.entites.AssuranceEngin;
import com.gts.backgts.enums.StatutAssurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssuranceEnginRepository extends JpaRepository<AssuranceEngin, Long>    {
    List<AssuranceEngin> findByEnginId(Long enginId);
    Optional<AssuranceEngin> findByEnginIdAndStatut(Long enginId, StatutAssurance statut);
    boolean existsByEngin_IdAndStatut(Long enginId, StatutAssurance statut);

    AssuranceEngin findAssuranceEnginById(Long id);
}
