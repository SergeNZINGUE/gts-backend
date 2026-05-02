package com.gts.backgts.services;

import com.gts.backgts.dto.FactureMissionsRequest;
import com.gts.backgts.dto.FactureRequest;
import com.gts.backgts.dto.FactureResponse;
import com.gts.backgts.entites.Facture;
import com.gts.backgts.entites.Locations;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.repository.FactureRepository;
import com.gts.backgts.repository.LocationsRepository;
import com.gts.backgts.repository.MissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FactureService {

    private final FactureRepository factureRepository;
    private final LocationsRepository locationsRepository;
    private final MissionsRepository missionsRepository;

    public FactureResponse createFacture(FactureRequest request) {
        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Facture facture = new Facture();
        facture.setDateEmission(request.dateEmission());
        facture.setTauxTVA(request.tauxTVA() != null ? request.tauxTVA() : 18.0);
        facture.setEtatPaiement(request.etatPaiement());
        facture.setLocation(location);
        facture.setDateCreation(LocalDate.now());

        Facture saved = factureRepository.save(facture);

        if (request.missionIds() != null && !request.missionIds().isEmpty()) {
            List<Missions> missions = missionsRepository.findAllById(request.missionIds());
            if (missions.size() != request.missionIds().size()) {
                throw new IllegalArgumentException("Une ou plusieurs missions sont introuvables");
            }
            missions.forEach(m -> m.setFacture(saved));
            missionsRepository.saveAll(missions);
        }

        return toResponse(factureRepository.findById(saved.getId()).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<FactureResponse> getAllFactures() {
        return factureRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FactureResponse getFactureById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + id));
        return toResponse(facture);
    }

    public FactureResponse updateFacture(Long id, FactureRequest request) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + id));

        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        facture.setDateEmission(request.dateEmission());
        facture.setTauxTVA(request.tauxTVA() != null ? request.tauxTVA() : 18.0);
        facture.setEtatPaiement(request.etatPaiement());
        facture.setLocation(location);
        facture.setDateModification(LocalDate.now());

        return toResponse(factureRepository.save(facture));
    }

    public FactureResponse updateEtatFacture(Long id, FactureRequest request) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + id));
        facture.setEtatPaiement(request.etatPaiement());
        return toResponse(factureRepository.save(facture));
    }

    public void deleteFacture(Long id) {
        if (!factureRepository.existsById(id)) {
            throw new IllegalArgumentException("Facture introuvable avec id: " + id);
        }
        factureRepository.deleteById(id);
    }

    private FactureResponse toResponse(Facture facture) {
        Locations location = facture.getLocation();
        var client = (location != null) ? location.getClient() : null;

        return new FactureResponse(
                facture.getId(),
                facture.getDateEmission(),
                facture.getTauxTVA(),
                facture.getEtatPaiement(),
                facture.getDateCreation(),
                facture.getDateModification(),
                location != null ? location.getId() : null,
                location != null ? location.getCodeLocation() : null,
                location != null ? location.getSiteLocation() : null,
                client != null ? client.getId() : null,
                client != null ? client.getDesignationEntreprise() : null,
                facture.getMontantHT(),
                facture.getMontantTTC(),
                facture.getMissionsFacturees().stream()
                        .map(m -> new FactureResponse.MissionFactureeDTO(
                                m.getId(),
                                m.getCodeMission(),
                                m.getLieuMission(),
                                m.getNbHeures(),
                                m.getTarifHoraireApplique(),
                                m.getSousTotal(),
                                m.getStatutMission()
                        )).toList(),
                facture.getReglements().stream()
                        .map(r -> new FactureResponse.ReglementDTO(
                                r.getId(),
                                r.getDateReglement(),
                                r.getMontantVerse(),
                                r.getModePaiement()
                        )).toList()
        );
    }

    public FactureResponse affecterMissionsAFacture(Long factureId, FactureMissionsRequest request) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + factureId));

        List<Missions> missions = missionsRepository.findAllById(request.missionIds());

        if (missions.size() != request.missionIds().size()) {
            throw new IllegalArgumentException("Une ou plusieurs missions sont introuvables");
        }

        for (Missions mission : missions) {
            mission.setFacture(facture);
        }

        missionsRepository.saveAll(missions);
        facture.setDateModification(LocalDate.now());

        return toResponse(factureRepository.save(facture));
    }


}
