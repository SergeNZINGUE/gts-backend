package com.gts.backgts.entites;

import com.gts.backgts.enums.TypeEtatEngins;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"locationsCollection", "maintenanceCollection"})
@Table(name = "ENGINS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Engins implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String codeEngin;
    private String modelEngin;
    private LocalDate anneeEngin;
    private String immatriculationEngin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_engin_id")
    private TypeEngin typeEngin;


    private String marqueEngin;
    private Integer etatEngin;

    @Enumerated(EnumType.STRING)
    private TypeEtatEngins statusEngin;
    private String typCarbtEngin;
    private LocalDate dateAcqEngin;
    private Long coutHorLocEngin;
    LocalDate dateCreation;
    LocalDate dateModification;

    @OneToMany(mappedBy = "engins")
    private Collection<Locations> locationsCollection= new ArrayList<>();

    @OneToMany(mappedBy = "engins", cascade = CascadeType.ALL)
    private Collection<Maintenance> maintenanceCollection= new ArrayList<>();

    @OneToMany(mappedBy = "engin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <AssuranceEngin> assuranceEngins = new ArrayList<>();

    private Double poidsVide;
    private Double horametre;
}


