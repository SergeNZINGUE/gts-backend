package com.gts.backgts.services;

import com.gts.backgts.dto.CamionRequest;
import com.gts.backgts.dto.CamionResponse;
import com.gts.backgts.entites.Camion;
import com.gts.backgts.repository.CamionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CamionService {
    private final CamionRepository camionRepository;

    public CamionResponse createCamion(CamionRequest request) {
        if(camionRepository.existsByCodeEngin(request.codeEngin())){
            throw new IllegalArgumentException("Ce Camion existe dejà dans la base :"+request.codeEngin());
        }
        if (camionRepository.existsByImmatriculationEngin(request.immatriculationEngin())) {
            throw new IllegalArgumentException("Un camion avec cette immatriculation existe déjà: " + request.immatriculationEngin());
        }
        Camion camion = getCamion(request);
        Camion saved=camionRepository.save(camion);
        return toResponse(saved);
    }

    private static @NonNull Camion getCamion(CamionRequest request) {
        Camion camion = new Camion();
        camion.setCodeEngin(request.codeEngin());
        camion.setModelEngin(request.modelEngin());
        camion.setAnneeEngin(request.anneeEngin());
        camion.setImmatriculationEngin(request.immatriculationEngin());
        camion.setMarqueEngin(request.marqueEngin());
        camion.setEtatEngin(1);
        camion.setStatusEngin(request.statusEngin());
        camion.setTypCarbtEngin(request.typCarbtEngin());
        camion.setDateAcqEngin(request.dateAcqEngin());
        camion.setCoutHorLocEngin(request.coutHorLocEngin());
        camion.setPoidsVide(request.poidsVide());
        camion.setHorametre(request.horametre());
        camion.setChargeUtile(request.capaciteCharge());
        camion.setNbEssieux(request.nombreEssieux());
        camion.setVolumeBenne(request.volumeBenne());
        camion.setDateCreation(LocalDate.now());
        return camion;
    }

    private CamionResponse toResponse(Camion camion) {

        return new CamionResponse(
                camion.getId(),
                camion.getCodeEngin(),
                camion.getModelEngin(),
                camion.getAnneeEngin(),
                camion.getImmatriculationEngin(),
                camion.getMarqueEngin(),
                camion.getEtatEngin(),
                camion.getStatusEngin(),
                camion.getTypCarbtEngin(),
                camion.getDateAcqEngin(),
                camion.getCoutHorLocEngin(),
                camion.getPoidsVide(),
                camion.getHorametre(),
                camion.getChargeUtile(),
                camion.getNbEssieux(),
                camion.getVolumeBenne()
        );
    }
}
