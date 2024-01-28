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
@Entity(name = "main_ouevre")
@Table(name = "main_ouevre")
public class MainOuevre {

    @Id
    @GeneratedValue(generator = "main_ouevre")
    @SequenceGenerator(name = "main_ouevre", sequenceName = "_main_ouevre", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_road_type_quality", unique = false)
    RoadTypeQuality roadTypeQuality;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_outils", unique = false)
    Outils outils;

    @Column(name = "time_to_work", nullable = false, unique = false)
    Double time_to_work;
}
