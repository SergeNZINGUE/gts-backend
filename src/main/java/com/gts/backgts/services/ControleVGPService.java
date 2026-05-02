package com.gts.backgts.services;

import com.gts.backgts.dto.ControleVGPRequest;
import com.gts.backgts.dto.ControleVGPResponse;
import com.gts.backgts.entites.ControleVGP;
import com.gts.backgts.entites.Engins;
import com.gts.backgts.repository.ControleVGPRepository;
import com.gts.backgts.repository.EnginsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ControleVGPService {

    private final ControleVGPRepository controleVGPRepository;
    private final EnginsRepository enginsRepository;

    public ControleVGPResponse createControleVGP(ControleVGPRequest request) {
        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        ControleVGP controleVGP = new ControleVGP();
        controleVGP.setEngin(engin);
        controleVGP.setDateDernierControle(request.dateDernierControle());
        controleVGP.setDateProchaineEcheance(request.dateProchaineEcheance());
        controleVGP.setOrganismeControleur(request.organismeControleur());
        controleVGP.setNumeroRapport(request.numeroRapport());
        controleVGP.setResultat(request.resultat());
        controleVGP.setReserveVGP(request.reserveVGP());
        controleVGP.setEstAlerteActive(request.estAlerteActive() != null ? request.estAlerteActive() : true);
        controleVGP.setDateCreation(LocalDate.now());

        return toResponse(controleVGPRepository.save(controleVGP));
    }

    @Transactional(readOnly = true)
    public List<ControleVGPResponse> getAllControlesVGP() {
        return controleVGPRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ControleVGPResponse getControleVGPById(Long id) {
        ControleVGP controleVGP = controleVGPRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contrôle VGP introuvable avec id: " + id));
        return toResponse(controleVGP);
    }

    public ControleVGPResponse updateControleVGP(Long id, ControleVGPRequest request) {
        ControleVGP controleVGP = controleVGPRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contrôle VGP introuvable avec id: " + id));

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException("Engin introuvable avec id: " + request.enginId()));

        controleVGP.setEngin(engin);
        controleVGP.setDateDernierControle(request.dateDernierControle());
        controleVGP.setDateProchaineEcheance(request.dateProchaineEcheance());
        controleVGP.setOrganismeControleur(request.organismeControleur());
        controleVGP.setNumeroRapport(request.numeroRapport());
        controleVGP.setResultat(request.resultat());
        controleVGP.setReserveVGP(request.reserveVGP());
        controleVGP.setEstAlerteActive(request.estAlerteActive());
        controleVGP.setDateModification(LocalDate.now());

        return toResponse(controleVGPRepository.save(controleVGP));
    }

    public void deleteControleVGP(Long id) {
        if (!controleVGPRepository.existsById(id)) {
            throw new IllegalArgumentException("Contrôle VGP introuvable avec id: " + id);
        }
        controleVGPRepository.deleteById(id);
    }

    private ControleVGPResponse toResponse(ControleVGP controleVGP) {
        return new ControleVGPResponse(
                controleVGP.getId(),
                controleVGP.getEngin() != null ? controleVGP.getEngin().getId() : null,
                controleVGP.getEngin() != null ? controleVGP.getEngin().getCodeEngin() : null,
                controleVGP.getEngin() != null ? controleVGP.getEngin().getModelEngin() : null,
                controleVGP.getDateDernierControle(),
                controleVGP.getDateProchaineEcheance(),
                controleVGP.getOrganismeControleur(),
                controleVGP.getNumeroRapport(),
                controleVGP.getResultat(),
                controleVGP.getReserveVGP(),
                controleVGP.getEstAlerteActive(),
                controleVGP.getDateCreation(),
                controleVGP.getDateModification()
        );
    }
}
