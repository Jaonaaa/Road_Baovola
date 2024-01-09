package com.project.s5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.s5.Request.QuantityMaterial;
import com.project.s5.Request.RoadMaterialRequest;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.repository.PriceMateriauxRepo;
import com.project.s5.repository.RoadMateriauxRepo;
import com.project.s5.repository.RoadTypeQualityRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MateriauxService {

    private final PriceMateriauxRepo priceMateriauxRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final RoadMateriauxRepo roadMateriauxRepo;

    public void addMaterialPrice(PriceMateriaux priceMateriaux) {
        if (priceMateriaux.getPrice() <= 0)
            throw new RuntimeException("Price can't be negative or equal to 0");
        if (priceMateriaux.getUnit() <= 0)
            throw new RuntimeException("Unit can't be negative or equal to 0");
        priceMateriauxRepo.save(priceMateriaux);
    }

    @Transactional
    public void addMaterialNecessaryForRoad(RoadMaterialRequest roadMaterial) {
        RoadTypeQuality roadTypeQuality = RoadTypeQuality.builder().quality(roadMaterial.getQuality())
                .size(roadMaterial.getSize()).type(roadMaterial.getType()).build();
        roadTypeQuality = roadTypeQualityRepo.save(roadTypeQuality);

        addRoadMateriaux(roadMaterial.getMaterials(), roadTypeQuality);
    }

    public void addRoadMateriaux(List<QuantityMaterial> quantityMaterials, RoadTypeQuality roadTypeQuality) {

        for (QuantityMaterial qtMaterial : quantityMaterials) {
            RoadMateriaux roadMateriaux = RoadMateriaux.builder().materiaux(qtMaterial.getMateriaux())
                    .quantity(qtMaterial.getQuantity()).roadTypeQuality(roadTypeQuality).build();
            roadMateriauxRepo.save(roadMateriaux);
        }
    }

}
