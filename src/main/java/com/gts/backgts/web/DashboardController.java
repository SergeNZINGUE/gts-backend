package com.gts.backgts.web;

import com.gts.backgts.dto.DashboardDepensesDto;
import com.gts.backgts.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * KPI tableau de bord — accessible à tous les rôles authentifiés.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/dashboard")
@PreAuthorize("isAuthenticated()")
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * GET /api/gts/dashboard/kpi-depenses?annee=2026
     * Si annee non fourni → année en cours.
     */
    @GetMapping("/kpi-depenses")
    public ResponseEntity<DashboardDepensesDto> kpiDepenses(
            @RequestParam(required = false) Integer annee
    ) {
        int a = (annee != null) ? annee : LocalDate.now().getYear();
        return ResponseEntity.ok(dashboardService.getDepenses(a));
    }
}
