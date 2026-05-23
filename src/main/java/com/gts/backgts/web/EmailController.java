package com.gts.backgts.web;

import com.gts.backgts.dto.EmailRequest;
import com.gts.backgts.dto.EmailResponse;
import com.gts.backgts.dto.RegistrationRequest;
import com.gts.backgts.entites.Users;
import com.gts.backgts.repository.UserRepository;
import com.gts.backgts.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/api/gts/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @PostMapping("/confirmation/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmailResponse> envoyerConfirmation(
            @PathVariable Long userId,
            @RequestParam String lienConfirmation) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(emailService.envoyerConfirmationInscription(user, lienConfirmation));
    }

    @PostMapping("/reinitialisation/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmailResponse> envoyerReinitialisation(
            @PathVariable Long userId,
            @RequestParam String code) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(emailService.envoyerReinitialisationMotDePasse(user, code));
    }

    // forgot-password est déjà public (configuré dans SecurityConfig)
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        System.out.println("[debug]REQUEST GET DESTINATAIRE : " + request.get("email"));
        Users user = userRepository.findByEmailUsers(request.get("email"))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String code = String.format("%06d", new Random().nextInt(999999));
        user.setCodeVerification(code);
        userRepository.save(user);
        emailService.envoyerReinitialisationMotDePasse(user, code);
        return ResponseEntity.ok("un Email envoyé à l'adresse : " + request.get("email") + "");
    }

    @PostMapping("/notification/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmailResponse> envoyerNotification(
            @PathVariable Long userId,
            @RequestBody EmailRequest request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(emailService.envoyerNotification(
                user, request.getSujet(), request.getMessage()));
    }
}
