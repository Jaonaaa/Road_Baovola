package com.project.s5.Request;

import com.project.s5.models.Entreprise;
import com.project.s5.models.RoadTypeQuality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellProduct {

    RoadTypeQuality roadTypeQuality;
    Double quantity;
    Entreprise entreprise;

}
