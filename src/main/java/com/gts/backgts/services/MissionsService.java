package com.gts.backgts.services;

import com.gts.backgts.dto.MissionsRequest;
import com.gts.backgts.dto.MissionsResponse;
import com.gts.backgts.entites.Facture;
import com.gts.backgts.entites.Locations;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.repository.FactureRepository;
import com.gts.backgts.repository.LocationsRepository;
import com.gts.backgts.repository.MissionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

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

    public MissionsResponse createMission(MissionsRequest request) {
        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Facture facture = null;
        if (request.factureId() != null) {
            facture = factureRepository.findById(request.factureId())
                    .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + request.factureId()));
        }

        Missions mission = new Missions();
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
        mission.setDateCreation(LocalDate.now());

        return toResponse(missionsRepository.save(mission));
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
        mission.setDateModification(LocalDate.now());

        return toResponse(missionsRepository.save(mission));
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
                mission.getDateModification()
        );
    }
}
