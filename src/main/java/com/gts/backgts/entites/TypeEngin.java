package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "TYPE_ENGIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeEngin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String code;           // ex: PELLE, BENNE, BULLDOZER

    @Column(nullable = false, length = 100)
    private String libelle;        // ex: "Pelle hydraulique"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FamilleEngin famille;  // ENGIN_CHANTIER, CAMION,VEHICUL_LEGER, AUTRE

    @Column(length = 255)
    private String description;

    @Builder.Default
    private Boolean actif = true;

    private LocalDate dateCreation;
    private LocalDate dateModification;

    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDate.now();
        this.dateModification = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDate.now();
    }
}
