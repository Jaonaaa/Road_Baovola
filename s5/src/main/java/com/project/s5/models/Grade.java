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
@Entity(name = "grade")
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(generator = "grade")
    @SequenceGenerator(name = "grade", sequenceName = "_grade", allocationSize = 1)
    Long id;
    @Column(name = "name", nullable = false, unique = false)
    String name;
    @Column(name = "upgrade_salaire", nullable = false, unique = false)
    Double upgrade_salaire;
    @Column(name = "upgrade_after", nullable = false, unique = false)
    Double upgrade_after;
}
