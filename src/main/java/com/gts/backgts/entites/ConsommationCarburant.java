package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "consommation_carburant")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ConsommationCarburant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engins_id", nullable = false)
    private Engins engin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conducteur", nullable = false)
    private Conducteur conducteur; // Qui a fait le plein ?

    @Column(nullable = false)
    private LocalDateTime datePlein;

    @Column(nullable = false)
    private Double quantiteLitres;

    @Column(nullable = false)
    private Double prixUnitaire; // Le prix du gasoil varie souvent

    @Column(nullable = false)
    private Double horametreAuPlein; // Heures au compteur lors du ravitaillement

    private String lieuRavitaillement; // Cuve de chantier, station externe, camion-citerne

    // Calcul de commodité
    public Double getCoutTotal() {
        return quantiteLitres * prixUnitaire;
    }

    LocalDate dateCreation;
    LocalDate dateModification;
}

