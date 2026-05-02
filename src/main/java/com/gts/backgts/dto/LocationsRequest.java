package com.gts.backgts.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.Date;

public record LocationsRequest(
        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotBlank(message = "Le statut est obligatoire")
        String statut,

        @NotBlank(message = "Le code location est obligatoire")
        String codeLocation,

        @NotNull(message = "La quantité de location est obligatoire")
        @PositiveOrZero(message = "La quantité de location doit être positive ou nulle")
        Integer nbJoursLocation,

        @NotBlank(message = "Le site de location est obligatoire")
        String siteLocation,

        Date dateDbtLoc,
        Date dateFinLoc,

        @PositiveOrZero(message = "Le coût horaire de location doit être positif ou nul")
        Long coutHoraireLocation,

        @PositiveOrZero(message = "Le coût journalier de location doit être positif ou nul")
        Long coutJournalierLocation,

        @PositiveOrZero(message = "Le nb heure de location doit être positif ou nul")
        Integer nbHeureLocation,

        @NotNull(message = "L'état de location est obligatoire")
        @PositiveOrZero(message = "L'état de location doit être positif ou nul")
        Integer etatLocation,

        @NotNull(message = "Le client est obligatoire")
        Long clientId,

        @NotNull(message = "Le conducteur est obligatoire")
        Long conducteurId,

        @NotNull(message = "L'engin est obligatoire")
        Long enginId,

        LocalDate dateCreation,
        LocalDate dateModification
) {
        @AssertTrue(message = "Veuillez renseigner soit le coût horaire, soit le coût journalier de la location")
        public boolean isCoutLocationValide() {
                return (coutHoraireLocation != null && coutJournalierLocation == null)
                        || (coutHoraireLocation == null && coutJournalierLocation != null);
        }
}
