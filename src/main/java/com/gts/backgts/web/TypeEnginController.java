package com.gts.backgts.web;

import com.gts.backgts.dto.TypeEnginRequest;
import com.gts.backgts.dto.TypeEnginResponse;
import com.gts.backgts.enums.FamilleEngin;
import com.gts.backgts.services.TypeEnginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/gts/types-engins")
@RequiredArgsConstructor
public class TypeEnginController {

    private final TypeEnginService typeEnginService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TypeEnginResponse>> getAll() {
        return ResponseEntity.ok(typeEnginService.getAll());
    }

    @GetMapping("/actifs")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TypeEnginResponse>> getActifs() {
        return ResponseEntity.ok(typeEnginService.getActifs());
    }

    @GetMapping("/famille/{famille}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TypeEnginResponse>> getByFamille(@PathVariable FamilleEngin famille) {
        return ResponseEntity.ok(typeEnginService.getByFamille(famille));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TypeEnginResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(typeEnginService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TypeEnginResponse> create(@RequestBody TypeEnginRequest request) {
        return ResponseEntity.ok(typeEnginService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TypeEnginResponse> update(@PathVariable Long id, @RequestBody TypeEnginRequest request) {
        return ResponseEntity.ok(typeEnginService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        typeEnginService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
