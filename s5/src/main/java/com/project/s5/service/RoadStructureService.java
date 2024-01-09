package com.project.s5.service;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.project.s5.Response.RoadPriced;
import com.project.s5.models.Materiaux;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.repository.MateriauxRepo;
import com.project.s5.repository.PriceMateriauxRepo;
import com.project.s5.repository.RoadMateriauxRepo;
import com.project.s5.repository.RoadTypeQualityRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoadStructureService {

    private final RoadMateriauxRepo roadMateriauxRepo;
    private final MateriauxRepo materiauxRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final PriceMateriauxRepo priceMateriauxRepo;

    public List<RoadMateriaux> getRoadByMateriaux(Long idMateriaux) {
        Materiaux materiaux = materiauxRepo.findById(idMateriaux).get();
        List<RoadMateriaux> roadMateriaux = roadMateriauxRepo.findByMateriaux(materiaux);
        return roadMateriaux;
    }

    public List<RoadMateriaux> getByRoadQuality(RoadTypeQuality roadTypeQuality) {
        List<RoadMateriaux> roadMateriauxs = roadMateriauxRepo.findByRoadTypeQuality(roadTypeQuality);
        return roadMateriauxs;
    }

    public List<RoadPriced> getRoadBetween(Double min, Double max) {

        List<RoadPriced> roadPriceds = getRoadPriced();
        List<RoadPriced> filtredRoadPRiced = new Vector<RoadPriced>();
        for (RoadPriced roadPriced : roadPriceds) {
            if (min <= roadPriced.getPrice() && roadPriced.getPrice() <= max)
                filtredRoadPRiced.add(roadPriced);
        }
        return filtredRoadPRiced;

    }

    public List<RoadPriced> getRoadPriced() {
        List<RoadPriced> roadPriceds = new Vector<RoadPriced>();
        List<RoadTypeQuality> roads = roadTypeQualityRepo.findAll();

        for (RoadTypeQuality roadTypeQuality : roads) {
            List<RoadMateriaux> roadMateriauxs = getByRoadQuality(roadTypeQuality);
            Double priceTotal = 0.0;
            for (RoadMateriaux roadMaterial : roadMateriauxs) {
                PriceMateriaux priceMateriaux = priceMateriauxRepo.findFirstByMateriaux(roadMaterial.getMateriaux())
                        .get();
                priceTotal += roadMaterial.getQuantity() * priceMateriaux.getPrice();// Unit 1 auto
            }
            RoadPriced roadPriced = RoadPriced.builder().roadTypeQuality(roadTypeQuality).price(priceTotal).build();
            roadPriceds.add(roadPriced);
        }
        return roadPriceds;
    }

}
