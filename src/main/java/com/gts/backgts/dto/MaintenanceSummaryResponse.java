package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeMaintenance;

import java.time.LocalDate;

/**
 * Vue allégée pour les listes de maintenances (sans les sous-collections détaillées).
 */
public record MaintenanceSummaryResponse(
        Long id,
        String codeMaintenance,
        String libMaintenance,
        LocalDate dateDbtMaintenance,
        Double horametreIntervention,
        TypeMaintenance type,
        Long enginId,
        String codeEngin,
        String modelEngin,
        Long conducteurId,
        String nomConducteur,
        String prenomsConducteur,
        String nomTechnicien,
        Boolean enginOperationnel,
        Double coutTotal,
        int nbActions,
        int nbPieces,
        LocalDate dateCreation
) {}
