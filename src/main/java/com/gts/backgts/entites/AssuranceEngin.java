package com.gts.backgts.entites;


import com.gts.backgts.enums.StatutAssurance;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="ASSURANCE_ENGIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssuranceEngin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroPolice;

    private String compagnieAssurance;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private BigDecimal montant;

    private String documentUrl;

    LocalDate dateCreation;
    LocalDate dateModification;

    @Enumerated(EnumType.STRING)
    private StatutAssurance statut;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "engin_id",nullable = false)
    private Engins engin;
}
