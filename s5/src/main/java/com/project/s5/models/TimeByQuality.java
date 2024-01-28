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
@Entity(name = "time_by_quality")
@Table(name = "time_by_quality")
public class TimeByQuality {

    @Id
    @GeneratedValue(generator = "time_by_quality")
    @SequenceGenerator(name = "time_by_quality", sequenceName = "_time_by_quality", allocationSize = 1)
    Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_quality", unique = false)
    Quality quality;

    @Column(name = "time_to_work", nullable = false, unique = false)
    Double time_to_work;
}
