package com.gts.backgts.services;

import com.gts.backgts.dto.PieceRechangeRequest;
import com.gts.backgts.dto.PieceRechangeResponse;
import com.gts.backgts.entites.PieceRechange;
import com.gts.backgts.repository.PieceRechangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PieceRechangeService {

    private final PieceRechangeRepository pieceRechangeRepository;

    public PieceRechangeResponse createPieceRechange(PieceRechangeRequest request) {
        PieceRechange pieceRechange = new PieceRechange();
        pieceRechange.setDesignation(request.designation());
        pieceRechange.setReferenceConstructeur(request.referenceConstructeur());
        pieceRechange.setQuantiteEnStock(request.quantiteEnStock());
        pieceRechange.setSeuilAlerteStock(request.seuilAlerteStock());
        pieceRechange.setPrixUnitaireAchat(request.prixUnitaireAchat());
        pieceRechange.setDateCreation(LocalDate.now());

        return toResponse(pieceRechangeRepository.save(pieceRechange));
    }

    @Transactional(readOnly = true)
    public List<PieceRechangeResponse> getAllPiecesRechange() {
        return pieceRechangeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PieceRechangeResponse getPieceRechangeById(Long id) {
        PieceRechange pieceRechange = pieceRechangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pièce de rechange introuvable avec id: " + id));
        return toResponse(pieceRechange);
    }

    public PieceRechangeResponse updatePieceRechange(Long id, PieceRechangeRequest request) {
        PieceRechange pieceRechange = pieceRechangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pièce de rechange introuvable avec id: " + id));

        pieceRechange.setDesignation(request.designation());
        pieceRechange.setReferenceConstructeur(request.referenceConstructeur());
        pieceRechange.setQuantiteEnStock(request.quantiteEnStock());
        pieceRechange.setSeuilAlerteStock(request.seuilAlerteStock());
        pieceRechange.setPrixUnitaireAchat(request.prixUnitaireAchat());
        pieceRechange.setDateModification(LocalDate.now());

        return toResponse(pieceRechangeRepository.save(pieceRechange));
    }

    public void deletePieceRechange(Long id) {
        if (!pieceRechangeRepository.existsById(id)) {
            throw new IllegalArgumentException("Pièce de rechange introuvable avec id: " + id);
        }
        pieceRechangeRepository.deleteById(id);
    }

    private PieceRechangeResponse toResponse(PieceRechange pieceRechange) {
        return new PieceRechangeResponse(
                pieceRechange.getId(),
                pieceRechange.getDesignation(),
                pieceRechange.getReferenceConstructeur(),
                pieceRechange.getQuantiteEnStock(),
                pieceRechange.getSeuilAlerteStock(),
                pieceRechange.getPrixUnitaireAchat(),
                pieceRechange.isAlerteReapprovisionnement(),
                pieceRechange.getMessageAlerteStock(),
                pieceRechange.getDateCreation(),
                pieceRechange.getDateModification()
        );
    }
}
