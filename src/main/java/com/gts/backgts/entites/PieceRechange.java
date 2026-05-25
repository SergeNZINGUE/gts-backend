package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PieceRechange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;
    private String referenceConstructeur;

    private Integer quantiteEnStock;
    private Integer seuilAlerteStock; // Pour déclencher une commande

    private Double prixUnitaireAchat;
    LocalDate dateCreation;
    LocalDate dateModification;

    public boolean isAlerteReapprovisionnement (){
        return  quantiteEnStock != null
                && seuilAlerteStock != null
                && quantiteEnStock <= seuilAlerteStock;
    }
    public String getMessageAlerteStock() {
        if (isAlerteReapprovisionnement()) {
            return "Alerte de réapprovisionnement pour la pièce " + designation + ". Quantité en stock : " + quantiteEnStock + ", seuil d'alerte : " + seuilAlerteStock;
        }
        return null;
    }
}
