package com.project.s5.Response;

import com.project.s5.models.RoadTypeQuality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadPriced {

    RoadTypeQuality roadTypeQuality;
    Double price;
    ///
    Double benefice;
    Double prix_de_vente;
    Double prix_de_revient;
    Double prix_employer;
    Double prix_matiere_premiere;

}
