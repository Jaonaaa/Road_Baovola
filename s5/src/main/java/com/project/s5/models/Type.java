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
@Entity(name = "type")
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(generator = "type_seq")
    @SequenceGenerator(name = "type_seq", sequenceName = "_type_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    String name;
}
