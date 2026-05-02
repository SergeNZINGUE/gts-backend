package com.gts.backgts.entites;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"locationsCollection", "conducteurMissions"})
@Table(name = "CONDUCTEUR")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conducteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idConducteur;
    private String codeConducteur;
    private String nomConducteur;
    private String prenomsConducteur;
    private String telephone;
    private String permisCond;
    private String qualifications;
    private String cnibRef;
    private String cniDateEmi;
    private String cniDateExp;
    private String cniLieuEtab;
    private String imgCni;
    private String imgConducteur;
    private String imgPermis;
    private LocalDate dateDebutEmp;
    private LocalDate dateFinEmp;
    private LocalDate dateNaissance;
    private String typEmpl;
    private Integer statutConducteur;
    LocalDate dateCreation;
    LocalDate dateModification;

    @OneToMany(mappedBy = "conducteur")
    private Collection<Locations> locationsCollection = new ArrayList<>();

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConducteurMission> conducteurMissions = new ArrayList<>();
}
