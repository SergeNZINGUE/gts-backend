package com.gts.backgts.services;

import com.gts.backgts.dto.PelleRequest;
import com.gts.backgts.dto.PelleResponse;
import com.gts.backgts.entites.Pelle;
import com.gts.backgts.entites.TypeEngin;
import com.gts.backgts.repository.PelleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PelleService {
    private final TypeEnginService typeEnginService;
    private final PelleRepository pelleRepository;

    public PelleResponse create(PelleRequest request) {
        Pelle pelle = toEntity(request);
        pelle.setDateCreation(LocalDate.now());
        pelle.setDateModification(LocalDate.now());
        return toResponse(pelleRepository.save(pelle));
    }

    @Transactional(readOnly = true)
    public List<PelleResponse> getAll() {
        return pelleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PelleResponse getById(Long id) {
        Pelle pelle = pelleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pelle introuvable avec id: " + id));
        return toResponse(pelle);
    }

    public PelleResponse update(Long id, PelleRequest request) {
        Pelle pelle = pelleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pelle introuvable avec id: " + id));

        Pelle updated = toEntity(request);
        pelle.setCodeEngin(updated.getCodeEngin());
        pelle.setModelEngin(updated.getModelEngin());
        pelle.setAnneeEngin(updated.getAnneeEngin());
        pelle.setImmatriculationEngin(updated.getImmatriculationEngin());
        pelle.setTypeEngin(updated.getTypeEngin());
        pelle.setMarqueEngin(updated.getMarqueEngin());
        pelle.setStatusEngin(updated.getStatusEngin());
        pelle.setEtatEngin(updated.getEtatEngin());
        pelle.setTypCarbtEngin(updated.getTypCarbtEngin());
        pelle.setDateAcqEngin(updated.getDateAcqEngin());
        pelle.setCoutHorLocEngin(updated.getCoutHorLocEngin());
        pelle.setPoidsVide(updated.getPoidsVide());
        pelle.setHorametre(updated.getHorametre());
        pelle.setDateModification(LocalDate.now());
        pelle.setProfondeurFouille(updated.getProfondeurFouille());
        pelle.setTypeTrain(updated.getTypeTrain());

        return toResponse(pelleRepository.save(pelle));
    }

    public void delete(Long id) {
        if (!pelleRepository.existsById(id)) {
            throw new IllegalArgumentException("Pelle introuvable avec id: " + id);
        }
        pelleRepository.deleteById(id);
    }

    private Pelle toEntity(PelleRequest request) {
        TypeEngin typeEngin = request.typeEnginId() != null
                ? pelleRepository.findById(request.typeEnginId())
                  .orElseThrow(() -> new RuntimeException("Type engin non trouvé")).getTypeEngin()
                : null;

        Pelle pelle = new Pelle();
        pelle.setCodeEngin(request.codeEngin());
        pelle.setModelEngin(request.modelEngin());
        pelle.setAnneeEngin(request.anneeEngin());
        pelle.setImmatriculationEngin(request.immatriculationEngin());
        pelle.setTypeEngin(typeEngin);
        pelle.setMarqueEngin(request.marqueEngin());
        pelle.setStatusEngin(request.statusEngin());
        pelle.setEtatEngin(request.etatEngin());
        pelle.setTypCarbtEngin(request.typCarbtEngin());
        pelle.setDateAcqEngin(request.dateAcqEngin());
        pelle.setCoutHorLocEngin(request.coutHorLocEngin());
        pelle.setPoidsVide(request.poidsVide());
        pelle.setHorametre(request.horametre());
        pelle.setDateCreation(request.dateCreation());
        pelle.setDateModification(request.dateModification());
        pelle.setProfondeurFouille(request.profondeurFouille());
        pelle.setTypeTrain(request.typeTrain());
        return pelle;
    }

    private PelleResponse toResponse(Pelle pelle) {
        return new PelleResponse(
                pelle.getId(),
                pelle.getCodeEngin(),
                pelle.getModelEngin(),
                pelle.getAnneeEngin(),
                pelle.getImmatriculationEngin(),
                pelle.getTypeEngin() != null
                        ? String.valueOf(typeEnginService.toResponse(pelle.getTypeEngin()))
                        : null,
                pelle.getMarqueEngin(),
                pelle.getStatusEngin(),
                pelle.getEtatEngin(),
                pelle.getTypCarbtEngin(),
                pelle.getDateAcqEngin(),
                pelle.getCoutHorLocEngin(),
                pelle.getPoidsVide(),
                pelle.getHorametre(),
                pelle.getDateCreation(),
                pelle.getDateModification(),
                pelle.getProfondeurFouille(),
                pelle.getTypeTrain()
        );
    }
}
