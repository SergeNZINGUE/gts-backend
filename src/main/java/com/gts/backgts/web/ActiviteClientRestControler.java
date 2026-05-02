package com.gts.backgts.web;

import com.gts.backgts.dto.ActiviteClientResponse;
import com.gts.backgts.entites.ActiviteClient;
import com.gts.backgts.repository.ActiviteClientRepository;
import com.gts.backgts.services.ActiviteClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/gts/activite-client")
@RestController
@RequiredArgsConstructor

public class ActiviteClientRestControler {
    private final ActiviteClientRepository activiteClientRepository;
    private final ActiviteClientService activiteClientService;



    @GetMapping(path ="/list")
    public ResponseEntity<List<ActiviteClientResponse>> getAllActiviteClients() {
        return ResponseEntity.ok(activiteClientService.getActiviteClient());
    }

    @GetMapping(path ="/{codeActivite}")
    public ResponseEntity<ActiviteClient> getActiviteByCode(@PathVariable String codeActivite) {
        return ResponseEntity.ok(activiteClientRepository.findByCodeActClt(codeActivite));
    }

    @PostMapping (path ="")
    public ActiviteClient saveActiviteClients(@RequestParam String codeActivite, @RequestParam String libelleActivite) {
        ActiviteClient activiteClient = new ActiviteClient();
        activiteClient.setCodeActClt(codeActivite);
        activiteClient.setDescription(libelleActivite);
        return activiteClientRepository.save(activiteClient);
    }

    @PutMapping (path ="/updateActiviteClients/{codeActivite}")
    public ActiviteClient updateActiviteClients(@PathVariable String codeActivite, @RequestParam String libelleActivite) {
        ActiviteClient activiteClient = activiteClientRepository.findByCodeActClt(codeActivite);
        activiteClient.setDescription(libelleActivite);
        return activiteClientRepository.save(activiteClient);
    }
}
