package com.gts.backgts.repository;

import com.gts.backgts.entites.ActionIntervention;
import com.gts.backgts.enums.SystemeIntervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionInterventionRepository extends JpaRepository<ActionIntervention, Long> {

    List<ActionIntervention> findByMaintenanceId(Long maintenanceId);

    List<ActionIntervention> findBySysteme(SystemeIntervention systeme);
}
