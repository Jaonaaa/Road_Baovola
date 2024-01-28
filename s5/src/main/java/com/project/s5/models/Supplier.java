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
@Entity(name = "supplier")
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(generator = "supplier_seq")
    @SequenceGenerator(name = "supplier_seq", sequenceName = "_supplier_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false)
    String name;
}
