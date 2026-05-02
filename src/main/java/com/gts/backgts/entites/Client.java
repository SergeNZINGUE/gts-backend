package com.gts.backgts.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"activiteClient", "locations", "reglements"})
@Builder
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String codeClient;
    private String designationEntreprise;
    private String descriptionEntreprise;
    private String paysEntreprise;
    private String villeEntreprise;
    private String communeEntreprise;
    private String email;
    private String cheminLogoEntreprise;
    private String telEntreprise;
    private String personneRessource;
    private String telPersonneRessource;
    private String adresseEntreprise;
    private String rccmClient;
    private String numeroIFUEntreprise;
    private String regimeFiscalEntreprise;
    private String numeroCompteBancaire;

    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "CODE_ACTIVITE_CLIENT")
    @JsonIgnoreProperties("clients")
    private ActiviteClient activiteClient;

    @OneToMany(mappedBy = "client")
    private List<Locations> locations = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Reglement> reglements = new ArrayList<>();
}
