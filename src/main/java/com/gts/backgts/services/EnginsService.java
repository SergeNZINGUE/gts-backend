package com.gts.backgts.services;

import com.gts.backgts.dto.EnginRequest;
import com.gts.backgts.dto.EnginResponse;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.entites.TypeEngin;
import com.gts.backgts.repository.EnginsRepository;
import com.gts.backgts.repository.TypeEnginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnginsService {
    private final EnginsRepository enginsRepository;
    private final TypeEnginService typeEnginService;
    private final TypeEnginRepository typeEnginRepository;  // ← ajout

    public EnginResponse createEngin(EnginRequest enginRequest) {
        if (enginsRepository.existsByCodeEngin(enginRequest.codeEngin())) {
            throw new IllegalArgumentException("Un engin avec ce code existe déjà: " + enginRequest.codeEngin());
        }

        // ← Résolution du typeEngin depuis l'ID
        TypeEngin typeEngin = enginRequest.typeEnginId() != null
                ? typeEnginRepository.findById(enginRequest.typeEnginId())
                  .orElseThrow(() -> new IllegalArgumentException("Type engin introuvable: " + enginRequest.typeEnginId()))
                : null;

        Engins engin = Engins.builder()
                .codeEngin(enginRequest.codeEngin())
                .modelEngin(enginRequest.modelEngin())
                .anneeEngin(enginRequest.anneeEngin())
                .immatriculationEngin(enginRequest.immatriculationEngin())
                .typeEngin(typeEngin)                        // ← variable résolue
                .marqueEngin(enginRequest.marqueEngin())
                .etatEngin(1)
                .statusEngin(enginRequest.statusEngin())
                .typCarbtEngin(enginRequest.typCarbtEngin())
                .dateAcqEngin(enginRequest.dateAcqEngin())
                .coutHorLocEngin(enginRequest.coutHorLocEngin())
                .poidsVide(enginRequest.poidsVide())
                .horametre(enginRequest.horametre())
                .dateCreation(LocalDate.now())
                .build();

        return toResponse(enginsRepository.save(engin));
    }

    @Transactional(readOnly = true)
    public List<EnginResponse> getAllEngins() {
        return enginsRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EnginResponse getEnginById(Long id) {
        Engins engin = enginsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + id));
        return toResponse(engin);
    }

    public EnginResponse updateEngin(Long id, EnginRequest request) {
        Engins engin = enginsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + id));

        // ← Résolution du typeEngin depuis l'ID
        TypeEngin typeEngin = request.typeEnginId() != null
                ? typeEnginRepository.findById(request.typeEnginId())
                  .orElseThrow(() -> new IllegalArgumentException("Type engin introuvable: " + request.typeEnginId()))
                : null;

        engin.setCodeEngin(request.codeEngin());
        engin.setModelEngin(request.modelEngin());
        engin.setAnneeEngin(request.anneeEngin());
        engin.setImmatriculationEngin(request.immatriculationEngin());
        engin.setTypeEngin(typeEngin);                       // ← variable résolue
        engin.setMarqueEngin(request.marqueEngin());
        engin.setStatusEngin(request.statusEngin());
        engin.setTypCarbtEngin(request.typCarbtEngin());
        engin.setDateAcqEngin(request.dateAcqEngin());
        engin.setCoutHorLocEngin(request.coutHorLocEngin());
        engin.setPoidsVide(request.poidsVide());
        engin.setHorametre(request.horametre());
        engin.setDateModification(LocalDate.now());

        return toResponse(enginsRepository.save(engin));
    }

    public void deleteEngin(Long id) {
        if (!enginsRepository.existsById(id)) {
            throw new IllegalArgumentException("Engin introuvable avec id: " + id);
        }
        enginsRepository.deleteById(id);
    }

    public EnginResponse desactiveEngin(Long id) {
        Engins engin = enginsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + id));
        engin.setEtatEngin(0);
        return toResponse(enginsRepository.save(engin));
    }

    private EnginResponse toResponse(Engins engin) {
        return new EnginResponse(
                engin.getId(),
                engin.getCodeEngin(),
                engin.getModelEngin(),
                engin.getAnneeEngin(),
                engin.getImmatriculationEngin(),
                engin.getTypeEngin() != null
                        ? typeEnginService.toResponse(engin.getTypeEngin())
                        : null,
                engin.getMarqueEngin(),
                engin.getStatusEngin(),
                engin.getEtatEngin(),
                engin.getTypCarbtEngin(),
                engin.getDateAcqEngin(),
                engin.getCoutHorLocEngin(),
                engin.getPoidsVide(),
                engin.getHorametre(),
                engin.getDateCreation(),
                engin.getDateModification()
        );
    }
}
