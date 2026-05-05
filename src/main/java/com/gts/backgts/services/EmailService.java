package com.gts.backgts.services;

import com.gts.backgts.dto.EmailResponse;
import com.gts.backgts.entites.Users;
import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String mailFrom;

    @Value("${app.mail.from-name}")
    private String mailFromName;

    // Email de confirmation d'inscription
    public EmailResponse envoyerConfirmationInscription(Users user, String lienConfirmation) {
        try {
            Context context = new Context();
            context.setVariable("nom", user.getNomUsers());
            context.setVariable("prenoms", user.getPrenomsUsers());
            context.setVariable("lienConfirmation", lienConfirmation);

            String contenu = templateEngine.process("emails/confirmation-inscription", context);

            envoyerEmail(user.getEmailUsers(), "Confirmez votre compte GTS", contenu);

            return EmailResponse.builder()
                    .succes(true)
                    .message("Email de confirmation envoyé à " + user.getEmailUsers())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Erreur envoi email confirmation : {}", e.getMessage());
            return EmailResponse.builder()
                    .succes(false)
                    .message("Échec de l'envoi : " + e.getMessage())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        }
    }

    // Email de réinitialisation de mot de passe
    public EmailResponse envoyerReinitialisationMotDePasse(Users user, String code) {
        try {
            Context context = new Context();
            context.setVariable("nom", user.getNomUsers());
            context.setVariable("prenoms", user.getPrenomsUsers());
            context.setVariable("code", code);

            String contenu = templateEngine.process("emails/reinitialisation-mot-de-passe", context);

            envoyerEmail(user.getEmailUsers(), "Réinitialisation de votre mot de passe GTS", contenu);

            return EmailResponse.builder()
                    .succes(true)
                    .message("Email de réinitialisation envoyé à " + user.getEmailUsers())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Erreur envoi email réinitialisation : {}", e.getMessage());
            return EmailResponse.builder()
                    .succes(false)
                    .message("Échec de l'envoi : " + e.getMessage())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        }
    }

    // Email de notification générale
    public EmailResponse envoyerNotification(Users user, String sujet, String message) {
        try {
            Context context = new Context();
            context.setVariable("nom", user.getNomUsers());
            context.setVariable("prenoms", user.getPrenomsUsers());
            context.setVariable("sujet", sujet);
            context.setVariable("message", message);

            String contenu = templateEngine.process("emails/notification", context);

            envoyerEmail(user.getEmailUsers(), sujet, contenu);

            return EmailResponse.builder()
                    .succes(true)
                    .message("Notification envoyée à " + user.getEmailUsers())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Erreur envoi notification : {}", e.getMessage());
            return EmailResponse.builder()
                    .succes(false)
                    .message("Échec de l'envoi : " + e.getMessage())
                    .dateEnvoi(LocalDateTime.now())
                    .build();
        }
    }

    // Méthode commune d'envoi
    private void envoyerEmail(String destinataire, String sujet, String contenuHtml)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress(mailFrom, mailFromName));
        helper.setTo(destinataire);
        helper.setSubject(sujet);
        helper.setText(contenuHtml, true);

        mailSender.send(message);
        log.info("Email envoyé à : {}", destinataire);
    }
}