package com.gts.backgts.entites;

import com.gts.backgts.enums.StatutVGP;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "controles_vgp")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControleVGP {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "engin_id", nullable = false)
        private Engins engin;

        private LocalDate dateDernierControle;
        private LocalDate dateProchaineEcheance;

        private String organismeControleur; // Ex: Veritas, Apave
        private String numeroRapport;

        @Enumerated(EnumType.STRING)
        private StatutVGP resultat; // CONFORME, AVEC_RESERVES, NON_CONFORME
        private String reserveVGP;// si reserves
        private Boolean estAlerteActive = true;

        LocalDate dateCreation;
        LocalDate dateModification;

}
