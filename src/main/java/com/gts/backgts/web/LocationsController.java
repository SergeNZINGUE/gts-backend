package com.gts.backgts.web;

import com.gts.backgts.dto.LocationsRequest;
import com.gts.backgts.dto.LocationsResponse;
import com.gts.backgts.services.LocationsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gts/locations")
public class LocationsController {

    private final LocationsService locationsService;

    @PostMapping
    public ResponseEntity<LocationsResponse> create(@Valid @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.createLocation(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<LocationsResponse>> getAll() {
        return ResponseEntity.ok(locationsService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationsResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationsService.getLocationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationsResponse> update(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.updateLocation(id, request));
    }
    @PutMapping("/{id}/valider")
    public ResponseEntity<LocationsResponse> validerLocation(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.validerLocation(id, request));
    }
    @PutMapping("/{id}/terminer")
    public ResponseEntity<LocationsResponse> terminerLocation(@PathVariable Long id, @RequestBody LocationsRequest request) {
        return ResponseEntity.ok(locationsService.terminerLocation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationsService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}