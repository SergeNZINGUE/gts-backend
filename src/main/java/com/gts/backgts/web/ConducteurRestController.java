package com.gts.backgts.web;

import com.gts.backgts.dto.*;
import com.gts.backgts.services.ConducteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/conducteurs")

public class ConducteurRestController {

    private final ConducteurService conducteurService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConducteurResponse> create(@ModelAttribute ConducteurRequest conducteurRequest) throws IOException {
        return ResponseEntity.ok(conducteurService.createConducteur(conducteurRequest));
    }
    @GetMapping("/list")
    public ResponseEntity <List<ConducteurResponse>> getAll() {
        return ResponseEntity.ok(conducteurService.getAllConducteurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity <ConducteurDetailResponse> getById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);
        System.out.println("conducteurService.getConducteurDetailWithMissionByID(id, PageRequest.of(page, size) : " + conducteurService.getConducteurDetailWithMissionByID(id, PageRequest.of(page, size)));
        return ResponseEntity.ok(conducteurService.getConducteurDetailWithMissionByID(id, PageRequest.of(page, size)));

    }

    @GetMapping("/{id}/images")
    public ResponseEntity<ConducteurImagesResponse> getImages(@PathVariable Long id) {
        return ResponseEntity.ok(conducteurService.getConducteurImages(id));
    }
}
