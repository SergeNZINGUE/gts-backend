package com.gts.backgts.entites;

import com.gts.backgts.enums.TypeMouvement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventaires_etat_lieux")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtatDesLieux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    private LocalDateTime dateInventaire;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type; // DEPART, RETOUR

    private Double niveauCarburant; // En pourcentage ou litres
    private Double horametreConstat;

    @Column(columnDefinition = "TEXT")
    private String observationsGenerales;

    private Boolean aDesDegats;
    private String photosLien; // URL vers un stockage S3/Cloud des photos

    @ManyToOne
    private Users inspecteur; // L'agent qui a fait le contrôle
}
