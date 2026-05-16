package com.gts.backgts.entites;

import com.gts.backgts.enums.TypeMaintenance;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@ToString

@Getter
@Setter

@Table(name = "MAINTENANCE")
public class Maintenance implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeMaintenance;
    private String libMaintenance;
    private LocalDate dateDbtMaintenance;
    private Date dateProchaineRevision;
    private LocalDate dateDerniereRevision;
    private Double horametreIntervention; // Heures affichées au compteur lors du soin
    private Long huile10L;
    private Long huile40L;
    private Long huile90L;
    @Enumerated(EnumType.STRING)
    private TypeMaintenance type; //

    private String descriptionTravaux;
    private Double coutPieces;
    private Double coutMainOeuvre;
    private Double prochaineEcheanceHeures; //ex prochaine maintenance dans 5000 H
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "CODE_ENGIN")
    private Engins  engins;


    @ManyToMany
    @JoinTable(
        name = "maintenance_piece_rechange",
        joinColumns = @JoinColumn(name = "maintenance_id"),
        inverseJoinColumns = @JoinColumn(name = "piece_rechange_id")
    )
    private Set<PieceRechange> piecesUtilisees = new HashSet<>();
}
