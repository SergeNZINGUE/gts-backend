package com.gts.backgts.repository;

import com.gts.backgts.entites.PieceRechange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PieceRechangeRepository extends JpaRepository<PieceRechange, Long> {

    List<PieceRechange> findByDesignationContainingIgnoreCase(String designation);

    List<PieceRechange> findByReferenceConstructeurContainingIgnoreCase(String reference);

    /** Pièces dont le stock est inférieur ou égal au seuil d'alerte */
    @Query("SELECT p FROM PieceRechange p WHERE p.quantiteEnStock IS NOT NULL " +
           "AND p.seuilAlerteStock IS NOT NULL " +
           "AND p.quantiteEnStock <= p.seuilAlerteStock")
    List<PieceRechange> findAllEnAlerte();
}
