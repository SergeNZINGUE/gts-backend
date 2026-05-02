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
@NoArgsConstructor
@AllArgsConstructor
public class Reglement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateReglement;
    private Double montantVerse;
    private String modePaiement; // VIREMENT, CHEQUE, ESPECES
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "FACTURE_ID")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
