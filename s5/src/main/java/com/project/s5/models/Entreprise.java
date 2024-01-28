package com.project.s5.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "entreprise")
@Table(name = "entreprise")
public class Entreprise {

    @Id
    @GeneratedValue(generator = "entreprise")
    @SequenceGenerator(name = "entreprise", sequenceName = "_entreprise", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false, unique = false)
    String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_type_entreprise", unique = false)
    TypeEntreprise type_entreprise;

}
