package com.gts.backgts.services;

import com.gts.backgts.dto.AssuranceEnginRequest;
import com.gts.backgts.dto.AssuranceEnginResponse;
import com.gts.backgts.entites.AssuranceEngin;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.enums.StatutAssurance;
import com.gts.backgts.repository.AssuranceEnginRepository;
import com.gts.backgts.repository.EnginsRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssuranceEnginService {

    private final AssuranceEnginRepository assuranceEnginRepository;
    private final EnginsRepository enginsRepository;

    public AssuranceEnginResponse createAssuranceEngin( AssuranceEnginRequest request) {
    Engins engins=enginsRepository.findById(request.enginId())
            .orElseThrow(() -> new IllegalArgumentException("Engin not found with id: " + request.enginId()));

    assuranceEnginRepository.findByEnginIdAndStatut(engins.getId(), StatutAssurance.VALIDE)
            .ifPresent(assuranceEngin -> {
                assuranceEngin.setStatut(StatutAssurance.EXPIRE);
                if(assuranceEngin.getDateFin()==null || assuranceEngin.getDateFin().isAfter(LocalDate.now())){
                    assuranceEngin.setDateFin(LocalDate.now());
                }
                assuranceEnginRepository.save(assuranceEngin);
            });
        AssuranceEngin assurance=AssuranceEngin.builder()
                .engin(engins)
                .numeroPolice(request.numeroPolice())
                .compagnieAssurance(request.compagnieAssurance())
                .dateDebut(request.dateDebut())
                .dateFin(request.dateDebut().plusYears(1))
                .montant(request.montant())
                .documentUrl(request.documentUrl())
                .statut(StatutAssurance.VALIDE)
                .dateCreation(LocalDate.now())
                .build();
        AssuranceEngin saved=assuranceEnginRepository.save(assurance);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AssuranceEnginResponse> getAssurancesByEngin(Long enginId) {
        return assuranceEnginRepository.findByEnginId(enginId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AssuranceEnginResponse> getAll() {
        return assuranceEnginRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AssuranceEnginResponse getActiveAssuranceByEngin(Long enginId) {
        AssuranceEngin assurance = assuranceEnginRepository.findByEnginIdAndStatut(enginId, StatutAssurance.VALIDE)
                .orElseThrow(() -> new IllegalArgumentException("Aucune assurance active trouvée pour l'engin id: " + enginId));
        return toResponse(assurance);
    }

    public AssuranceEnginResponse expireAssurance(Long assuranceId) {
        AssuranceEngin assurance = assuranceEnginRepository.findById(assuranceId)
                .orElseThrow(() -> new IllegalArgumentException("Assurance introuvable avec id: " + assuranceId));

        assurance.setStatut(StatutAssurance.EXPIRE);
        if (assurance.getDateFin() == null || assurance.getDateFin().isAfter(LocalDate.now())) {
            assurance.setDateFin(LocalDate.now());
        }

        return toResponse(assuranceEnginRepository.save(assurance));
    }

    public void deleteAssurance(Long assuranceId) {
        if (!assuranceEnginRepository.existsById(assuranceId)) {
            throw new IllegalArgumentException("Assurance introuvable avec id: " + assuranceId);
        }
        assuranceEnginRepository.deleteById(assuranceId);
    }




    public AssuranceEnginResponse updateAssuranceEngin(Long id, AssuranceEnginRequest request) {
        Engins engins=enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin not found with id: " + request.enginId()));


        AssuranceEngin assurance=assuranceEnginRepository.findAssuranceEnginById(id);

                assurance.setEngin(engins);
                assurance.setNumeroPolice(request.numeroPolice());
                assurance.setCompagnieAssurance(request.compagnieAssurance());
                assurance.setDateDebut(request.dateDebut());
                assurance.setDateFin(request.dateFin());
                assurance.setMontant(request.montant());
                assurance.setDocumentUrl(request.documentUrl());
                assurance.setStatut(request.statut());
                assurance.setDateModification(LocalDate.now());
                System.out.println(" [ASSURANCE] ASSURANCE : "+assurance);
                System.out.println("assurance Statut : "+assurance.getStatut()+"..");

        AssuranceEngin saved=assuranceEnginRepository.save(assurance);
        return toResponse(saved);
    }

    private AssuranceEnginResponse toResponse(AssuranceEngin assurance) {
        return new AssuranceEnginResponse(
                assurance.getId(),
                assurance.getEngin().getId(),
                assurance.getNumeroPolice(),
                assurance.getCompagnieAssurance(),
                assurance.getDateDebut(),
                assurance.getDateFin(),
                assurance.getMontant(),
                assurance.getDocumentUrl(),
                assurance.getStatut(),
                assurance.getDateCreation(),
                assurance.getDateModification()
        );
    }

}