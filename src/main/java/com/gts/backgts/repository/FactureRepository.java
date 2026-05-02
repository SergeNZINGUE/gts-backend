package com.gts.backgts.repository;

import com.gts.backgts.entites.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    // Reporting
    List<Facture> findByEtatPaiementNot(String etatPaiement);
    List<Facture> findByEtatPaiementNotAndLocation_Client_Id(String etatPaiement, Long clientId);
}
