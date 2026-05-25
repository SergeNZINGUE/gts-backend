package com.gts.backgts.web;

import com.gts.backgts.dto.MaintenanceRequest;
import com.gts.backgts.dto.MaintenanceResponse;
import com.gts.backgts.dto.MaintenanceSummaryResponse;
import com.gts.backgts.services.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    /** Créer une fiche de maintenance */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MaintenanceResponse> create(
            @Valid @RequestBody MaintenanceRequest request) {
        return ResponseEntity.ok(maintenanceService.createMaintenance(request));
    }

    /** Liste paginée de toutes les maintenances (tri par date desc) */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<Page<MaintenanceSummaryResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "dateDbtMaintenance"));
        return ResponseEntity.ok(maintenanceService.getAllMaintenances(pageable));
    }

    /** Détail complet d'une maintenance */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MaintenanceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.getMaintenanceById(id));
    }

    /** Liste des maintenances d'un engin donné */
    @GetMapping("/engin/{enginId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<List<MaintenanceSummaryResponse>> getByEngin(
            @PathVariable Long enginId) {
        return ResponseEntity.ok(maintenanceService.getMaintenancesByEngin(enginId));
    }

    /** Mettre à jour une fiche de maintenance */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MaintenanceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequest request) {
        return ResponseEntity.ok(maintenanceService.updateMaintenance(id, request));
    }

    /** Supprimer une fiche de maintenance */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
