package com.gts.backgts.entites;

import com.gts.backgts.enums.TypeMaintenance;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"actions", "piecesConsommees"})
@Getter
@Setter
@Table(name = "MAINTENANCE")
public class Maintenance implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeMaintenance;
    private String libMaintenance;
    private LocalDate dateDbtMaintenance;

    /** Horamètre (heures compteur) au moment de l'intervention */
    private Double horametreIntervention;

    @Enumerated(EnumType.STRING)
    private TypeMaintenance type;

    /** Nom/prénom du technicien ayant réalisé l'intervention */
    private String nomTechnicien;

    /** Heure de début d'intervention */
    private LocalTime heureDebut;

    /** Heure de fin d'intervention */
    private LocalTime heureFin;

    /** Observations générales / description des travaux */
    @Column(columnDefinition = "TEXT")
    private String descriptionTravaux;

    /** Coût total main-d'œuvre */
    private Double coutMainOeuvre;

    /** Prochaine révision dans X heures-compteur */
    private Double prochaineEcheanceHeures;

    /** Date cible de la prochaine maintenance */
    private LocalDate prochaineMaintenanceDate;

    /** L'engin était-il remis en service opérationnel à la fin ? */
    private Boolean enginOperationnel;

    /** Référence URL ou identifiant de la signature du technicien */
    private String signatureTechnicien;

    /** Référence URL ou identifiant de la signature du responsable */
    private String signatureResponsable;

    LocalDate dateCreation;
    LocalDate dateModification;

    @ManyToOne
    @JoinColumn(name = "CODE_ENGIN")
    private Engins engins;

    /** Conducteur associé à l'engin au moment de l'intervention (optionnel) */
    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Conducteur conducteur;

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionIntervention> actions = new ArrayList<>();

    @OneToMany(mappedBy = "maintenance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PieceConsommee> piecesConsommees = new ArrayList<>();

    // ── helpers ────────────────────────────────────────────────────────────
    public Double getCoutPieces() {
        if (piecesConsommees == null) return 0.0;
        return piecesConsommees.stream()
                .mapToDouble(p -> {
                    if (p.getQuantite() == null) return 0.0;
                    double pu = p.getPrixUnitaire() != null ? p.getPrixUnitaire() : 0.0;
                    return p.getQuantite() * pu;
                })
                .sum();
    }

    public Double getCoutTotal() {
        double mo = coutMainOeuvre != null ? coutMainOeuvre : 0.0;
        return getCoutPieces() + mo;
    }
}
