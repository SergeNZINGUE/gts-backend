package com.gts.backgts.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "clients")
@Table(name = "ACTIVITE_CLIENT")

@Builder
public class ActiviteClient implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeActClt;
    private String description;
    LocalDate dateCreation;
    LocalDate dateModification;

    @OneToMany(mappedBy = "activiteClient")
    @JsonIgnoreProperties("activiteClient")
    private Collection<Client> clients= new ArrayList<>();
}
