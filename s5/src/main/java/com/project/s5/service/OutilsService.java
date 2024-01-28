package com.project.s5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.s5.models.MainOuevre;
import com.project.s5.models.Outils;
import com.project.s5.models.OutilsBySize;
import com.project.s5.models.PriceVente;
import com.project.s5.models.Quality;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.TimeByQuality;
import com.project.s5.repository.MainOuevreRepo;
import com.project.s5.repository.OutilsBySizeRepo;
import com.project.s5.repository.OutilsRepository;
import com.project.s5.repository.PriceVenteRepo;
import com.project.s5.repository.QualityRepo;
import com.project.s5.repository.RoadTypeQualityRepo;
import com.project.s5.repository.SizeRoadRepo;
import com.project.s5.repository.TimeByQualityRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutilsService {

    private final OutilsRepository outilsRepository;
    private final PriceVenteRepo priceVenteRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final OutilsBySizeRepo outilsBySizeRepo;
    private final TimeByQualityRepo timeByQualityRepo;
    private final MainOuevreRepo mainOuevreRepo;
    private final SizeRoadRepo sizeRoadRepo;
    private final QualityRepo qualityRepo;

    // GET
    public List<Outils> getAllOutils() {
        return outilsRepository.findAll();
    }

    public List<PriceVente> getAllPriceVente() {
        return priceVenteRepo.findAll();
    }

    public List<OutilsBySize> getAllOutilsBySize() {
        return outilsBySizeRepo.findAll();
    }

    public List<TimeByQuality> getAllTimeByQualities() {
        return timeByQualityRepo.findAll();
    }

    public List<MainOuevre> getMainOeuvre(Long idRoadTypeQuality) {
        RoadTypeQuality roadTypeQuality = roadTypeQualityRepo.findById(idRoadTypeQuality).get();
        return mainOuevreRepo.findAllByRoadTypeQuality(roadTypeQuality);
    }

    // POST

    public void addOutils(Outils outils) {
        outilsRepository.save(outils);
    }

    public void addPriceVente(PriceVente priceVente) {
        RoadTypeQuality roadTypeQuality = priceVente.getRoadTypeQuality();
        roadTypeQuality = roadTypeQualityRepo.findById(roadTypeQuality.getId()).get();
        Optional<RoadTypeQuality> roadTypeQualityExist = roadTypeQualityRepo.findFirstByTypeAndQualityAndSize(
                roadTypeQuality.getType(), roadTypeQuality.getQuality(), roadTypeQuality.getSize());

        if (!roadTypeQualityExist.isPresent())
            throw new RuntimeException("Road Type Quality and size n'existe par encore");

        Optional<PriceVente> priceVenteExisting = priceVenteRepo.findFirstByRoadTypeQuality(roadTypeQualityExist.get());

        if (priceVenteExisting.isPresent()) {
            PriceVente priceVenteOld = priceVenteExisting.get();
            priceVenteOld.setPrice(priceVente.getPrice());
            priceVenteRepo.save(priceVenteOld);

        } else {
            priceVenteRepo.save(priceVente);
        }
    }

    public void addOutilsBySize(OutilsBySize outilsBySize) {
        if (outilsBySize.getQuantity() <= 0)
            throw new RuntimeException("Quantité outils by size incorrect");

        Optional<OutilsBySize> ot = outilsBySizeRepo.findFirstBySizeRoad(
                sizeRoadRepo.findById(outilsBySize.getSizeRoad().getId()).get());
        if (ot.isPresent()) {
            OutilsBySize ott = ot.get();
            ott.setQuantity(outilsBySize.getQuantity());
            outilsBySizeRepo.save(ott);
        } else
            outilsBySizeRepo.save(outilsBySize);
    }

    public void addTimeByQuality(TimeByQuality timeByQuality) {
        if (timeByQuality.getTime_to_work() <= 0)
            throw new RuntimeException("Time to work dans  Time by quality  incorrect");

        Optional<TimeByQuality> ot = timeByQualityRepo.findFirstByQuality(
                qualityRepo.findById(timeByQuality.getQuality().getId()).get());
        if (ot.isPresent()) {
            TimeByQuality ott = ot.get();
            ott.setTime_to_work(timeByQuality.getTime_to_work());
            timeByQualityRepo.save(ott);
        } else

            timeByQualityRepo.save(timeByQuality);
    }

    public void addMainOuevre(MainOuevre mainOuevre) {
        if (mainOuevre.getTime_to_work() <= 0)
            throw new RuntimeException("Main d'ouvre quantity incorrect");
        checkTimeNotUpper(mainOuevre.getRoadTypeQuality(), mainOuevre.getTime_to_work());
        checkMainOuvreNumberIsOk(mainOuevre.getRoadTypeQuality());
        // exception temps de travail tsy miotra ataovy
        mainOuevreRepo.save(mainOuevre);
    }

    public void checkTimeNotUpper(RoadTypeQuality rtq, Double time_to_work) {
        RoadTypeQuality roadTypeQuality = roadTypeQualityRepo.findById(rtq.getId()).get();
        Quality quality = roadTypeQuality.getQuality();
        Optional<TimeByQuality> time = timeByQualityRepo.findFirstByQuality(quality);
        if (time.isPresent()) {
            if (time.get().getTime_to_work() < time_to_work)
                throw new RuntimeException(
                        "Time required for the quality " + quality.getName() + "(" + time.get().getTime_to_work()
                                + ") is less than the given one : "
                                + time_to_work);
        } else
            throw new RuntimeException("Time required for the quality " + quality.getName() + " is not already set");

    }

    public void checkMainOuvreNumberIsOk(RoadTypeQuality rtq) {
        RoadTypeQuality roadTypeQuality = roadTypeQualityRepo.findById(rtq.getId()).get();
        Optional<OutilsBySize> outilsBySize = outilsBySizeRepo.findFirstBySizeRoad(roadTypeQuality.getSize());
        if (outilsBySize.isPresent()) {
            Double necessary = outilsBySize.get().getQuantity();
            List<MainOuevre> mains = mainOuevreRepo.findAllByRoadTypeQuality(roadTypeQuality);
            if (mains.size() + 1 > necessary)
                throw new RuntimeException(
                        "Le nombre d'employé travaillant ne peut pas dépasser le nombre " + necessary);
        } else
            throw new RuntimeException(
                    "Le nombre d'employé travaillant sur cette route par rapport au taille n'a pa encore été fournis");
    }

}
