package com.gts.backgts.repository;

import com.gts.backgts.entites.Conducteur;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.entites.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Optional<Maintenance> findById(Long id);
    Maintenance findByCodeMaintenance(String codeMaintenance);
    List<Maintenance> findByDateDbtMaintenanceBetween(Date dateDbtMaintenanceAfter, Date dateDbtMaintenanceBefore);
    List<Maintenance> findByEngins(Engins engins);

    List<Maintenance> findByDateDerniereRevisionAfter(LocalDate dateDerniereRevisionAfter);
    List<Maintenance> findByEnginsAndDateDbtMaintenanceBetween(Engins engins, Date dateDbtMaintenanceAfter, Date dateDbtMaintenanceBefore);
}
    