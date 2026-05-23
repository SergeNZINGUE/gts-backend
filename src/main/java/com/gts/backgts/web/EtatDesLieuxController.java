package com.gts.backgts.web;

import com.gts.backgts.dto.EtatDesLieuxRequest;
import com.gts.backgts.dto.EtatDesLieuxResponse;
import com.gts.backgts.services.EtatDesLieuxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/etats-des-lieux")
public class EtatDesLieuxController {

    private final EtatDesLieuxService etatDesLieuxService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EtatDesLieuxResponse> create(@Valid @ModelAttribute EtatDesLieuxRequest request) throws IOException {
        return ResponseEntity.ok(etatDesLieuxService.createEtatDesLieux(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<List<EtatDesLieuxResponse>> getAll() {
        return ResponseEntity.ok(etatDesLieuxService.getAllEtatsDesLieux());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EtatDesLieuxResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(etatDesLieuxService.getEtatDesLieuxById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EtatDesLieuxResponse> update(@PathVariable Long id, @ModelAttribute EtatDesLieuxRequest request) throws IOException {
        return ResponseEntity.ok(etatDesLieuxService.updateEtatDesLieux(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etatDesLieuxService.deleteEtatDesLieux(id);
        return ResponseEntity.noContent().build();
    }
}
