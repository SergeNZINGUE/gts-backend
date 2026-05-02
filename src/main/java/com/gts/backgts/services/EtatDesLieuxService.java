package com.gts.backgts.services;

import com.gts.backgts.dto.EtatDesLieuxRequest;
import com.gts.backgts.dto.EtatDesLieuxResponse;
import com.gts.backgts.entites.EtatDesLieux;
import com.gts.backgts.entites.Locations;
import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.EtatDesLieuxRepository;
import com.gts.backgts.repository.LocationsRepository;
import com.gts.backgts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EtatDesLieuxService {

    private final EtatDesLieuxRepository etatDesLieuxRepository;
    private final LocationsRepository locationsRepository;
    private final UserRepository userRepository;

    public EtatDesLieuxResponse createEtatDesLieux(EtatDesLieuxRequest request) throws IOException {
        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Users inspecteur = userRepository.findById(request.inspecteurId())
                .orElseThrow(() -> new IllegalArgumentException("Inspecteur introuvable avec id: " + request.inspecteurId()));

        EtatDesLieux etatDesLieux = new EtatDesLieux();
        etatDesLieux.setLocation(location);
        etatDesLieux.setDateInventaire(request.dateInventaire());
        etatDesLieux.setType(request.type());
        etatDesLieux.setNiveauCarburant(request.niveauCarburant());
        etatDesLieux.setHorametreConstat(request.horametreConstat());
        etatDesLieux.setObservationsGenerales(request.observationsGenerales());
        etatDesLieux.setADesDegats(request.aDesDegats());
        etatDesLieux.setInspecteur(inspecteur);
        etatDesLieux.setDateCreation(LocalDate.now());

        savePhoto(etatDesLieux, request.photosLien());

        return toResponse(etatDesLieuxRepository.save(etatDesLieux));
    }

    @Transactional(readOnly = true)
    public List<EtatDesLieuxResponse> getAllEtatsDesLieux() {
        return etatDesLieuxRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EtatDesLieuxResponse getEtatDesLieuxById(Long id) {
        EtatDesLieux etatDesLieux = etatDesLieuxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Etat des lieux introuvable avec id: " + id));
        return toResponse(etatDesLieux);
    }

    public EtatDesLieuxResponse updateEtatDesLieux(Long id, EtatDesLieuxRequest request) throws IOException {
        EtatDesLieux etatDesLieux = etatDesLieuxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Etat des lieux introuvable avec id: " + id));

        Locations location = locationsRepository.findById(request.locationId())
                .orElseThrow(() -> new IllegalArgumentException("Location introuvable avec id: " + request.locationId()));

        Users inspecteur = userRepository.findById(request.inspecteurId())
                .orElseThrow(() -> new IllegalArgumentException("Inspecteur introuvable avec id: " + request.inspecteurId()));

        etatDesLieux.setLocation(location);
        etatDesLieux.setDateInventaire(request.dateInventaire());
        etatDesLieux.setType(request.type());
        etatDesLieux.setNiveauCarburant(request.niveauCarburant());
        etatDesLieux.setHorametreConstat(request.horametreConstat());
        etatDesLieux.setObservationsGenerales(request.observationsGenerales());
        etatDesLieux.setADesDegats(request.aDesDegats());
        etatDesLieux.setInspecteur(inspecteur);
        etatDesLieux.setDateModification(LocalDate.now());

        savePhoto(etatDesLieux, request.photosLien());

        return toResponse(etatDesLieuxRepository.save(etatDesLieux));
    }

    public void deleteEtatDesLieux(Long id) {
        if (!etatDesLieuxRepository.existsById(id)) {
            throw new IllegalArgumentException("Etat des lieux introuvable avec id: " + id);
        }
        etatDesLieuxRepository.deleteById(id);
    }

    private void savePhoto(EtatDesLieux etatDesLieux, MultipartFile photosLien) throws IOException {
        if (photosLien != null && !photosLien.isEmpty()) {
            Path uploadDir = Path.of("uploads", "etatsdeslieux");
            Files.createDirectories(uploadDir);

            String originalFilename = photosLien.getOriginalFilename();
            String fileName = java.util.UUID.randomUUID() + "_" +
                    (originalFilename != null ? originalFilename : "photo-etat-lieux");

            Path destination = uploadDir.resolve(fileName);
            Files.copy(photosLien.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            etatDesLieux.setPhotosLien(destination.toString());
        }
    }

    private EtatDesLieuxResponse toResponse(EtatDesLieux etatDesLieux) {
        return new EtatDesLieuxResponse(
                etatDesLieux.getId(),
                etatDesLieux.getLocation() != null ? etatDesLieux.getLocation().getId() : null,
                etatDesLieux.getLocation() != null ? etatDesLieux.getLocation().getCodeLocation() : null,
                etatDesLieux.getDateInventaire(),
                etatDesLieux.getType(),
                etatDesLieux.getNiveauCarburant(),
                etatDesLieux.getHorametreConstat(),
                etatDesLieux.getObservationsGenerales(),
                etatDesLieux.getADesDegats(),
                etatDesLieux.getPhotosLien(),
                etatDesLieux.getInspecteur() != null ? etatDesLieux.getInspecteur().getId() : null,
                etatDesLieux.getInspecteur() != null ? etatDesLieux.getInspecteur().getNomUsers() : null,
                etatDesLieux.getInspecteur() != null ? etatDesLieux.getInspecteur().getPrenomsUsers() : null,
                etatDesLieux.getDateCreation(),
                etatDesLieux.getDateModification()
        );
    }
}
