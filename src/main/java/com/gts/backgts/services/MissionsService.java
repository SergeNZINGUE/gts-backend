package com.gts.backgts.services;

import com.gts.backgts.dto.MissionsRequest;
import com.gts.backgts.dto.MissionsResponse;
import com.gts.backgts.entites.Conducteur;
import com.gts.backgts.entites.ConducteurMission;
import com.gts.backgts.entites.Facture;
import com.gts.backgts.entites.Locations;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.repository.ConducteurMissionRepository;
import com.gts.backgts.repository.ConducteurRepository;
import com.gts.backgts.repository.FactureRepository;
import com.gts.backgts.repository.LocationsRepository;
import com.gts.backgts.repository.MissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionsService {

    private final MissionsRepository missionsRepository;
    private final LocationsRepository locationsRepository;
    private final FactureRepository factureRepository;
    private final ConducteurRepository conducteurRepository;
    private final ConducteurMissionRepository conducteurMissionRepository;

    public MissionsResponse createMission(MissionsRequest request) {
        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));
        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Facture facture = null;
        if (request.factureId() != null) {
            facture = factureRepository.findById(request.factureId())
                    .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + request.factureId()));
        }

        Missions mission = new Missions();
        saveMission(request, location, facture, mission);
        mission.setDateCreation(LocalDate.now());
        Missions savedMission = missionsRepository.save(mission);

        ConducteurMission conducteurMission = new ConducteurMission();
        conducteurMission.setConducteur(conducteur);
        conducteurMission.setMission(savedMission);
        conducteurMission.setDateMCd(LocalDate.now());
        conducteurMission.setDateCreation(LocalDate.now());
        conducteurMissionRepository.save(conducteurMission);

        return toResponse(savedMission);
    }

    private void saveMission(MissionsRequest request, Locations location, Facture facture, Missions mission) {
        mission.setDateTravail(request.dateTravail());
        mission.setNbHeures(request.nbHeures());
        mission.setHeureDebutMission(request.heureDebutMission());
        mission.setHeureFinMission(request.heureFinMission());

        mission.setTarifHoraireApplique(request.tarifHoraireApplique());
        mission.setLocation(location);
        mission.setFacture(facture);
        mission.setCodeMission(request.codeMission());
        mission.setDateDebutMission(request.dateDebutMission());
        mission.setDateFinMission(request.dateFinMission());
        mission.setKmDbtMission(request.kmDbtMission());
        mission.setKmFinMission(request.kmFinMission());
        mission.setCompteurDbtMission(request.compteurDbtMission());
        mission.setCompteurFinMission(request.compteurFinMission());
        mission.setCarbtDbtMission(request.carbtDbtMission());
        mission.setCarbtFinMission(request.carbtFinMission());
        mission.setMateriauxMission(request.materiauxMission());
        mission.setQteMateriauxMission(request.qteMateriauxMission());
        mission.setStatutMission(request.statutMission());
        mission.setObservationMission(request.observationMission());
        mission.setPrioriteMission(request.prioriteMission());
        mission.setResponsableMission(request.responsableMission());
        mission.setLieuMission(request.lieuMission());
        mission.setDescriptionMission(request.descriptionMission());
    }

    @Transactional(readOnly = true)
    public List<MissionsResponse> getAllMissions() {
        return missionsRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MissionsResponse getMissionById(Long id) {
        Missions mission = missionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable avec id: " + id));
        return toResponse(mission);
    }

    public MissionsResponse updateMission(Long id, MissionsRequest request) {
        Missions mission = missionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable avec id: " + id));

        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Facture facture = null;
        if (request.factureId() != null) {
            facture = factureRepository.findById(request.factureId())
                    .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + request.factureId()));
        }

        saveMission(request, location, facture, mission);
        mission.setDateModification(LocalDate.now());
        Missions savedMission = missionsRepository.save(mission);

        if (request.conducteurId() != null) {
            ConducteurMission cm = conducteurMissionRepository.findByMission(savedMission)
                    .orElse(new ConducteurMission());
            boolean conducteurChange = cm.getConducteur() == null
                    || !cm.getConducteur().getIdConducteur().equals(request.conducteurId());
            if (conducteurChange) {
                Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                        .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));
                cm.setConducteur(conducteur);
                cm.setMission(savedMission);
                cm.setDateMCd(LocalDate.now());
                if (cm.getDateCreation() == null) cm.setDateCreation(LocalDate.now());
                cm.setDateModification(LocalDate.now());
                conducteurMissionRepository.save(cm);
            }
        }

        return toResponse(savedMission);
    }

    public MissionsResponse terminerMission(Long id, MissionsRequest request) {
        System.out.println("Mission"+ request);
        System.out.println("Mission nbHeure"+ request.nbHeures());
        System.out.println("Mission"+ request.tarifHoraireApplique());
        System.out.println("Mission"+ request.kmDbtMission());
        System.out.println("Mission"+ request.kmFinMission());
        System.out.println("Mission"+ request.carbtDbtMission());
        System.out.println("Mission"+ request.carbtFinMission());
        System.out.println("Mission"+ request.materiauxMission());
        Missions mission = missionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable avec id: " + id));

        Optional.ofNullable(request.nbHeures())
                .ifPresent(mission::setNbHeures);
        Optional.ofNullable(request.kmDbtMission())
                .ifPresent(mission::setKmDbtMission);
        Optional.ofNullable(request.kmFinMission())
                .ifPresent(mission::setKmFinMission);
        Optional.ofNullable(request.compteurDbtMission())
                .ifPresent(mission::setCompteurDbtMission);
        Optional.ofNullable(request.compteurFinMission())
                .ifPresent(mission::setCompteurFinMission);
        Optional.ofNullable(request.carbtDbtMission())
                .ifPresent(mission::setCarbtDbtMission);
        Optional.ofNullable(request.carbtFinMission())
                .ifPresent(mission::setCarbtFinMission);
        Optional.ofNullable(request.materiauxMission())
                .ifPresent(mission::setMateriauxMission);
        Optional.ofNullable(request.qteMateriauxMission())
                .ifPresent(mission::setQteMateriauxMission);
        Optional.ofNullable(request.heureDebutMission())
                .ifPresent(mission::setHeureDebutMission);
        Optional.ofNullable(request.heureFinMission())
                .ifPresent(mission::setHeureFinMission);

       mission.setStatutMission(request.statutMission());

        return toResponse(missionsRepository.save(mission));
    }


    public void deleteMission(Long id) {
        if (!missionsRepository.existsById(id)) {
            throw new IllegalArgumentException("Mission introuvable avec id: " + id);
        }
        missionsRepository.deleteById(id);
    }

    private MissionsResponse toResponse(Missions mission) {
        Conducteur conducteur = mission.getConducteurMissions().stream()
                .findFirst()
                .map(ConducteurMission::getConducteur)
                .orElse(null);

        return new MissionsResponse(
                mission.getId(),
                mission.getDateTravail(),
                mission.getNbHeures(),
                mission.getHeureDebutMission(),
                mission.getHeureFinMission(),
                mission.getTarifHoraireApplique(),
                mission.getSousTotal(),
                mission.getLocation() != null ? mission.getLocation().getId() : null,
                mission.getLocation() != null ? mission.getLocation().getCodeLocation() : null,
                mission.getFacture() != null ? mission.getFacture().getId() : null,
                mission.getFacture() != null ? mission.getFacture().getEtatPaiement() : null,
                mission.getCodeMission(),
                mission.getDateDebutMission(),
                mission.getDateFinMission(),
                mission.getKmDbtMission(),
                mission.getKmFinMission(),
                mission.getCompteurDbtMission(),
                mission.getCompteurFinMission(),
                mission.getCarbtDbtMission(),
                mission.getCarbtFinMission(),
                mission.getMateriauxMission(),
                mission.getQteMateriauxMission(),
                mission.getStatutMission(),
                mission.getObservationMission(),
                mission.getPrioriteMission(),
                mission.getResponsableMission(),
                mission.getLieuMission(),
                mission.getDescriptionMission(),
                mission.getDateCreation(),
                mission.getDateModification(),
                conducteur != null ? conducteur.getIdConducteur() : null,
                conducteur != null ? conducteur.getCodeConducteur() : null,
                conducteur != null ? conducteur.getNomConducteur() : null,
                conducteur != null ? conducteur.getPrenomsConducteur() : null,
                conducteur != null ? conducteur.getTelephone() : null
        );
    }
}
