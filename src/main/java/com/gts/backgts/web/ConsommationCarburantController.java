package com.gts.backgts.web;

import com.gts.backgts.dto.ConsommationCarburantRequest;
import com.gts.backgts.dto.ConsommationCarburantResponse;
import com.gts.backgts.services.ConsommationCarburantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/consommations-carburant")
public class ConsommationCarburantController {

    private final ConsommationCarburantService consommationCarburantService;

    @PostMapping
    public ResponseEntity<ConsommationCarburantResponse> create(@RequestBody ConsommationCarburantRequest request) {
        return ResponseEntity.ok(consommationCarburantService.createConsommationCarburant(request));
    }

    @GetMapping
    public ResponseEntity<List<ConsommationCarburantResponse>> getAll() {
        return ResponseEntity.ok(consommationCarburantService.getAllConsommationsCarburant());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsommationCarburantResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(consommationCarburantService.getConsommationCarburantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsommationCarburantResponse> update(@PathVariable Long id, @RequestBody ConsommationCarburantRequest request) {
        return ResponseEntity.ok(consommationCarburantService.updateConsommationCarburant(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consommationCarburantService.deleteConsommationCarburant(id);
        return ResponseEntity.noContent().build();
    }
}
