package com.project.s5.service;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.project.s5.Response.Statistique;
import com.project.s5.models.Entreprise;
import com.project.s5.models.RoadTypeQuality;
import com.project.s5.models.TypeEntreprise;
import com.project.s5.models.Vente;
import com.project.s5.repository.EntrepriseRepo;
import com.project.s5.repository.RoadTypeQualityRepo;
import com.project.s5.repository.TypeEntrepriseRepo;
import com.project.s5.repository.VenteRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatService {

    private final VenteRepo venteRepo;
    private final RoadTypeQualityRepo roadTypeQualityRepo;
    private final TypeEntrepriseRepo typeEntrepriseRepo;

    public List<Statistique> getStat(long idroadTypeQuality) {
        RoadTypeQuality roadTypeQuality = roadTypeQualityRepo.findById(idroadTypeQuality).get();
        List<Vente> ventes = venteRepo.findAllByRoadTypeQuality(roadTypeQuality);
        return groupedByType(ventes);
    }

    public List<Statistique> getStat(RoadTypeQuality roadTypeQuality) {
        List<Vente> ventes = venteRepo.findAllByRoadTypeQuality(roadTypeQuality);
        return groupedByType(ventes);
    }

    public List<List<Statistique>> getStatAll() {
        List<List<Statistique>> stat = new Vector<List<Statistique>>();
        List<RoadTypeQuality> roads = roadTypeQualityRepo.findAll();
        for (RoadTypeQuality roadTypeQuality : roads) {
            stat.add(getStat(roadTypeQuality));
        }
        return stat;
    }

    public List<Statistique> groupedByType(List<Vente> ventes) {
        List<Statistique> stat = new Vector<Statistique>();
        List<TypeEntreprise> types = typeEntrepriseRepo.findAll();

        for (TypeEntreprise typeEntreprise : types) {
            Statistique st = new Statistique();
            st.setVentes(new Vector<Vente>());
            st.setTypeEntreprise(typeEntreprise);
            for (Vente vente : ventes) {
                if (vente.getEntreprise().getType_entreprise().getId() == typeEntreprise.getId())

                {
                    st.getVentes().add(vente);
                }
            }
            stat.add(st);
        }

        return stat;
    }

}
