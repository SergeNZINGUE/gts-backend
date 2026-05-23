package com.gts.backgts.web;

import com.gts.backgts.dto.EnginRequest;
import com.gts.backgts.dto.EnginResponse;
import com.gts.backgts.services.EnginsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/engins")
public class EnginsController {

    private final EnginsService enginsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EnginResponse> create(@RequestBody EnginRequest request) {
        return ResponseEntity.ok(enginsService.createEngin(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<List<EnginResponse>> getAll() {
        return ResponseEntity.ok(enginsService.getAllEngins());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EnginResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(enginsService.getEnginById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EnginResponse> update(@PathVariable Long id, @RequestBody EnginRequest request) {
        return ResponseEntity.ok(enginsService.updateEngin(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enginsService.deleteEngin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/desactive/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<EnginResponse> desactive(@PathVariable Long id) {
        return ResponseEntity.ok(enginsService.desactiveEngin(id));
    }
}
