package com.gts.backgts.web;

import com.gts.backgts.dto.ReglementRequest;
import com.gts.backgts.dto.ReglementResponse;
import com.gts.backgts.services.ReglementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/reglements")
public class ReglementController {

    private final ReglementService reglementService;

    @PostMapping
    public ResponseEntity<ReglementResponse> create(@Valid @RequestBody ReglementRequest request) {
        return ResponseEntity.ok(reglementService.createReglement(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReglementResponse>> getAll() {
        return ResponseEntity.ok(reglementService.getAllReglements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReglementResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reglementService.getReglementById(id));
    }

    @GetMapping("/facture/{id}")
    public ResponseEntity<List<ReglementResponse>> getByFactureId(@PathVariable Long id) {
        return ResponseEntity.ok(reglementService.getReglementByFactureId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglementResponse> update(@PathVariable Long id, @RequestBody ReglementRequest request) {
        return ResponseEntity.ok(reglementService.updateReglement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reglementService.deleteReglement(id);
        return ResponseEntity.noContent().build();
    }
}
