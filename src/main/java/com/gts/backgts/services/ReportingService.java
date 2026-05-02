package com.gts.backgts.services;

import com.gts.backgts.dto.*;
import com.gts.backgts.entites.*;
import com.gts.backgts.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportingService {

    private final LocationsRepository locationsRepository;
    private final MissionsRepository missionsRepository;
    private final FactureRepository factureRepository;
    private final ConducteurRepository conducteurRepository;

    // L1 — État des locations sur période
    public RapportLocationPeriodeResponse rapportLocationsPeriode(LocalDate dateDebut, LocalDate dateFin) {
        List<Locations> locations = locationsRepository.findByDateDbtLocBetween(
                toDate(dateDebut, 0, 0, 0),
                toDate(dateFin, 23, 59, 59)
        );

        int enCours   = (int) locations.stream().filter(l -> "EN_COURS".equals(l.getStatut())).count();
        int terminees = (int) locations.stream().filter(l -> "TERMINEE".equals(l.getStatut())).count();

        return new RapportLocationPeriodeResponse(
                locations.size(), enCours, terminees,
                locations.stream().map(this::toLocationPeriodeDetail).toList()
        );
    }

    // L2 — Locations par client
    public RapportLocationClientResponse rapportLocationsClient(Long clientId, LocalDate dateDebut, LocalDate dateFin) {
        List<Locations> locations = (dateDebut != null && dateFin != null)
                ? locationsRepository.findByClient_IdAndDateDbtLocBetween(
                        clientId,
                        toDate(dateDebut, 0, 0, 0),
                        toDate(dateFin, 23, 59, 59))
                : locationsRepository.findByClient_Id(clientId);

        String clientNom = locations.stream()
                .filter(l -> l.getClient() != null)
                .findFirst()
                .map(l -> l.getClient().getDescriptionEntreprise())
                .orElse(null);

        int enCours   = (int) locations.stream().filter(l -> "EN_COURS".equals(l.getStatut())).count();
        int terminees = (int) locations.stream().filter(l -> "TERMINEE".equals(l.getStatut())).count();
        double totalHT = locations.stream()
                .mapToDouble(l -> l.getMissions().stream().mapToDouble(Missions::getSousTotal).sum())
                .sum();

        return new RapportLocationClientResponse(
                clientId, clientNom, locations.size(), enCours, terminees, totalHT,
                locations.stream().map(this::toLocationClientDetail).toList()
        );
    }

    // M1 — Missions par conducteur
    public RapportMissionsConducteurResponse rapportMissionsConducteur(Long conducteurId, LocalDate dateDebut, LocalDate dateFin) {
        Conducteur conducteur = conducteurRepository.findById(conducteurId)
                .orElseThrow(() -> new IllegalArgumentException("Conducteur introuvable avec id: " + conducteurId));

        List<Missions> missions = (dateDebut != null && dateFin != null)
                ? missionsRepository.findByLocation_Conducteur_IdConducteurAndDateDebutMissionBetween(conducteurId, dateDebut, dateFin)
                : missionsRepository.findByLocation_Conducteur_IdConducteur(conducteurId);

        double totalHeures = missions.stream()
                .filter(m -> m.getNbHeures() != null)
                .mapToDouble(Missions::getNbHeures)
                .sum();

        long totalKm = missions.stream()
                .filter(m -> m.getKmDbtMission() != null && m.getKmFinMission() != null)
                .mapToLong(m -> m.getKmFinMission() - m.getKmDbtMission())
                .sum();

        String nomComplet = conducteur.getNomConducteur() + " " + conducteur.getPrenomsConducteur();

        return new RapportMissionsConducteurResponse(
                conducteurId, nomComplet, missions.size(), totalHeures, totalKm,
                missions.stream().map(this::toMissionConducteurDetail).toList()
        );
    }

    // F2 — Suivi des impayés
    public RapportImpayesResponse rapportImpayes(Long clientId) {
        List<Facture> factures = (clientId != null)
                ? factureRepository.findByEtatPaiementNotAndLocation_Client_Id("PAYEE", clientId)
                : factureRepository.findByEtatPaiementNot("PAYEE");

        double totalHT    = factures.stream().mapToDouble(Facture::getMontantHT).sum();
        double totalTTC   = factures.stream().mapToDouble(Facture::getMontantTTC).sum();
        double totalVerse = factures.stream()
                .flatMap(f -> f.getReglements().stream())
                .filter(r -> r.getMontantVerse() != null)
                .mapToDouble(Reglement::getMontantVerse)
                .sum();

        return new RapportImpayesResponse(
                factures.size(), totalHT, totalTTC, totalVerse, totalTTC - totalVerse,
                factures.stream().map(this::toFactureImpayeeDetail).toList()
        );
    }

    // --- mappers ---

    private RapportLocationPeriodeResponse.LocationDetail toLocationPeriodeDetail(Locations l) {
        Client client      = l.getClient();
        Conducteur cond    = l.getConducteur();
        Engins engin       = l.getEngins();
        double montantHT   = l.getMissions().stream().mapToDouble(Missions::getSousTotal).sum();

        return new RapportLocationPeriodeResponse.LocationDetail(
                l.getId(), l.getCodeLocation(), l.getDateDbtLoc(), l.getDateFinLoc(),
                l.getStatut(), l.getEtatLocation(), l.getSiteLocation(),
                client != null ? client.getId() : null,
                client != null ? client.getDescriptionEntreprise() : null,
                cond != null ? cond.getIdConducteur() : null,
                cond != null ? cond.getNomConducteur() + " " + cond.getPrenomsConducteur() : null,
                engin != null ? engin.getId() : null,
                engin != null ? engin.getModelEngin() : null,
                engin != null ? engin.getMarqueEngin() : null,
                l.getNbJoursLocation(), l.getNbHeureLocation(),
                l.getCoutHoraireLocation(), l.getCoutJournalierLocation(),
                montantHT
        );
    }

    private RapportLocationClientResponse.LocationDetail toLocationClientDetail(Locations l) {
        Conducteur cond  = l.getConducteur();
        Engins engin     = l.getEngins();
        double montantHT = l.getMissions().stream().mapToDouble(Missions::getSousTotal).sum();

        return new RapportLocationClientResponse.LocationDetail(
                l.getId(), l.getCodeLocation(), l.getDateDbtLoc(), l.getDateFinLoc(),
                l.getStatut(), l.getEtatLocation(), l.getSiteLocation(),
                engin != null ? engin.getModelEngin() : null,
                engin != null ? engin.getMarqueEngin() : null,
                cond != null ? cond.getNomConducteur() + " " + cond.getPrenomsConducteur() : null,
                l.getNbJoursLocation(), l.getNbHeureLocation(),
                l.getCoutHoraireLocation(), l.getCoutJournalierLocation(),
                montantHT
        );
    }

    private RapportMissionsConducteurResponse.MissionDetail toMissionConducteurDetail(Missions m) {
        Locations loc  = m.getLocation();
        Client client  = (loc != null) ? loc.getClient() : null;

        return new RapportMissionsConducteurResponse.MissionDetail(
                m.getId(), m.getCodeMission(),
                m.getDateDebutMission(), m.getDateFinMission(),
                m.getHeureDebutMission(), m.getHeureFinMission(),
                m.getLieuMission(), m.getNbHeures(),
                m.getTarifHoraireApplique(), m.getSousTotal(),
                m.getKmDbtMission(), m.getKmFinMission(),
                m.getCarbtDbtMission(), m.getCarbtFinMission(),
                m.getMateriauxMission(), m.getQteMateriauxMission(),
                m.getStatutMission(),
                loc != null ? loc.getCodeLocation() : null,
                client != null ? client.getDescriptionEntreprise() : null
        );
    }

    private RapportImpayesResponse.FactureImpayeeDetail toFactureImpayeeDetail(Facture f) {
        Locations loc  = f.getLocation();
        Client client  = (loc != null) ? loc.getClient() : null;
        double verse   = f.getReglements().stream()
                .filter(r -> r.getMontantVerse() != null)
                .mapToDouble(Reglement::getMontantVerse)
                .sum();
        double montantTTC = f.getMontantTTC();
        long jours = f.getDateEmission() != null
                ? ChronoUnit.DAYS.between(f.getDateEmission(), LocalDate.now()) : 0L;

        return new RapportImpayesResponse.FactureImpayeeDetail(
                f.getId(), f.getDateEmission(), f.getEtatPaiement(),
                client != null ? client.getId() : null,
                client != null ? client.getDescriptionEntreprise() : null,
                loc != null ? loc.getCodeLocation() : null,
                loc != null ? loc.getSiteLocation() : null,
                f.getMontantHT(), montantTTC,
                verse, montantTTC - verse,
                jours
        );
    }

    private Date toDate(LocalDate localDate, int hour, int minute, int second) {
        return Date.from(localDate.atTime(hour, minute, second)
                .atZone(ZoneId.systemDefault()).toInstant());
    }
}
