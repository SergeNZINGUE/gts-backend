package com.gts.backgts.entites;

import com.gts.backgts.entites.Maintenance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "piecesUtilisees")
    private Set<Maintenance> maintenances = new HashSet<>();

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
