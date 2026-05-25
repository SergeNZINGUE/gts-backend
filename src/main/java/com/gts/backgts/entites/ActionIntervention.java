package com.gts.backgts.entites;

import com.gts.backgts.enums.SystemeIntervention;
import jakarta.persistence.*;
import lombok.*;

/**
 * Action réalisée lors d'une intervention de maintenance.
 * Chaque action est rattachée à un système technique (moteur, hydraulique, etc.)
 * et décrit librement ce qui a été fait + le résultat constaté.
 */
@Entity
@Table(name = "ACTION_INTERVENTION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "maintenance")
public class ActionIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;

    /** Système / organe concerné par l'action */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemeIntervention systeme;

    /** Description libre de l'action effectuée (ex : "Remplacement filtre à huile") */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String descriptionAction;

    /** Résultat ou observation après l'action (nullable) */
    @Column(columnDefinition = "TEXT")
    private String resultat;
}
