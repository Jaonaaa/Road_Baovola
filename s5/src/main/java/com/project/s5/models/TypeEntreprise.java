package com.project.s5.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Entity(name = "type_entreprise")
@Table(name = "type_entreprise")
public class TypeEntreprise {

    @Id
    @GeneratedValue(generator = "type_entreprise")
    @SequenceGenerator(name = "type_entreprise", sequenceName = "_type_entreprise", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false, unique = false)
    String name;
}
