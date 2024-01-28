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
@Entity(name = "vente")
@Table(name = "vente")
public class Vente {

    @Id
    @GeneratedValue(generator = "vente")
    @SequenceGenerator(name = "vente", sequenceName = "_vente", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_road_type_quality", unique = false)
    RoadTypeQuality roadTypeQuality;

    @Column(name = "quantity", nullable = false)
    Double quantity;

    @Column(name = "price", nullable = false)
    Double price;

    @Column(name = "date", nullable = false)
    Timestamp date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_entreprise", unique = false)
    Entreprise entreprise;

}
