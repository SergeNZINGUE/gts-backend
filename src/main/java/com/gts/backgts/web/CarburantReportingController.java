package com.gts.backgts.web;

import com.gts.backgts.dto.BilanCarburantEnginDto;
import com.gts.backgts.dto.ConsommationMissionDto;
import com.gts.backgts.dto.RapportCarburantResponse;
import com.gts.backgts.services.CarburantReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Reporting carburant — accessible ADMIN, MANAGER, OPERATEUR, COMPTABLE.
 * Déclaré sur un chemin distinct de /api/gts/reporting/** pour permettre
 * l'accès OPERATEUR sans lever la restriction générale du ReportingController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/reporting/carburant")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR','COMPTABLE')")
public class CarburantReportingController {

    private final CarburantReportingService carburantReportingService;

    // ── C1 : Rapport global flotte ──────────────────────────────────────────
    /**
     * GET /api/gts/reporting/carburant?dateDebut=YYYY-MM-DD&dateFin=YYYY-MM-DD[&enginId=N]
     * Retourne le rapport complet : KPI flotte + bilan par engin.
     */
    @GetMapping
    public ResponseEntity<RapportCarburantResponse> rapportCarburant(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Long enginId
    ) {
        return ResponseEntity.ok(
                carburantReportingService.getRapport(dateDebut, dateFin, enginId));
    }

    // ── C2 : Bilan d'un engin ───────────────────────────────────────────────
    /**
     * GET /api/gts/reporting/carburant/engin/{id}?dateDebut=&dateFin=
     * Retourne le bilan détaillé d'un seul engin (incluant le détail mission par mission).
     */
    @GetMapping("/engin/{id}")
    public ResponseEntity<BilanCarburantEnginDto> bilanEngin(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        RapportCarburantResponse rapport =
                carburantReportingService.getRapport(dateDebut, dateFin, id);
        if (rapport.bilansParEngin().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rapport.bilansParEngin().get(0));
    }

    // ── C3 : Liste plate des missions avec détail carburant ─────────────────
    /**
     * GET /api/gts/reporting/carburant/missions?dateDebut=&dateFin=[&enginId=]
     * Retourne la liste flat de toutes les ConsommationMissionDto (complètes ou partielles).
     */
    @GetMapping("/missions")
    public ResponseEntity<List<ConsommationMissionDto>> detailMissions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) Long enginId
    ) {
        RapportCarburantResponse rapport =
                carburantReportingService.getRapport(dateDebut, dateFin, enginId);
        List<ConsommationMissionDto> missions = rapport.bilansParEngin().stream()
                .flatMap(b -> b.detailMissions().stream())
                .toList();
        return ResponseEntity.ok(missions);
    }
}
