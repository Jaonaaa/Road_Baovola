package com.project.s5.Request;

import java.util.List;

import com.project.s5.models.Quality;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadMaterialRequest {

    Type type;
    Quality quality;
    SizeRoad size;
    List<QuantityMaterial> materials;
}
