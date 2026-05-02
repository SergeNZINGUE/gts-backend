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
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"missions", "factures", "client", "conducteur", "engins"})
@Table(name = "LOCATIONS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Locations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private String statut; // EN_COURS, TERMINE

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Missions> missions = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<Facture> factures = new ArrayList<>();

    private String codeLocation;
    private Integer nbJoursLocation;
    private String siteLocation ;
    private Long coutHoraireLocation;
    private Long coutJournalierLocation;
    private Integer nbHeureLocation;
    private Date dateDbtLoc;
    private Date dateFinLoc;
    private Integer etatLocation; // 1=actif 2 validé 3 livré
    @ManyToOne
    @JoinColumn(name = "CODE_CLIENT")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "CODE_CONDUCTEUR")
    private Conducteur conducteur;

    @ManyToOne
    @JoinColumn(name = "CODE_ENGIN")
    private Engins  engins;

    //Gestion des logs
    LocalDate dateCreation;
    LocalDate dateModification;
}
