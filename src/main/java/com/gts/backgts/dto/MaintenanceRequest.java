package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeMaintenance;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record MaintenanceRequest(

        @NotNull(message = "L'identifiant de l'engin est obligatoire")
        Long enginId,

        /** Conducteur associé à l'engin (optionnel) */
        Long conducteurId,

        String codeMaintenance,
        String libMaintenance,

        @NotNull(message = "La date d'intervention est obligatoire")
        LocalDate dateDbtMaintenance,

        Double horametreIntervention,

        @NotNull(message = "Le type de maintenance est obligatoire")
        TypeMaintenance type,

        String nomTechnicien,
        LocalTime heureDebut,
        LocalTime heureFin,

        /** Observations générales */
        String descriptionTravaux,

        Double coutMainOeuvre,

        /** Prochaine maintenance dans X heures-compteur */
        Double prochaineEcheanceHeures,

        /** Date cible de la prochaine maintenance */
        LocalDate prochaineMaintenanceDate,

        Boolean enginOperationnel,
        String signatureTechnicien,
        String signatureResponsable,

        @Valid
        List<ActionInterventionRequest> actions,

        @Valid
        List<PieceConsommeeRequest> piecesConsommees
) {}
