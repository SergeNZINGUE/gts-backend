package com.gts.backgts.web;

import com.gts.backgts.dto.MissionsRequest;
import com.gts.backgts.dto.MissionsResponse;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.enums.ModeCloture;
import com.gts.backgts.enums.StatutMission;
import com.gts.backgts.services.MissionsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/missions")
public class MissionsController {

    private final MissionsService missionsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MissionsResponse> create(@Valid @RequestBody MissionsRequest request) {
        return ResponseEntity.ok(missionsService.createMission(request));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR','COMPTABLE')")
    public ResponseEntity<List<MissionsResponse>> getAll() {
        return ResponseEntity.ok(missionsService.getAllMissions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR','COMPTABLE')")
    public ResponseEntity<MissionsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(missionsService.getMissionById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MissionsResponse> update(@PathVariable Long id, @RequestBody MissionsRequest request) {
        return ResponseEntity.ok(missionsService.updateMission(id, request));
    }

    @PutMapping("/{id}/terminer")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MissionsResponse> terminerMission(@PathVariable Long id, @RequestBody MissionsRequest request) {
        return ResponseEntity.ok(missionsService.terminerMission(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        missionsService.deleteMission(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cloturer")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<MissionsResponse> cloturerMission(@PathVariable Long id) {
        return ResponseEntity.ok(missionsService.cloturerMission(id, ModeCloture.MANUEL));
    }
}
