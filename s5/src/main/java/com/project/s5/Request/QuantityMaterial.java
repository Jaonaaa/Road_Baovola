package com.project.s5.Request;

import com.project.s5.models.Materiaux;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuantityMaterial {

    Materiaux materiaux;
    Double quantity;
}
