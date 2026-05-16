package com.gts.backgts.dto;

import com.gts.backgts.enums.TypeMouvement;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record EtatDesLieuxRequest(
        @NotNull(message = "La location est obligatoire")
        Long locationId,

        @NotNull(message = "La date d'inventaire est obligatoire")
        LocalDateTime dateInventaire,

        @NotNull(message = "Le type de mouvement est obligatoire")
        TypeMouvement type,

        Double niveauCarburant,
        Double horametreConstat,
        String observationsGenerales,
        Boolean aDesDegats,
        MultipartFile photosLien,

        @NotNull(message = "L'inspecteur est obligatoire")
        Long inspecteurId
) {
}
