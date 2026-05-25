package com.gts.backgts.services;

import com.gts.backgts.dto.DashboardDepensesDto;
import com.gts.backgts.repository.ConsommationCarburantRepository;
import com.gts.backgts.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final ConsommationCarburantRepository consommationRepo;
    private final MaintenanceRepository           maintenanceRepo;

    /**
     * Calcule les KPI dépenses opérationnelles pour une année donnée.
     * Utilise des agrégats JPQL purs — aucune entité chargée en mémoire,
     * pas de risque de NullPointerException ni de MultipleBagFetchException.
     *
     * @param annee l'année cible (ex. 2026)
     */
    public DashboardDepensesDto getDepenses(int annee) {

        LocalDate     debut = LocalDate.of(annee, 1, 1);
        LocalDate     fin   = LocalDate.of(annee, 12, 31);
        LocalDateTime dbt   = debut.atStartOfDay();
        LocalDateTime fn    = fin.plusDays(1).atStartOfDay();

        // ── Carburant : SUM(quantiteLitres * prixUnitaire) ──────────────────
        Double carburant = consommationRepo.sumCoutCarburant(dbt, fn);
        double depensesCarburant = (carburant != null) ? carburant : 0.0;

        // ── Maintenance : SUM(coutMainOeuvre) + SUM(quantite * prixUnitaire) ──
        Double mo     = maintenanceRepo.sumCoutMainOeuvre(debut, fin);
        Double pieces = maintenanceRepo.sumCoutPieces(debut, fin);
        double depensesMaintenance = (mo != null ? mo : 0.0) + (pieces != null ? pieces : 0.0);

        double total = depensesCarburant + depensesMaintenance;

        return new DashboardDepensesDto(annee, depensesCarburant, depensesMaintenance, total);
    }
}
