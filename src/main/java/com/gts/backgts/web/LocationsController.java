package com.gts.backgts.web;

import com.gts.backgts.dto.LocationsRequest;
import com.gts.backgts.dto.LocationsResponse;
import com.gts.backgts.services.LocationsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/locations")
public class LocationsController {

    private final LocationsService locationsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<LocationsResponse> create(@Valid @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.createLocation(request));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COMPTABLE','OPERATEUR')")
    public ResponseEntity<List<LocationsResponse>> getAll() {
        return ResponseEntity.ok(locationsService.getAllLocations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COMPTABLE','OPERATEUR')")
    public ResponseEntity<LocationsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationsService.getLocationById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<LocationsResponse> update(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.updateLocation(id, request));
    }

    @PutMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<LocationsResponse> validerLocation(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.validerLocation(id, request));
    }

    @PutMapping("/{id}/terminer")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','OPERATEUR')")
    public ResponseEntity<LocationsResponse> terminerLocation(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.terminerLocation(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationsService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
