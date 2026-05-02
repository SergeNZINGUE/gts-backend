package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"location", "missionsFacturees", "reglements"})
public class Facture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateEmission;
    private Double tauxTVA = 18.0; // Exemple
    private String etatPaiement; // BROUILLON, VALIDEE, PAYEE
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    @OneToMany(mappedBy = "facture")
    private List<Missions> missionsFacturees = new ArrayList<>();

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL)
    private List<Reglement> reglements = new ArrayList<>();

    public Double getMontantHT() {
        return missionsFacturees.stream().mapToDouble(Missions::getSousTotal).sum();
    }

    public Double getMontantTTC() {
        return getMontantHT() * (1 + (tauxTVA / 100));
    }

}
