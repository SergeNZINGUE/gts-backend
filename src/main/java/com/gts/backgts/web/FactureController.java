package com.gts.backgts.web;

import com.gts.backgts.dto.FactureMissionsRequest;
import com.gts.backgts.dto.FactureRequest;
import com.gts.backgts.dto.FactureResponse;
import com.gts.backgts.services.FactureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/factures")
public class FactureController {

    private final FactureService factureService;

    @PostMapping
    public ResponseEntity<FactureResponse> create(@Valid @RequestBody FactureRequest request) {
        return ResponseEntity.ok(factureService.createFacture(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<FactureResponse>> getAll() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FactureResponse> update(@PathVariable Long id, @RequestBody FactureRequest request) {
        return ResponseEntity.ok(factureService.updateFacture(id, request));
    }

    @PatchMapping("/{id}/etat")
    public ResponseEntity<FactureResponse> updateEtat(@PathVariable Long id, @RequestBody FactureRequest request) {
        return ResponseEntity.ok(factureService.updateEtatFacture(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/missions")
    public ResponseEntity<FactureResponse> affecterMissions(
            @PathVariable Long id,
            @RequestBody FactureMissionsRequest request
    ) {
        return ResponseEntity.ok(factureService.affecterMissionsAFacture(id, request));
    }
}
