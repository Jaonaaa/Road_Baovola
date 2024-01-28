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
@Entity(name = "outils_by_size")
@Table(name = "outils_by_size")
public class OutilsBySize {

    @Id
    @GeneratedValue(generator = "outils_by_size")
    @SequenceGenerator(name = "outils_by_size", sequenceName = "_outils_by_size", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_size_road", unique = false)
    SizeRoad sizeRoad;

    @Column(name = "quantity", nullable = false, unique = false)
    Double quantity;
}
