package com.gts.backgts.entites;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.time.LocalTime;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"conducteur", "mission"})
@Table(name = "CONDUCTEUR_MISSION")

public class ConducteurMission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeCondMission;
    private LocalDate dateMCd;
    private LocalTime heureMCd;
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "CONDUCTEUR_ID", nullable = false)
    private Conducteur conducteur;

    @ManyToOne
    @JoinColumn(name = "MISSION_ID", nullable = false)
    private Missions mission;
}
