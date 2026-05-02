package com.gts.backgts.services;

import com.gts.backgts.dto.*;
import com.gts.backgts.entites.*;
import com.gts.backgts.repository.ActiviteClientRepository;
import com.gts.backgts.repository.ClientRepository;
import com.gts.backgts.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ActiviteClientRepository activiteClientRepository;
    private final LocationsRepository locationsRepository;
    @Value("${app.base-url:http://localhost:8081}")
    private String baseUrl;

    public ClientResponse saveClient(ClientRequest clientRequest) throws IOException {
        Client client = new Client();

        ActiviteClient activiteClient = activiteClientRepository.findByCodeActClt(clientRequest.secteur_activite());

        client.setCodeClient(clientRequest.codeClient());
        client.setDesignationEntreprise(clientRequest.nameClient());
        client.setDescriptionEntreprise(clientRequest.raison_sociale());
        client.setPaysEntreprise(clientRequest.pays());
        client.setVilleEntreprise(clientRequest.ville());
        client.setEmail(clientRequest.email());
        client.setTelEntreprise(clientRequest.phoneNumber());
        client.setActiviteClient(activiteClient);
        client.setPersonneRessource(clientRequest.personneRessource());
        client.setTelPersonneRessource(clientRequest.telPersonneRessource());
        client.setAdresseEntreprise(clientRequest.adresseEntreprise());
        client.setRccmClient(clientRequest.rccmClient());
        client.setNumeroIFUEntreprise(clientRequest.numeroIFUEntreprise());
        client.setRegimeFiscalEntreprise(clientRequest.regimeFiscalEntreprise());
        client.setNumeroCompteBancaire(clientRequest.numeroCompteBancaire());
        client.setDateCreation(LocalDate.now());


        saveLogoEntreprise(client, clientRequest.urlMedia());

        Client saved = clientRepository.save(client);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + id));
        return toResponse(client);
    }

    public ClientResponse updateClient(Long id, ClientRequest request) throws IOException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + id));

        ActiviteClient activiteClient = activiteClientRepository.findByCodeActClt(request.secteur_activite());

        client.setCodeClient(request.codeClient());
        client.setDesignationEntreprise(request.nameClient());
        client.setDescriptionEntreprise(request.raison_sociale());
        client.setPaysEntreprise(request.pays());
        client.setVilleEntreprise(request.ville());
        client.setEmail(request.email());
        client.setTelEntreprise(request.phoneNumber());
        client.setRccmClient(request.rccmClient());
        client.setActiviteClient(activiteClient);
        client.setPersonneRessource(request.personneRessource());
        client.setTelPersonneRessource(request.phoneNumber());


        client.setAdresseEntreprise(request.adresseEntreprise());

        client.setNumeroIFUEntreprise(request.numeroIFUEntreprise());
        client.setRegimeFiscalEntreprise(request.regimeFiscalEntreprise());
        client.setNumeroCompteBancaire(request.numeroCompteBancaire());

        client.setDateModification(LocalDate.now());

        saveLogoEntreprise(client, request.urlMedia());




        return toResponse(clientRepository.save(client));
    }

    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client introuvable avec id: " + id);
        }
        clientRepository.deleteById(id);
    }

    private void saveLogoEntreprise(Client client, MultipartFile urlMedia) throws IOException {
        if (urlMedia != null && !urlMedia.isEmpty()) {
            Path uploadDir = Path.of("uploads", "clients");
            Files.createDirectories(uploadDir);

            String originalFilename = urlMedia.getOriginalFilename();
            String fileName = java.util.UUID.randomUUID() + "_" +
                    (originalFilename != null ? originalFilename : "logo-client");
            Path destination = uploadDir.resolve(fileName);

            Files.copy(urlMedia.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            client.setCheminLogoEntreprise(destination.toString());
        }
    }
    @Transactional(readOnly = true)
    public ClientDetailsResponse getClientWithLocations(Long id, Pageable pageable) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + id));

        List<LocationsResponse> locations = locationsRepository.findByClient_Id(id,pageable)
                .stream()
                .map(this::toLocationResponse)
                .toList();

        ActiviteClientResponse activiteClientResponse = null;

        if (client.getActiviteClient() != null) {
            activiteClientResponse = new ActiviteClientResponse(
                    client.getActiviteClient().getId(),
                    client.getActiviteClient().getCodeActClt(),
                    client.getActiviteClient().getDescription(),
                    client.getActiviteClient().getDateCreation(),
                    client.getActiviteClient().getDateModification()
            );
        }

        return new ClientDetailsResponse(
                client.getId(),
                client.getCodeClient(),
                client.getDesignationEntreprise(),
                client.getDescriptionEntreprise(),
                client.getPaysEntreprise(),
                client.getVilleEntreprise(),
                client.getEmail(),
                client.getTelEntreprise(),
                client.getPersonneRessource(),
                client.getTelPersonneRessource(),
                client.getAdresseEntreprise(),
                client.getRccmClient(),
                client.getNumeroIFUEntreprise(),
                client.getRegimeFiscalEntreprise(),
                client.getNumeroCompteBancaire(),
                client.getDateCreation(),
                client.getDateModification(),
                client.getCheminLogoEntreprise(),
                activiteClientResponse,
                locations
        );
    }

    private LocationsResponse toLocationResponse(Locations location) {

        return new LocationsResponse(
                location.getId(),
                location.getDateDebut(),
                location.getStatut(),
                location.getCodeLocation(),
                location.getNbJoursLocation(),
                location.getSiteLocation(),
                location.getDateDbtLoc(),
                location.getDateFinLoc(),
                location.getCoutHoraireLocation(),
                location.getCoutJournalierLocation(),
                location.getNbHeureLocation(),
                location.getEtatLocation(),
                location.getClient() != null ? location.getClient().getId() : null,
                location.getClient() != null ? location.getClient().getDescriptionEntreprise() : null,
                location.getConducteur() != null ? location.getConducteur().getIdConducteur() : null,
                location.getConducteur() != null ? location.getConducteur().getNomConducteur() : null,
                location.getConducteur() != null ? location.getConducteur().getPrenomsConducteur() : null,
                location.getEngins() != null ? location.getEngins().getId() : null,
                location.getEngins() != null ? location.getEngins().getCodeEngin() : null,
                location.getEngins() != null ? location.getEngins().getModelEngin() : null,
                location.getEngins()!=null?location.getEngins().getMarqueEngin():null,
                location.getDateCreation(),
                location.getDateModification()
        );
    }


    private  ClientResponse toResponse(Client client) {
        ActiviteClientResponse activiteClientResponse = null;

        if (client.getActiviteClient() != null) {
            activiteClientResponse = new ActiviteClientResponse(
                    client.getActiviteClient().getId(),
                    client.getActiviteClient().getCodeActClt(),
                    client.getActiviteClient().getDescription(),
                    client.getActiviteClient().getDateCreation(),
                    client.getActiviteClient().getDateModification()
            );
        }
        return new ClientResponse(
                client.getId(),
                client.getCodeClient(),
                client.getDesignationEntreprise(),
                client.getDescriptionEntreprise(),
                client.getPaysEntreprise(),
                client.getVilleEntreprise(),
                client.getEmail(),
                client.getTelEntreprise(),
                client.getPersonneRessource(),
                client.getTelPersonneRessource(),
                client.getAdresseEntreprise(),
                client.getRccmClient(),
                client.getNumeroIFUEntreprise(),
                client.getRegimeFiscalEntreprise(),
                client.getNumeroCompteBancaire(),
                client.getDateCreation(),
                client.getDateModification(),
                client.getCheminLogoEntreprise(),
                activiteClientResponse
        );
    }

    @Transactional(readOnly = true)
    public LogoClientResponse getLogoClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec id: " + id));

        return new LogoClientResponse(
                client.getId(),
                toPublicUrl(client.getCheminLogoEntreprise())

        );
    }

    private String toPublicUrl(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            return null;
        }

        String normalizedPath = storedPath.replace("\\", "/");
        if (normalizedPath.startsWith("uploads/")) {
            return baseUrl + "/" + normalizedPath;
        }

        return baseUrl + "/uploads/conducteurs/" + normalizedPath;
    }
}

