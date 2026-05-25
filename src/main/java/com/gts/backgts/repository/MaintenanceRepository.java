package com.gts.backgts.repository;

import com.gts.backgts.entites.Engins;
import com.gts.backgts.entites.Maintenance;
import com.gts.backgts.enums.TypeMaintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Maintenance findByCodeMaintenance(String codeMaintenance);

    List<Maintenance> findByEngins(Engins engins);

    List<Maintenance> findByEnginsId(Long enginId);

    Page<Maintenance> findByEnginsId(Long enginId, Pageable pageable);

    List<Maintenance> findByType(TypeMaintenance type);

    List<Maintenance> findByDateDbtMaintenanceBetween(LocalDate debut, LocalDate fin);

    List<Maintenance> findByEnginsIdAndDateDbtMaintenanceBetween(
            Long enginId, LocalDate debut, LocalDate fin);

    Page<Maintenance> findAll(Pageable pageable);

    /**
     * Coût main-d'œuvre total des maintenances sur une période (agrégat pur).
     */
    @Query("""
        SELECT COALESCE(SUM(COALESCE(m.coutMainOeuvre, 0.0)), 0.0)
        FROM Maintenance m
        WHERE m.dateDbtMaintenance BETWEEN :debut AND :fin
        """)
    Double sumCoutMainOeuvre(
            @Param("debut") LocalDate debut,
            @Param("fin")   LocalDate fin);

    /**
     * Coût pièces consommées total sur une période (agrégat pur via PieceConsommee).
     */
    @Query("""
        SELECT COALESCE(SUM(p.quantite * COALESCE(p.prixUnitaire, 0.0)), 0.0)
        FROM PieceConsommee p
        WHERE p.maintenance.dateDbtMaintenance BETWEEN :debut AND :fin
        """)
    Double sumCoutPieces(
            @Param("debut") LocalDate debut,
            @Param("fin")   LocalDate fin);
}
