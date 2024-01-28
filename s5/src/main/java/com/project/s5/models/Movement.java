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
@Entity(name = "movement")
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(generator = "movement_seq")
    @SequenceGenerator(name = "movement_seq", sequenceName = "_movement_seq", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_materiaux", unique = false)
    Materiaux materiaux;

    @Column(name = "quantity", nullable = false)
    Double quantity;

    @Column(name = "status_movement", nullable = false)
    String status_movement;

    @Column(name = "date", nullable = false)
    Timestamp date;
}
