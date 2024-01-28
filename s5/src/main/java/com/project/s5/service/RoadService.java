package com.project.s5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.s5.Request.RoadStruct;
import com.project.s5.models.Materiaux;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.SizeRoad;
import com.project.s5.models.TimeRoad;
import com.project.s5.models.Type;
import com.project.s5.models.TypeMateriaux;
import com.project.s5.repository.MateriauxRepo;
import com.project.s5.repository.RoadMateriauxRepo;
import com.project.s5.repository.RoadTypeQualityRepo;
import com.project.s5.repository.SizeRoadRepo;
import com.project.s5.repository.TimeRoadRepo;
import com.project.s5.repository.TypeMateriauxRepo;
import com.project.s5.repository.TypeRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoadService {

    private final TypeRepo typeRepo;
    private final MateriauxRepo materiauxRepo;
    private final TypeMateriauxRepo typeMateriauxRepo;
    private final TimeRoadRepo timeRoadRepo;
    private final SizeRoadRepo sizeRoadRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final RoadMateriauxRepo roadMateriauxRepo;

    // GET
    public List<TypeMateriaux> getAllTypeMaterial(Long idType) {
        Type type = typeRepo.findById(idType).get();
        return typeMateriauxRepo.findAllByType(type);
    }

    public List<SizeRoad> getAllSize() {
        return sizeRoadRepo.findAll();
    }

    public List<RoadTypeQuality> getAllRoadTypeQuality() {
        return roadTypeQualityRepo.findAll();
    }

    // ADD
    public void addTimeQualityType(TimeRoad timeRoad) {
        Optional<TimeRoad> timeRoadExisting = timeRoadRepo.findFirstByQualityAndType(timeRoad.getQuality(),
                timeRoad.getType());
        if (timeRoadExisting.isPresent())
            throw new RuntimeException("Specification for type = " + timeRoadExisting.get().getType().getName()
                    + " and quality = " + timeRoadExisting.get().getQuality().getName() + " already exists");
        timeRoadRepo.save(timeRoad);
    };

    public void addSizeRoad(SizeRoad sizeRoad) {
        Optional<SizeRoad> sizeRoadExisting = sizeRoadRepo.findFirstByName(sizeRoad.getName());
        if (sizeRoadExisting.isPresent())
            throw new RuntimeException("Size " + sizeRoad.getName() + " already exists");
        sizeRoadRepo.save(sizeRoad);
    };

    public void addType(Type type) {
        typeRepo.save(type);
    }

    public void addMaterial(Materiaux materiaux) {
        materiauxRepo.save(materiaux);
    }

    @Transactional
    public void addAllMaterials(List<TypeMateriaux> materials) {
        for (TypeMateriaux typeMateriaux : materials) {
            addMaterialToType(typeMateriaux);
        }
    }

    public void addMaterialToType(TypeMateriaux typeMateriaux) {
        Optional<TypeMateriaux> typeMateriauxExisting = typeMateriauxRepo
                .findFirstByTypeAndMateriaux(typeMateriaux.getType(), typeMateriaux.getMateriaux());
        if (typeMateriauxExisting.isPresent())
            throw new RuntimeException(
                    "This material ( " + typeMateriauxExisting.get().getMateriaux().getName()
                            + " ) is already used for the type " + typeMateriauxExisting.get().getType().getName());
        typeMateriauxRepo.save(typeMateriaux);
    }

    @Transactional
    public void addStructRoad(List<RoadStruct> roadStructs) {
        if (roadStructs.size() > 0) {
            RoadStruct roadStruct = roadStructs.get(0);

            RoadTypeQuality road = null;
            Optional<RoadTypeQuality> roadExist = roadTypeQualityRepo.findFirstByTypeAndQualityAndSize(
                    roadStruct.getRoadTypeQuality().getType(), roadStruct.getRoadTypeQuality().getQuality(),
                    roadStruct.getRoadTypeQuality().getSize());

            if (!roadExist.isPresent()) {
                road = roadTypeQualityRepo.save(roadStruct.getRoadTypeQuality());
                for (RoadStruct roadS : roadStructs) {
                    if (roadS.getQuantity() < 0)
                        throw new RuntimeException("Quantity can't be negative");
                    RoadMateriaux roadMateriaux = RoadMateriaux.builder().roadTypeQuality(road)
                            .quantity(roadS.getQuantity()).materiaux(roadS.getMateriaux()).build();
                    roadMateriauxRepo.save(roadMateriaux);
                }
            } else {
                road = roadExist.get();
                for (RoadStruct roadS : roadStructs) {
                    if (roadS.getQuantity() < 0)
                        throw new RuntimeException("Quantity can't be negative");
                    RoadMateriaux roadMateriaux = roadMateriauxRepo
                            .findFirstByRoadTypeQualityAndMateriaux(road, roadS.getMateriaux()).get();
                    roadMateriaux.setQuantity(roadS.getQuantity());
                    roadMateriauxRepo.save(roadMateriaux);
                }
            }
            ///
            /////
        } else
            throw new RuntimeException("Data empty");
    };

}
