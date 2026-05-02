package com.gts.backgts.dto;

import java.time.LocalDate;
import java.util.List;

public record ClientDetailsResponse (
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
        ActiviteClientResponse secteur_activite,
        List<LocationsResponse> locations
){
}
