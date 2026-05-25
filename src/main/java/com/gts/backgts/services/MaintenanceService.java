package com.gts.backgts.services;

import com.gts.backgts.dto.*;
import com.gts.backgts.entites.*;
import com.gts.backgts.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EnginsRepository enginsRepository;
    private final ConducteurRepository conducteurRepository;
    private final PieceRechangeRepository pieceRechangeRepository;

    // ── Création ───────────────────────────────────────────────────────────

    public MaintenanceResponse createMaintenance(MaintenanceRequest request) {

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Engin introuvable avec id: " + request.enginId()));

        Maintenance maintenance = new Maintenance();
        mapRequestToEntity(request, maintenance, engin);
        maintenance.setDateCreation(LocalDate.now());

        Maintenance saved = maintenanceRepository.save(maintenance);
        return toResponse(saved);
    }

    // ── Lecture ────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public MaintenanceResponse getMaintenanceById(Long id) {
        Maintenance m = findOrThrow(id);
        return toResponse(m);
    }

    @Transactional(readOnly = true)
    public Page<MaintenanceSummaryResponse> getAllMaintenances(Pageable pageable) {
        return maintenanceRepository.findAll(pageable)
                .map(this::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public List<MaintenanceSummaryResponse> getMaintenancesByEngin(Long enginId) {
        return maintenanceRepository.findByEnginsId(enginId)
                .stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    // ── Mise à jour ────────────────────────────────────────────────────────

    public MaintenanceResponse updateMaintenance(Long id, MaintenanceRequest request) {

        Maintenance maintenance = findOrThrow(id);

        Engins engin = enginsRepository.findById(request.enginId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Engin introuvable avec id: " + request.enginId()));

        // On efface les anciennes collections (orphanRemoval = true s'occupe de la DB)
        maintenance.getActions().clear();
        maintenance.getPiecesConsommees().clear();

        mapRequestToEntity(request, maintenance, engin);
        maintenance.setDateModification(LocalDate.now());

        return toResponse(maintenanceRepository.save(maintenance));
    }

    // ── Suppression ────────────────────────────────────────────────────────

    public void deleteMaintenance(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Maintenance introuvable avec id: " + id);
        }
        maintenanceRepository.deleteById(id);
    }

    // ── Mapping interne ────────────────────────────────────────────────────

    private void mapRequestToEntity(MaintenanceRequest request,
                                    Maintenance maintenance,
                                    Engins engin) {
        maintenance.setEngins(engin);
        maintenance.setCodeMaintenance(request.codeMaintenance());
        maintenance.setLibMaintenance(request.libMaintenance());
        maintenance.setDateDbtMaintenance(request.dateDbtMaintenance());
        maintenance.setHorametreIntervention(request.horametreIntervention());
        maintenance.setType(request.type());
        maintenance.setNomTechnicien(request.nomTechnicien());
        maintenance.setHeureDebut(request.heureDebut());
        maintenance.setHeureFin(request.heureFin());
        maintenance.setDescriptionTravaux(request.descriptionTravaux());
        maintenance.setCoutMainOeuvre(request.coutMainOeuvre());
        maintenance.setProchaineEcheanceHeures(request.prochaineEcheanceHeures());
        maintenance.setProchaineMaintenanceDate(request.prochaineMaintenanceDate());
        maintenance.setEnginOperationnel(request.enginOperationnel());
        maintenance.setSignatureTechnicien(request.signatureTechnicien());
        maintenance.setSignatureResponsable(request.signatureResponsable());

        // Conducteur (optionnel)
        if (request.conducteurId() != null) {
            Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Conducteur introuvable avec id: " + request.conducteurId()));
            maintenance.setConducteur(conducteur);
        } else {
            maintenance.setConducteur(null);
        }

        // Actions d'intervention
        if (request.actions() != null) {
            for (ActionInterventionRequest ar : request.actions()) {
                ActionIntervention action = new ActionIntervention();
                action.setMaintenance(maintenance);
                action.setSysteme(ar.systeme());
                action.setDescriptionAction(ar.descriptionAction());
                action.setResultat(ar.resultat());
                maintenance.getActions().add(action);
            }
        }

        // Pièces consommées
        if (request.piecesConsommees() != null) {
            for (PieceConsommeeRequest pr : request.piecesConsommees()) {
                PieceConsommee piece = new PieceConsommee();
                piece.setMaintenance(maintenance);
                piece.setDesignation(pr.designation());
                piece.setReferencePiece(pr.referencePiece());
                piece.setQuantite(pr.quantite());
                piece.setUnite(pr.unite());
                piece.setPrixUnitaire(pr.prixUnitaire());

                if (pr.pieceRechangeId() != null) {
                    PieceRechange pr2 = pieceRechangeRepository.findById(pr.pieceRechangeId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Pièce de rechange introuvable avec id: " + pr.pieceRechangeId()));
                    piece.setPieceRechange(pr2);
                    // Compléter designation/référence depuis le catalogue si non renseigné
                    if (piece.getDesignation() == null || piece.getDesignation().isBlank()) {
                        piece.setDesignation(pr2.getDesignation());
                    }
                    if (piece.getReferencePiece() == null || piece.getReferencePiece().isBlank()) {
                        piece.setReferencePiece(pr2.getReferenceConstructeur());
                    }
                }

                maintenance.getPiecesConsommees().add(piece);
            }
        }
    }

    private Maintenance findOrThrow(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Maintenance introuvable avec id: " + id));
    }

    // ── Conversions DTO ────────────────────────────────────────────────────

    private MaintenanceResponse toResponse(Maintenance m) {
        List<ActionInterventionResponse> actions = m.getActions().stream()
                .map(a -> new ActionInterventionResponse(
                        a.getId(), a.getSysteme(), a.getDescriptionAction(), a.getResultat()))
                .toList();

        List<PieceConsommeeResponse> pieces = m.getPiecesConsommees().stream()
                .map(p -> new PieceConsommeeResponse(
                        p.getId(),
                        p.getPieceRechange() != null ? p.getPieceRechange().getId() : null,
                        p.getReferencePiece(),
                        p.getDesignation(),
                        p.getQuantite(),
                        p.getUnite(),
                        p.getPrixUnitaire(),
                        p.getMontantTotal()))
                .toList();

        Engins engin = m.getEngins();
        Conducteur conducteur = m.getConducteur();

        return new MaintenanceResponse(
                m.getId(),
                m.getCodeMaintenance(),
                m.getLibMaintenance(),
                m.getDateDbtMaintenance(),
                m.getHorametreIntervention(),
                m.getType(),
                engin != null ? engin.getId() : null,
                engin != null ? engin.getCodeEngin() : null,
                engin != null ? engin.getModelEngin() : null,
                conducteur != null ? conducteur.getIdConducteur() : null,
                conducteur != null ? conducteur.getNomConducteur() : null,
                conducteur != null ? conducteur.getPrenomsConducteur() : null,
                m.getNomTechnicien(),
                m.getHeureDebut(),
                m.getHeureFin(),
                m.getDescriptionTravaux(),
                m.getCoutMainOeuvre(),
                m.getCoutPieces(),
                m.getCoutTotal(),
                m.getProchaineEcheanceHeures(),
                m.getProchaineMaintenanceDate(),
                m.getEnginOperationnel(),
                m.getSignatureTechnicien(),
                m.getSignatureResponsable(),
                actions,
                pieces,
                m.getDateCreation(),
                m.getDateModification()
        );
    }

    private MaintenanceSummaryResponse toSummaryResponse(Maintenance m) {
        Engins engin = m.getEngins();
        Conducteur conducteur = m.getConducteur();

        return new MaintenanceSummaryResponse(
                m.getId(),
                m.getCodeMaintenance(),
                m.getLibMaintenance(),
                m.getDateDbtMaintenance(),
                m.getHorametreIntervention(),
                m.getType(),
                engin != null ? engin.getId() : null,
                engin != null ? engin.getCodeEngin() : null,
                engin != null ? engin.getModelEngin() : null,
                conducteur != null ? conducteur.getIdConducteur() : null,
                conducteur != null ? conducteur.getNomConducteur() : null,
                conducteur != null ? conducteur.getPrenomsConducteur() : null,
                m.getNomTechnicien(),
                m.getEnginOperationnel(),
                m.getCoutTotal(),
                m.getActions() != null ? m.getActions().size() : 0,
                m.getPiecesConsommees() != null ? m.getPiecesConsommees().size() : 0,
                m.getDateCreation()
        );
    }
}
