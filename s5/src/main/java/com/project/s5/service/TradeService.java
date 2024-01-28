package com.project.s5.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.s5.models.Achat;
import com.project.s5.models.Entreprise;
import com.project.s5.models.Materiaux;
import com.project.s5.models.Movement;
import com.project.s5.models.PriceMateriaux;
import com.project.s5.models.RoadMateriaux;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.Stock;
import com.project.s5.models.Supplier;
import com.project.s5.models.Vente;
import com.project.s5.repository.AchatRepo;
import com.project.s5.repository.MateriauxRepo;
import com.project.s5.repository.PriceMateriauxRepo;
import com.project.s5.repository.RoadMateriauxRepo;
import com.project.s5.repository.RoadTypeQualityRepo;
import com.project.s5.repository.StockRepo;
import com.project.s5.repository.SupplierRepo;
import com.project.s5.repository.VenteRepo;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final SupplierRepo supplierRepo;
    private final AchatRepo achatRepo;
    private final VenteRepo venteRepo;
    private final StockRepo stockRepo;
    private final MovementService movementService;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final RoadMateriauxRepo roadMateriauxRepo;
    private final PriceMateriauxRepo priceMateriauxRepo;
    private final MateriauxRepo materiauxRepo;

    public void registerSuupplier(@NonNull Supplier supplier) {
        supplierRepo.save(supplier);
    }

    public List<Supplier> getAllSupplier() {
        return supplierRepo.findAll();
    }

    public List<Stock> getAllStock() {
        return stockRepo.findAll();
    }

    public List<Movement> getAllMovement() {
        return movementService.getAll();
    }

    @Transactional
    public void buyMatiere(Materiaux materiaux, Supplier supplier, Double quantity) {
        if (quantity <= 0)
            throw new RuntimeException("Quantité invalide");
        materiaux = materiauxRepo.findById(materiaux.getId()).get();
        Movement movement = movementService.addMovement(materiaux, quantity, "in");
        Achat achat = Achat.builder().materiaux(materiaux).supplier(supplier).quantity(quantity).movement(movement)
                .build();
        achatRepo.save(achat);
        updateStock(quantity, materiaux);
    }

    public void updateStock(Double quantity, Materiaux materiaux) {
        Stock newStock = null;
        Optional<Stock> stock = stockRepo.findFirstByMateriaux(materiaux);
        if (!stock.isPresent())
            newStock = Stock.builder().quantity(quantity).materiaux(materiaux).build();
        else {
            newStock = stock.get();
            newStock.setQuantity(newStock.getQuantity() + quantity);
        }
        stockRepo.save(newStock);
    }

    @Transactional
    public void sellProduct(RoadTypeQuality roadTypeQuality, Double quantity, Entreprise entreprise) {
        if (quantity <= 0)
            throw new RuntimeException("Quantité invalide");
        Timestamp now = Timestamp.from(Instant.now());
        Double price = getPriceProductOnSelling(roadTypeQuality, quantity);
        Vente vente = Vente.builder().roadTypeQuality(roadTypeQuality).quantity(quantity).price(price).date(now)
                .entreprise(entreprise)
                .build();
        venteRepo.save(vente);
    }

    public Double getPriceProductOnSelling(RoadTypeQuality roadTypeQuality, Double quantity_road) {
        RoadTypeQuality road = roadTypeQualityRepo.findById(roadTypeQuality.getId()).get();

        List<RoadMateriaux> roadMateriauxs = getByRoadQuality(road);
        updateQuantityOnSelling(roadMateriauxs, quantity_road);
        Double priceTotal = 0.0;

        for (RoadMateriaux roadMaterial : roadMateriauxs) {
            PriceMateriaux priceMateriaux = priceMateriauxRepo.findFirstByMateriaux(roadMaterial.getMateriaux())
                    .get();
            priceTotal += roadMaterial.getQuantity() * priceMateriaux.getPrice();// Unit 1 auto
        }
        return priceTotal;
    }

    public Double getPriceProductOnSellingWithoutUpdate(RoadTypeQuality roadTypeQuality, Double quantity_road) {
        RoadTypeQuality road = roadTypeQualityRepo.findById(roadTypeQuality.getId()).get();

        List<RoadMateriaux> roadMateriauxs = getByRoadQuality(road);
        Double priceTotal = 0.0;

        for (RoadMateriaux roadMaterial : roadMateriauxs) {
            PriceMateriaux priceMateriaux = priceMateriauxRepo.findFirstByMateriaux(roadMaterial.getMateriaux())
                    .get();
            priceTotal += roadMaterial.getQuantity() * priceMateriaux.getPrice();// Unit 1 auto
        }
        return priceTotal;
    }

    public void updateQuantityOnSelling(List<RoadMateriaux> roadMateriauxs, Double qt_road) {
        for (RoadMateriaux roadMateriaux : roadMateriauxs) {
            Stock stock = stockRepo.findFirstByMateriaux(roadMateriaux.getMateriaux()).get();
            if (stock == null)
                throw new RuntimeException(" Quantité de " + roadMateriaux.getMateriaux().getName()
                        + " est insuffisant de " + roadMateriaux.getQuantity());
            else {
                Double qt_restant = stock.getQuantity() - (roadMateriaux.getQuantity() * qt_road);
                if (qt_restant < 0) {
                    throw new RuntimeException(" Quantité de " + roadMateriaux.getMateriaux().getName()
                            + " est insuffisant de " + (qt_restant * -1));
                } else {
                    stock.setQuantity(qt_restant);
                    stockRepo.save(stock);
                }
                movementService.addMovement(roadMateriaux.getMateriaux(),
                        (roadMateriaux.getQuantity() * qt_road), "out");
            }
        }
    }

    public List<RoadMateriaux> getByRoadQuality(RoadTypeQuality roadTypeQuality) {
        List<RoadMateriaux> roadMateriauxs = roadMateriauxRepo.findByRoadTypeQuality(roadTypeQuality);
        return roadMateriauxs;
    }

}
