package com.project.s5.models;

import java.sql.Timestamp;

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
@Entity(name = "outils")
@Table(name = "outils")
public class Outils {

    @Id
    @GeneratedValue(generator = "outils_seq")
    @SequenceGenerator(name = "outils_seq", sequenceName = "_outils_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false, unique = false)
    String name;

    @Column(name = "salaire", nullable = false, unique = false)
    Double salaire;
}
