package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"location", "conducteurMissions", "facture"})
public class Missions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateTravail;
    private Double nbHeures;
    private Double tarifHoraireApplique;
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Locations location;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConducteurMission> conducteurMissions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "FACTURE_ID") // Permet de savoir si la mission est facturée
    private Facture facture;

    public Double getSousTotal() {
        return (nbHeures != null && tarifHoraireApplique != null) ? nbHeures * tarifHoraireApplique : 0.0;
    }
    private String codeMission;
    private LocalDate dateDebutMission;
    private LocalDate dateFinMission;
    private LocalTime heureDebutMission;
    private LocalTime heureFinMission;
    private Long kmDbtMission;
    private Long kmFinMission;

    private Long compteurDbtMission;
    private Long compteurFinMission;


    private Long carbtDbtMission;
    private Long carbtFinMission;
    private String materiauxMission;
    private Long qteMateriauxMission;

    private String statutMission;

    private String observationMission;

    private String prioriteMission;
    private String responsableMission;
    private String lieuMission;
    private String descriptionMission;

}








