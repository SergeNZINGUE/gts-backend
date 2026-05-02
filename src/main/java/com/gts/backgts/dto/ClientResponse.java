package com.gts.backgts.dto;

import com.gts.backgts.entites.ActiviteClient;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record ClientResponse(
        Long id,
        String codeClient,
        String nameClient,
        String raison_sociale,
        String pays,
        String ville,
        String email,
        String phoneNumber,
        String personneRessource,
        String telPersonneRessource,
        String adresseEntreprise,
        String rccmClient,
        String numeroIFUEntreprise,
        String regimeFiscalEntreprise,
        String numeroCompteBancaire,
        LocalDate dateCreation,
        LocalDate dateModification,
        String cheminLogoEntreprise,
        ActiviteClientResponse secteur_activite
) {
}
