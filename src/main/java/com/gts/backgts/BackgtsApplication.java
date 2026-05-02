package com.gts.backgts;

import com.gts.backgts.entites.ActiviteClient;
import com.gts.backgts.entites.Client;
import com.gts.backgts.repository.ActiviteClientRepository;
import com.gts.backgts.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication(excludeName = {
        "org.springdoc.core.configuration.SpringDocHateoasConfiguration",
        "org.springdoc.core.configuration.SpringDocDataRestConfiguration"
})
public class BackgtsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgtsApplication.class, args);
        System.out.println("Hello");

    }
//    @Bean
//    CommandLineRunner commandLineRunner(ClientRepository clientRepository,
//                                        ActiviteClientRepository activiteClientRepository) {
//        return args -> {
//            ActiviteClient activite = activiteClientRepository.findByCodeActClt("A02");
//            if (activite == null) {
//                activite = activiteClientRepository.save(
//                        ActiviteClient.builder()
//                                .codeActClt("A09")
//                                .description("BTP")
//                                .dateCreation(LocalDate.now())
//                                .dateModification(LocalDate.now())
//                                .build()
//                );
//            }
//
//            clientRepository.save(Client.builder()
//                    .codeClient("007")
//                    .activiteClient(activite)
//                    .designationEntreprise("AMP")
//                    .descriptionEntreprise("SOGEA MINING ")
//                    .communeEntreprise("Balkui")
//                    .villeEntreprise("OUAGADOUGOU")
//                    .telEntreprise("0101010101")
//                    .personneRessource("OUEDRAOGO BENOIT")
//                    .telPersonneRessource("12345556")
//                    .dateCreation(LocalDate.now())
//                    .dateModification(LocalDate.now())
//                    .build());
//
//            System.out.println("Fin enregistrement Client");
//        };
//    }
}
