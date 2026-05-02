package com.gts.backgts.services;

import com.gts.backgts.dto.ReglementRequest;
import com.gts.backgts.dto.ReglementResponse;
import com.gts.backgts.entites.Client;
import com.gts.backgts.entites.Facture;
import com.gts.backgts.entites.Reglement;
import com.gts.backgts.repository.ClientRepository;
import com.gts.backgts.repository.FactureRepository;
import com.gts.backgts.repository.ReglementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReglementService {

    private final ReglementRepository reglementRepository;
    private final FactureRepository factureRepository;
    private final ClientRepository clientRepository;

    public ReglementResponse createReglement(ReglementRequest request) {

        Facture facture = factureRepository.findById(request.factureId())
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + request.factureId()));

        if (facture.getMissionsFacturees() == null || facture.getMissionsFacturees().isEmpty()) {
            throw new IllegalArgumentException("Impossible de créer un règlement : la facture ne contient aucune mission.");
        }

        if (facture.getMontantTTC() == null || facture.getMontantTTC() <= 0) {
            throw new IllegalArgumentException("Impossible de créer un règlement : le montant total de la facture est nul.");
        }

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + request.clientId()));

        Reglement reglement = new Reglement();
        reglement.setDateReglement(request.dateReglement());
        reglement.setMontantVerse(request.montantVerse());
        reglement.setModePaiement(request.modePaiement());
        reglement.setFacture(facture);
        reglement.setClient(client);
        reglement.setDateCreation(LocalDate.now());

        Reglement savedReglement = reglementRepository.save(reglement);

        updateFacturePaiementStatus(facture);

        return toResponse(savedReglement);
    }

    @Transactional(readOnly = true)
    public List<ReglementResponse> getAllReglements() {
        return reglementRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReglementResponse getReglementById(Long id) {
        Reglement reglement = reglementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Règlement introuvable avec id: " + id));
        return toResponse(reglement);
    }

    @Transactional(readOnly = true)
    public List<ReglementResponse> getReglementByFactureId(Long id) {
        return reglementRepository.findByFacture_Id(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    public ReglementResponse updateReglement(Long id, ReglementRequest request) {
        Reglement reglement = reglementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Règlement introuvable avec id: " + id));

        Facture facture = factureRepository.findById(request.factureId())
                .orElseThrow(() -> new IllegalArgumentException("Facture introuvable avec id: " + request.factureId()));

        if (facture.getMissionsFacturees() == null || facture.getMissionsFacturees().isEmpty()) {
            throw new IllegalArgumentException("Impossible de créer un règlement : la facture ne contient aucune mission.");
        }

        if (facture.getMontantTTC() == null || facture.getMontantTTC() <= 0) {
            throw new IllegalArgumentException("Impossible de créer un règlement : le montant total de la facture est nul.");
        }
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + request.clientId()));

        reglement.setDateReglement(request.dateReglement());
        reglement.setMontantVerse(request.montantVerse());
        reglement.setModePaiement(request.modePaiement());
        reglement.setFacture(facture);
        reglement.setClient(client);
        reglement.setDateModification(LocalDate.now());

        Reglement savedReglement = reglementRepository.save(reglement);

        updateFacturePaiementStatus(facture);

        return toResponse(savedReglement);
    }

    public void deleteReglement(Long id) {
        if (!reglementRepository.existsById(id)) {
            throw new IllegalArgumentException("Règlement introuvable avec id: " + id);
        }
        reglementRepository.deleteById(id);
    }

    private void updateFacturePaiementStatus(Facture facture) {
        double montantTotal = facture.getMontantTTC();
        double montantPaye = facture.getReglements()
                .stream()
                .mapToDouble(reg -> reg.getMontantVerse() != null ? reg.getMontantVerse() : 0.0)
                .sum();

        if (montantPaye <= 0) {
            facture.setEtatPaiement("BROUILLON");
        } else if (montantPaye < montantTotal) {
            facture.setEtatPaiement("PARTIELLEMENT_PAYEE");
        } else {
            facture.setEtatPaiement("PAYEE");
        }

        facture.setDateModification(LocalDate.now());
        factureRepository.save(facture);
    }

    private ReglementResponse toResponse(Reglement reglement) {

        double montantTotalFacture = reglement.getFacture() != null ? reglement.getFacture().getMontantTTC() : 0.0;
        double montantDejaPaye = reglement.getFacture() != null
                ? reglement.getFacture().getReglements()
                  .stream()
                  .mapToDouble(r -> r.getMontantVerse() != null ? r.getMontantVerse() : 0.0)
                  .sum()
                : 0.0;

        double resteAPayer = Math.max(montantTotalFacture - montantDejaPaye, 0.0);


        return new ReglementResponse(
                reglement.getId(),
                reglement.getFacture() != null ? reglement.getFacture().getId() : null,
                reglement.getFacture() != null ? reglement.getFacture().getEtatPaiement() : null,
                reglement.getClient() != null ? reglement.getClient().getId() : null,
                reglement.getClient() != null ? reglement.getClient().getDescriptionEntreprise() : null,
                reglement.getDateReglement(),
                reglement.getMontantVerse(),
                reglement.getModePaiement(),
                montantTotalFacture,
                montantDejaPaye,
                resteAPayer,
                reglement.getDateCreation(),
                reglement.getDateModification()
        );
    }
}
