package com.gts.backgts.web;

import com.gts.backgts.dto.*;
import com.gts.backgts.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/clients")
public class ClientRestControler {

    private final ClientService clientService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COMPTABLE','OPERATEUR')")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<ClientResponse> create(@ModelAttribute ClientRequest clientRequest) throws IOException {
        return ResponseEntity.ok(clientService.saveClient(clientRequest));
    }

    @GetMapping("/{id}/locations")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COMPTABLE','OPERATEUR')")
    public ResponseEntity<ClientDetailsResponse> getClientWithLocations(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);
        return ResponseEntity.ok(clientService.getClientWithLocations(id, PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/images")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COMPTABLE','OPERATEUR')")
    public ResponseEntity<LogoClientResponse> getImages(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getLogoClient(id));
    }
}
