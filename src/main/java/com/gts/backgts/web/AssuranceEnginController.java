package com.gts.backgts.web;

import com.gts.backgts.dto.AssuranceEnginRequest;
import com.gts.backgts.dto.AssuranceEnginResponse;
import com.gts.backgts.enums.StatutAssurance;
import com.gts.backgts.services.AssuranceEnginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/assurances")

public class AssuranceEnginController {
    private final AssuranceEnginService assuranceEnginService;

    @PostMapping
    public ResponseEntity<AssuranceEnginResponse> create(@RequestBody AssuranceEnginRequest request) {
        return ResponseEntity.ok(assuranceEnginService.createAssuranceEngin(request));
    }
    @GetMapping("/engin/{enginId}")
    public ResponseEntity<List<AssuranceEnginResponse>> getByEngin(@PathVariable Long enginId) {
        return ResponseEntity.ok(assuranceEnginService.getAssurancesByEngin(enginId));
    }
    @GetMapping("/list")
    public ResponseEntity<List<AssuranceEnginResponse>> getAll() {
        return ResponseEntity.ok(assuranceEnginService.getAll());
    }
    @GetMapping("/engin/{enginId}/active")
    public ResponseEntity<AssuranceEnginResponse> getActiveByEngin(@PathVariable Long enginId) {
        return ResponseEntity.ok(assuranceEnginService.getActiveAssuranceByEngin(enginId));
    }

    @PutMapping("/{assuranceId}/expire")
    public ResponseEntity<AssuranceEnginResponse> expire(@PathVariable Long assuranceId) {
        return ResponseEntity.ok(assuranceEnginService.expireAssurance(assuranceId));
    }
    @PutMapping("/{assuranceId}")
    public ResponseEntity<AssuranceEnginResponse> updateAssurance(@PathVariable Long assuranceId, @RequestBody AssuranceEnginRequest request) {
        return ResponseEntity.ok(assuranceEnginService.updateAssuranceEngin(assuranceId,request));
    }

    @DeleteMapping("/{assuranceId}")
    public ResponseEntity<Void> delete(@PathVariable Long assuranceId) {
        assuranceEnginService.deleteAssurance(assuranceId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssuranceEnginResponse> createWithDocument(
            @RequestParam Long enginId,
            @RequestParam String numeroPolice,
            @RequestParam String compagnieAssurance,
            @RequestParam String dateDebut,
            @RequestParam String dateFin,
            @RequestParam java.math.BigDecimal montant,
            @RequestParam String documentUrl,
            @RequestParam StatutAssurance statutAssurance
    ) {
        AssuranceEnginRequest request = new AssuranceEnginRequest(
                enginId,
                numeroPolice,
                compagnieAssurance,
                java.time.LocalDate.parse(dateDebut),
                java.time.LocalDate.parse(dateFin),
                montant,
                documentUrl,
                statutAssurance,
                LocalDate.now(),
                LocalDate.now()
        );
        return ResponseEntity.ok(assuranceEnginService.createAssuranceEngin(request));
    }
}
