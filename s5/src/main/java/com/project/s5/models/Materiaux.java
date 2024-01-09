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
@Entity(name = "materiaux")
@Table(name = "materiaux")
public class Materiaux {

    @Id
    @GeneratedValue(generator = "materiaux")
    @SequenceGenerator(name = "materiaux", sequenceName = "_materiaux", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

}
