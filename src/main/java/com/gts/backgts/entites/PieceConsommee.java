package com.gts.backgts.entites;

import jakarta.persistence.*;
import lombok.*;

/**
 * Pièce ou consommable utilisé lors d'une intervention de maintenance.
 * Peut référencer une pièce du catalogue (PieceRechange) ou être saisie librement.
 */
@Entity
@Table(name = "PIECE_CONSOMMEE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "maintenance")
public class PieceConsommee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private Maintenance maintenance;

    /**
     * Pièce du catalogue (nullable — si saisie libre, cette FK reste nulle).
     * Utile pour le lien avec le stock et la mise à jour automatique des quantités.
     */
    @ManyToOne
    @JoinColumn(name = "piece_rechange_id")
    private PieceRechange pieceRechange;

    /** Référence constructeur saisie librement (peut être déduite de pieceRechange) */
    private String referencePiece;

    /** Désignation saisie librement (peut être déduite de pieceRechange) */
    @Column(nullable = false)
    private String designation;

    /** Quantité consommée */
    @Column(nullable = false)
    private Double quantite;

    /** Unité : L, kg, pce, m, h … */
    @Column(nullable = false)
    private String unite;

    /** Prix unitaire au moment de l'intervention (nullable) */
    private Double prixUnitaire;

    // ── calculé ────────────────────────────────────────────────────────────
    @Transient
    public Double getMontantTotal() {
        if (quantite == null || prixUnitaire == null) return null;
        return quantite * prixUnitaire;
    }
}
