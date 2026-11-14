package com.gts.backgts.services;

import com.gts.backgts.dto.ActiviteClientResponse;
import com.gts.backgts.entites.ActiviteClient;
import com.gts.backgts.repository.ActiviteClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActiviteClientService {
    private final ActiviteClientRepository activiteClientRepository;

    @Transactional(readOnly = true)
    public List<ActiviteClientResponse> getActiviteClient() {
        return activiteClientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ActiviteClientResponse create(String code, String libelle) {
        String normalized = code == null ? "" : code.trim().toUpperCase();
        if (normalized.isEmpty() || libelle == null || libelle.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code et libellé obligatoires");
        }
        if (activiteClientRepository.findByCodeActClt(normalized) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Le code d'activité " + normalized + " existe déjà");
        }
        ActiviteClient activiteClient = new ActiviteClient();
        activiteClient.setCodeActClt(normalized);
        activiteClient.setDescription(libelle.trim());
        activiteClient.setDateCreation(LocalDate.now());
        return toResponse(activiteClientRepository.save(activiteClient));
    }

    public ActiviteClientResponse update(String code, String libelle) {
        if (libelle == null || libelle.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Libellé obligatoire");
        }
        ActiviteClient activiteClient = activiteClientRepository.findByCodeActClt(code);
        if (activiteClient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activité introuvable : " + code);
        }
        activiteClient.setDescription(libelle.trim());
        activiteClient.setDateModification(LocalDate.now());
        return toResponse(activiteClientRepository.save(activiteClient));
    }

    private ActiviteClientResponse toResponse(ActiviteClient activiteClient) {
        return new ActiviteClientResponse(
                activiteClient.getId(),
                activiteClient.getCodeActClt(),
                activiteClient.getDescription(),
                activiteClient.getDateCreation(),
                activiteClient.getDateModification());

    }

}
