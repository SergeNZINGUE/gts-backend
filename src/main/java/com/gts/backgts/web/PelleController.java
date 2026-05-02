package com.gts.backgts.web;

import com.gts.backgts.dto.PelleRequest;
import com.gts.backgts.dto.PelleResponse;
import com.gts.backgts.services.PelleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/pelles")
public class PelleController {

    private final PelleService pelleService;

    @PostMapping
    public ResponseEntity<PelleResponse> create(@Valid @RequestBody PelleRequest request) {
        return ResponseEntity.ok(pelleService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PelleResponse>> getAll() {
        return ResponseEntity.ok(pelleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PelleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pelleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PelleResponse> update(@PathVariable Long id, @Valid @RequestBody PelleRequest request) {
        return ResponseEntity.ok(pelleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pelleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
