package com.gts.backgts.web;

import com.gts.backgts.dto.ControleVGPRequest;
import com.gts.backgts.dto.ControleVGPResponse;
import com.gts.backgts.services.ControleVGPService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/controles-vgp")
public class ControleVGPController {

    private final ControleVGPService controleVGPService;

    @PostMapping
    public ResponseEntity<ControleVGPResponse> create(@Valid @RequestBody ControleVGPRequest request) {
        return ResponseEntity.ok(controleVGPService.createControleVGP(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ControleVGPResponse>> getAll() {
        return ResponseEntity.ok(controleVGPService.getAllControlesVGP());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControleVGPResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(controleVGPService.getControleVGPById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ControleVGPResponse> update(@PathVariable Long id, @RequestBody ControleVGPRequest request) {
        return ResponseEntity.ok(controleVGPService.updateControleVGP(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        controleVGPService.deleteControleVGP(id);
        return ResponseEntity.noContent().build();
    }
}
