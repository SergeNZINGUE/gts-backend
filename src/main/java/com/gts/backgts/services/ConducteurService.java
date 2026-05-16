package com.gts.backgts.services;

import com.gts.backgts.dto.*;
import com.gts.backgts.entites.Conducteur;
import com.gts.backgts.entites.Missions;
import com.gts.backgts.repository.ConducteurMissionRepository;
import com.gts.backgts.repository.ConducteurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ConducteurService {

    private final  ConducteurRepository conducteurRepository;
    private final ConducteurMissionRepository conducteurMissionRepository;
    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    public ConducteurResponse createConducteur(ConducteurRequest conducteurRequest) throws IOException {
        if (conducteurRepository.findByCodeConducteur(conducteurRequest.codeConducteur()) != null) {
            throw new IOException("Un conducteur avec le code " + conducteurRequest.codeConducteur() + " existe dejà");
        }
        Conducteur conducteur = Conducteur.builder()
                .codeConducteur(conducteurRequest.codeConducteur())
                .nomConducteur(conducteurRequest.nomConducteur())
                .prenomsConducteur(conducteurRequest.prenomsConducteur())
                .telephone(conducteurRequest.telephone())
                .cnibRef(conducteurRequest.cnibRef())
                .cniDateEmi(conducteurRequest.cniDateEmi())
                .cniLieuEtab(conducteurRequest.cniLieuEtab())
                .cniDateExp(conducteurRequest.cniDateExp())
                .permisCond(conducteurRequest.permisCond())
                .etatConducteur(1)
                .qualifications(conducteurRequest.qualifications())
                .dateDebutEmp(conducteurRequest.dateDebutEmp())
                .dateFinEmp(conducteurRequest.dateFinEmp())
                .dateNaissance(conducteurRequest.dateNaissance())
                .dateCreation(LocalDate.now())
                .typEmpl(conducteurRequest.typEmpl())

                .build();
        savePiecesJointes(conducteur, conducteurRequest.imgCni(), conducteurRequest.imgConducteur(), conducteurRequest.imgPermis());
        conducteurRepository.save(conducteur);

        return toResponse(conducteur);

    }

    @Transactional(readOnly = true)
    public ConducteurImagesResponse getConducteurImages(Long id) {
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + id));

        return new ConducteurImagesResponse(
                conducteur.getIdConducteur(),
                toPublicUrl(conducteur.getImgCni()),
                toPublicUrl(conducteur.getImgConducteur()),
                toPublicUrl(conducteur.getImgPermis())
        );
    }

    private String toPublicUrl(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            return null;
        }

        String normalizedPath = storedPath.replace("\\", "/");
        if (normalizedPath.startsWith("uploads/")) {
            return baseUrl + "/" + normalizedPath;
        }

        return baseUrl + "/uploads/conducteurs/" + normalizedPath;
    }


    @Transactional(readOnly = true)
    public List<ConducteurResponse> getAllConducteurs() {
        return conducteurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public ConducteurDetailResponse getConducteurDetailWithMissionByID(Long id, Pageable pageable) {
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + id));

        Page<Missions> missions = conducteurMissionRepository.findMissionsByConducteurId(id,pageable);

        List<MissionResumeResponse> missionResumeResponses = missions.getContent()
                .stream().map(m -> new MissionResumeResponse(
                        m.getId(),
                        m.getCodeMission(),
                        m.getDateTravail(),
                        m.getStatutMission(),
                        m.getLieuMission(),
                        m.getDescriptionMission(),
                        m.getNbHeures()
                ))
                .toList();

        return new ConducteurDetailResponse(
                        conducteur.getIdConducteur(),
                        conducteur.getCodeConducteur(),
                        conducteur.getNomConducteur(),
                        conducteur.getPrenomsConducteur(),
                        conducteur.getTelephone(),
                        conducteur.getPermisCond(),
                        conducteur.getQualifications(),
                        conducteur.getEtatConducteur(),
                        conducteur.getStatutConducteur(),
                        missions.getTotalElements(),
                        conducteur.getDateDebutEmp(),
                        conducteur.getDateFinEmp(),
                        conducteur.getDateNaissance(),
                        conducteur.getTypEmpl(),
                        conducteur.getImgConducteur(),
                        missions.getNumber(),
                        missions.getSize(),
                        missions.getTotalPages(),
                        missionResumeResponses
        );
    }

    @Transactional(readOnly = true)
    public ConducteurResponse getConducteurByID(Long id) {
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + id));
        return toResponse(conducteur);
    }

    private void savePiecesJointes(Conducteur conducteur, MultipartFile imgCni , MultipartFile imgConducteur,MultipartFile imgPermis) throws IOException {
        Path uploadDir = Path.of("uploads", "conducteurs");

        if (imgCni != null && !imgCni.isEmpty()) {

            Files.createDirectories(uploadDir);

            String originalFilename = imgCni.getOriginalFilename();
            String fileName = java.util.UUID.randomUUID() + "_" +
                    (originalFilename != null ? originalFilename : "img-cni");
            Path destination = uploadDir.resolve(fileName);

            Files.copy(imgCni.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            conducteur.setImgCni(destination.toString());
        }

        if (imgConducteur != null && !imgConducteur.isEmpty()) {

            Files.createDirectories(uploadDir);

            String originalFilename = imgConducteur.getOriginalFilename();
            String fileName = java.util.UUID.randomUUID() + "_" +
                    (originalFilename != null ? originalFilename : "img-conducteur");
            Path destination = uploadDir.resolve(fileName);

            Files.copy(imgConducteur.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            conducteur.setImgConducteur(destination.toString());
        }
        if (imgPermis != null && !imgPermis.isEmpty()) {

            Files.createDirectories(uploadDir);

            String originalFilename = imgPermis.getOriginalFilename();
            String fileName = java.util.UUID.randomUUID() + "_" +
                    (originalFilename != null ? originalFilename : "img-permis");
            Path destination = uploadDir.resolve(fileName);

            Files.copy(imgPermis.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            conducteur.setImgPermis(destination.toString());
        }
    }


    private ConducteurResponse toResponse(Conducteur conducteur) {

        return new ConducteurResponse(

                        conducteur.getIdConducteur(),

                        conducteur.getCodeConducteur(),
                        conducteur.getNomConducteur(),
                        conducteur.getPrenomsConducteur(),
                        conducteur.getTelephone(),
                        conducteur.getPermisCond(),
                        conducteur.getQualifications(),

                        conducteur.getCnibRef(),
                        conducteur.getCniDateEmi(),
                        conducteur.getCniDateExp(),
                        conducteur.getCniLieuEtab(),

                        conducteur.getImgCni(),
                        conducteur.getImgPermis(),
                        conducteur.getImgConducteur(),

                        conducteur.getDateDebutEmp(),
                        conducteur.getDateFinEmp(),
                        conducteur.getDateNaissance(),

                        conducteur.getTypEmpl(),


                        conducteur.getDateCreation(),
                        conducteur.getDateModification(),

                        conducteur.getEtatConducteur(),
                        conducteur.getStatutConducteur()

                );
    }


}
