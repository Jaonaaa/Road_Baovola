package com.project.s5.Response;

import java.util.List;
import java.util.Vector;

import com.project.s5.models.TypeEntreprise;
import com.project.s5.models.Vente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statistique {

    TypeEntreprise typeEntreprise;
    List<Vente> ventes;

}
