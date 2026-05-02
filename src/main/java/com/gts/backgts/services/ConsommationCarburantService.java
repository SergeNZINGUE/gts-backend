package com.gts.backgts.services;

import com.gts.backgts.dto.ConsommationCarburantRequest;
import com.gts.backgts.dto.ConsommationCarburantResponse;
import com.gts.backgts.entites.Conducteur;
import com.gts.backgts.entites.ConsommationCarburant;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.repository.ConducteurRepository;
import com.gts.backgts.repository.ConsommationCarburantRepository;
import com.gts.backgts.repository.EnginsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsommationCarburantService {

    private final ConsommationCarburantRepository consommationCarburantRepository;
    private final EnginsRepository enginsRepository;
    private final ConducteurRepository conducteurRepository;

    public ConsommationCarburantResponse createConsommationCarburant(ConsommationCarburantRequest request) {
        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));

        ConsommationCarburant consommationCarburant = new ConsommationCarburant();
        consommationCarburant.setEngin(engin);
        consommationCarburant.setConducteur(conducteur);
        consommationCarburant.setDatePlein(request.datePlein());
        consommationCarburant.setQuantiteLitres(request.quantiteLitres());
        consommationCarburant.setPrixUnitaire(request.prixUnitaire());
        consommationCarburant.setHorametreAuPlein(request.horametreAuPlein());
        consommationCarburant.setLieuRavitaillement(request.lieuRavitaillement());
        consommationCarburant.setDateCreation(LocalDate.now());

        return toResponse(consommationCarburantRepository.save(consommationCarburant));
    }

    @Transactional(readOnly = true)
    public List<ConsommationCarburantResponse> getAllConsommationsCarburant() {
        return consommationCarburantRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConsommationCarburantResponse getConsommationCarburantById(Long id) {
        ConsommationCarburant consommationCarburant = consommationCarburantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consommation carburant introuvable avec id: " + id));
        return toResponse(consommationCarburant);
    }

    public ConsommationCarburantResponse updateConsommationCarburant(Long id, ConsommationCarburantRequest request) {
        ConsommationCarburant consommationCarburant = consommationCarburantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consommation carburant introuvable avec id: " + id));

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));

        consommationCarburant.setEngin(engin);
        consommationCarburant.setConducteur(conducteur);
        consommationCarburant.setDatePlein(request.datePlein());
        consommationCarburant.setQuantiteLitres(request.quantiteLitres());
        consommationCarburant.setPrixUnitaire(request.prixUnitaire());
        consommationCarburant.setHorametreAuPlein(request.horametreAuPlein());
        consommationCarburant.setLieuRavitaillement(request.lieuRavitaillement());
        consommationCarburant.setDateModification(LocalDate.now());

        return toResponse(consommationCarburantRepository.save(consommationCarburant));
    }

    public void deleteConsommationCarburant(Long id) {
        if (!consommationCarburantRepository.existsById(id)) {
            throw new IllegalArgumentException("Consommation carburant introuvable avec id: " + id);
        }
        consommationCarburantRepository.deleteById(id);
    }

    private ConsommationCarburantResponse toResponse(ConsommationCarburant consommationCarburant) {
        return new ConsommationCarburantResponse(
                consommationCarburant.getId(),
                consommationCarburant.getEngin() != null ? consommationCarburant.getEngin().getId() : null,
                consommationCarburant.getEngin() != null ? consommationCarburant.getEngin().getCodeEngin() : null,
                consommationCarburant.getConducteur() != null ? consommationCarburant.getConducteur().getIdConducteur() : null,
                consommationCarburant.getConducteur() != null ? consommationCarburant.getConducteur().getNomConducteur() : null,
                consommationCarburant.getConducteur() != null ? consommationCarburant.getConducteur().getPrenomsConducteur() : null,
                consommationCarburant.getDatePlein(),
                consommationCarburant.getQuantiteLitres(),
                consommationCarburant.getPrixUnitaire(),
                consommationCarburant.getHorametreAuPlein(),
                consommationCarburant.getLieuRavitaillement(),
                consommationCarburant.getCoutTotal(),
                consommationCarburant.getDateCreation(),
                consommationCarburant.getDateModification()
        );
    }
}
