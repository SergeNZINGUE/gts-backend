package com.gts.backgts.services;

import com.gts.backgts.dto.LocationsRequest;
import com.gts.backgts.dto.LocationsResponse;
import com.gts.backgts.entites.*;
import com.gts.backgts.repository.ClientRepository;
import com.gts.backgts.repository.ConducteurRepository;
import com.gts.backgts.repository.EnginsRepository;
import com.gts.backgts.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationsService {

    private final LocationsRepository locationsRepository;
    private final ClientRepository clientRepository;
    private final ConducteurRepository conducteurRepository;
    private final EnginsRepository enginsRepository;

    public LocationsResponse createLocation(LocationsRequest request) {
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + request.clientId()));

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        Locations location = new Locations();
        location.setDateDebut(request.dateDebut());
        location.setStatut(request.statut());
        location.setCodeLocation(request.codeLocation());
        location.setNbJoursLocation(request.nbJoursLocation());
        location.setSiteLocation(request.siteLocation());
        location.setDateDbtLoc(request.dateDbtLoc());
        location.setDateFinLoc(request.dateFinLoc());
        location.setNbHeureLocation(request.nbHeureLocation());

        location.setCoutHoraireLocation(request.coutHoraireLocation());
        location.setCoutJournalierLocation(request.coutJournalierLocation());

        location.setEtatLocation(request.etatLocation());
        location.setClient(client);
        location.setConducteur(conducteur);
        location.setEngins(engin);
        location.setDateCreation(LocalDate.now());

        //Changement de status de l'engin'
        engin.setStatusEngin(TypeEtatEngins.EN_MISSION);
        enginsRepository.save(engin);

        //Changement de status du conducteur'
        conducteur.setStatutConducteur(StatutConducteur.EN_MISSION);
        enginsRepository.save(engin);
        return toResponse(locationsRepository.save(location));
    }

    @Transactional(readOnly = true)
    public List<LocationsResponse> getAllLocations() {
        return locationsRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LocationsResponse getLocationById(Long id) {
        Locations location = locationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + id));
        return toResponse(location);
    }

    private Long requireId(Long id, String message) {
        if (id == null) {
            throw new IllegalArgumentException(message);
        }
        return id;
    }


    public LocationsResponse updateLocation(Long id, LocationsRequest request) {
        Long locationId = requireId(id, "L'identifiant de la location est obligatoire");
        Long clientId = requireId(request.clientId(), "L'identifiant du client est obligatoire");
        Long conducteurId = requireId(request.conducteurId(), "L'identifiant du conducteur est obligatoire");
        Long enginId = requireId(request.enginId(), "L'identifiant de l'engin est obligatoire");

        Locations location = locationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + id));

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + request.clientId()));

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + request.conducteurId()));

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        location.setDateDebut(request.dateDebut());
        location.setStatut(request.statut());
        location.setCodeLocation(request.codeLocation());
        location.setNbJoursLocation(request.nbJoursLocation());
        location.setSiteLocation(request.siteLocation());
        location.setDateDbtLoc(request.dateDbtLoc());
        location.setDateFinLoc(request.dateFinLoc());
        location.setCoutHoraireLocation(request.coutHoraireLocation());
        location.setCoutJournalierLocation(request.coutJournalierLocation());
        location.setEtatLocation(request.etatLocation());
        location.setClient(client);
        location.setConducteur(conducteur);
        location.setEngins(engin);
        location.setDateModification(LocalDate.now());


        return toResponse(locationsRepository.save(location));
    }

    public LocationsResponse validerLocation(Long id, LocationsRequest request) {
        Locations location = locationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + id));
        location.setStatut("VALIDEE");
        location.setCoutHoraireLocation(request.coutHoraireLocation());
        location.setCoutJournalierLocation(request.coutJournalierLocation());

        return toResponse(locationsRepository.save(location));
    }
    public LocationsResponse terminerLocation(Long id, LocationsRequest request) {
        Locations location = locationsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + id));
        location.setStatut("TERMINEE");
        return toResponse(locationsRepository.save(location));
    }


    public void deleteLocation(Long id) {
        if (!locationsRepository.existsById(id)) {
            throw new IllegalArgumentException("Location introuvable avec id: " + id);
        }
        locationsRepository.deleteById(id);
    }

    private LocationsResponse toResponse(Locations location) {
        return new LocationsResponse(
                location.getId(),
                location.getDateDebut(),
                location.getStatut(),
                location.getCodeLocation(),
                location.getNbJoursLocation(),
                location.getSiteLocation(),
                location.getDateDbtLoc(),
                location.getDateFinLoc(),
                location.getCoutHoraireLocation(),
                location.getCoutJournalierLocation(),
                location.getNbHeureLocation(),
                location.getEtatLocation(),
                location.getClient() != null ? location.getClient().getId() : null,
                location.getClient() != null ? location.getClient().getDescriptionEntreprise() : null,
                location.getConducteur() != null ? location.getConducteur().getIdConducteur() : null,
                location.getConducteur() != null ? location.getConducteur().getNomConducteur() : null,
                location.getConducteur() != null ? location.getConducteur().getPrenomsConducteur() : null,
                location.getEngins() != null ? location.getEngins().getId() : null,
                location.getEngins() != null ? location.getEngins().getCodeEngin() : null,
                location.getEngins() != null ? location.getEngins().getModelEngin() : null,
                location.getEngins()!=null?location.getEngins().getMarqueEngin():null,
                location.getDateCreation(),
                location.getDateModification()
        );
    }
}