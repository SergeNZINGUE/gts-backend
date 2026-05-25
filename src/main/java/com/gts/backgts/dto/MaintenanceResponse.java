package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeMaintenance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record MaintenanceResponse(
        Long id,
        String codeMaintenance,
        String libMaintenance,
        LocalDate dateDbtMaintenance,
        Double horametreIntervention,
        TypeMaintenance type,

        // Engin
        Long enginId,
        String codeEngin,
        String modelEngin,

        // Conducteur (nullable)
        Long conducteurId,
        String nomConducteur,
        String prenomsConducteur,

        String nomTechnicien,
        LocalTime heureDebut,
        LocalTime heureFin,
        String descriptionTravaux,
        Double coutMainOeuvre,
        Double coutPieces,
        Double coutTotal,
        Double prochaineEcheanceHeures,
        LocalDate prochaineMaintenanceDate,
        Boolean enginOperationnel,
        String signatureTechnicien,
        String signatureResponsable,

        List<ActionInterventionResponse> actions,
        List<PieceConsommeeResponse> piecesConsommees,

        LocalDate dateCreation,
        LocalDate dateModification
) {}
