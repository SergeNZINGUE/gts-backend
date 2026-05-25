package com.gts.backgts.web;

import com.gts.backgts.dto.PieceRechangeRequest;
import com.gts.backgts.dto.PieceRechangeResponse;
import com.gts.backgts.services.PieceRechangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/pieces-rechange")
public class PieceRechangeController {

    private final PieceRechangeService pieceRechangeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<PieceRechangeResponse> create(@Valid @RequestBody PieceRechangeRequest request) {
        return ResponseEntity.ok(pieceRechangeService.createPieceRechange(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<List<PieceRechangeResponse>> getAll() {
        return ResponseEntity.ok(pieceRechangeService.getAllPiecesRechange());
    }

    @GetMapping("/en-alerte")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<List<PieceRechangeResponse>> getEnAlerte() {
        return ResponseEntity.ok(pieceRechangeService.getPiecesEnAlerte());
    }

    /** Ajustement rapide de stock : PATCH /{id}/stock?delta=+5 ou delta=-2 */
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<PieceRechangeResponse> ajusterStock(
            @PathVariable Long id,
            @RequestParam int delta) {
        return ResponseEntity.ok(pieceRechangeService.ajusterStock(id, delta));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<PieceRechangeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pieceRechangeService.getPieceRechangeById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<PieceRechangeResponse> update(@PathVariable Long id, @RequestBody PieceRechangeRequest request) {
        return ResponseEntity.ok(pieceRechangeService.updatePieceRechange(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pieceRechangeService.deletePieceRechange(id);
        return ResponseEntity.noContent().build();
    }
}
