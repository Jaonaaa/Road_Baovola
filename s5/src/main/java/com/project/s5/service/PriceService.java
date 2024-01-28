package com.project.s5.service;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.project.s5.Response.RoadPriced;
import com.project.s5.models.MainOuevre;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.PriceVente;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.repository.MainOuevreRepo;
import com.project.s5.repository.OutilsBySizeRepo;
import com.project.s5.repository.PriceVenteRepo;
import com.project.s5.repository.RoadTypeQualityRepo;
import com.project.s5.repository.TimeByQualityRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceVenteRepo priceVenteRepo;
    private final MainOuevreRepo mainOuevreRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final TradeService tradeService;

    public List<RoadPriced> getRoadBetweenByBenefice(Double min, Double max) {
        List<RoadTypeQuality> roads = roadTypeQualityRepo.findAll();
        List<RoadPriced> roadsBetween = new Vector<RoadPriced>();

        for (RoadTypeQuality road : roads) {
            Double[] price = getBenefice(road);
            System.out.println(road.getId());
            Double benefice = price[4];

            if (min <= benefice && benefice <= max)
                roadsBetween.add(RoadPriced.builder().roadTypeQuality(road).price(benefice)
                        .prix_matiere_premiere(price[0]).prix_employer(price[1]).prix_de_revient(price[2])
                        .prix_de_vente(price[3])
                        .benefice(benefice)
                        .build());
        }

        return roadsBetween;
    }

    public Double[] getBenefice(RoadTypeQuality roadTypeQuality) {
        Double[] prixDeRevient = getPrixDeRevient(roadTypeQuality);
        Optional<PriceVente> priceVente = priceVenteRepo.findFirstByRoadTypeQuality(roadTypeQuality);
        if (!priceVente.isPresent())
            throw new RuntimeException(
                    "Prix de vente de la route " + roadTypeQuality.getId() + " n'est pas encore présent");
        Double prixDeVente = priceVente.get().getPrice();

        Double[] prix = new Double[5];
        prix[0] = prixDeRevient[0];// prix des matières premieres
        prix[1] = prixDeRevient[1];// prix du salaire des employer
        prix[2] = prixDeRevient[2];// prix de revient (prix des matières premieres + prix du salaire des employer )
        prix[3] = prixDeVente; // prix de vente
        prix[4] = prixDeVente - prixDeRevient[2]; // benefice
        return prix;
    }

    public Double[] getPrixDeRevient(RoadTypeQuality roadTypeQuality) {

        Double priceMatierePremiere = tradeService.getPriceProductOnSellingWithoutUpdate(roadTypeQuality, 1.0);
        Double priceEmployer = getPriceEmployer(roadTypeQuality);
        Double[] prix = new Double[3];
        prix[0] = priceMatierePremiere;
        prix[1] = priceEmployer;
        prix[2] = priceMatierePremiere + priceEmployer;
        return prix;
    }

    public Double getPriceEmployer(RoadTypeQuality roadTypeQuality) {

        List<MainOuevre> mainOuevre = mainOuevreRepo.findAllByRoadTypeQuality(roadTypeQuality);
        Double total = 0.0;
        for (MainOuevre main_oeuvre : mainOuevre) {
            total += main_oeuvre.getTime_to_work() * main_oeuvre.getOutils().getSalaire();
        }
        return total;
    }

}
