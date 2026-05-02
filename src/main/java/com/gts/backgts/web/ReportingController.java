package com.gts.backgts.web;

import com.gts.backgts.dto.*;
import com.gts.backgts.services.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/reporting")
public class ReportingController {

    private final ReportingService reportingService;

    // L1 — État des locations sur période
    @GetMapping("/locations/periode")
    public ResponseEntity<RapportLocationPeriodeResponse> locationsPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        return ResponseEntity.ok(reportingService.rapportLocationsPeriode(dateDebut, dateFin));
    }

    // L2 — Locations par client (période optionnelle)
    @GetMapping("/locations/client/{clientId}")
    public ResponseEntity<RapportLocationClientResponse> locationsClient(
            @PathVariable Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        return ResponseEntity.ok(reportingService.rapportLocationsClient(clientId, dateDebut, dateFin));
    }

    // M1 — Missions par conducteur (période optionnelle)
    @GetMapping("/missions/conducteur/{conducteurId}")
    public ResponseEntity<RapportMissionsConducteurResponse> missionsConducteur(
            @PathVariable Long conducteurId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin
    ) {
        return ResponseEntity.ok(reportingService.rapportMissionsConducteur(conducteurId, dateDebut, dateFin));
    }

    // F2 — Suivi des impayés (client optionnel)
    @GetMapping("/factures/impayes")
    public ResponseEntity<RapportImpayesResponse> impayes(
            @RequestParam(required = false) Long clientId
    ) {
        return ResponseEntity.ok(reportingService.rapportImpayes(clientId));
    }
}
