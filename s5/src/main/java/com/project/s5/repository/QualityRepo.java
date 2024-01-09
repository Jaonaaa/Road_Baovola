package com.project.s5.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Quality;

public interface QualityRepo extends JpaRepository<Quality, Long> {

        // List<Movement> findAllByOrderByDateDesc();

        // List<Movement> findAllByMagasinOrderByDateDesc(Magasin magasin);

        // List<Movement> findAllByEtatOrderByDateDesc(Integer etat);

        // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida ORDER BY date
        // DESC")
        // List<Movement> findAllByIdArticle(@Param("ida") long id_article);

        // @Query("SELECT ar FROM movement ar WHERE ar.magasin.id = :idm ORDER BY date
        // DESC")
        // List<Movement> findAllByIdMagasin(@Param("idm") long id_magasin);

        // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida AND
        // ar.magasin.id = :idm ORDER BY date DESC")
        // Optional<List<Movement>> getByArticleAndMagasin(@Param("ida") long
        // id_article, @Param("idm") long id_magasin);

        // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida AND
        // ar.magasin.id = :idm ORDER BY date DESC LIMIT 1")
        // Optional<Movement> findLastMovement(@Param("ida") long id_article,
        // @Param("idm") long id_magasin);

        // @Query("SELECT ar FROM movement ar WHERE ar.article.id = :ida AND
        // ar.magasin.id = :idm AND ar.date >= :d_debut AND ar.date <= :d_fin ORDER BY
        // date DESC ")
        // List<Movement> findMovementBetween(@Param("ida") long id_article,
        // @Param("idm") long id_magasin,
        // @Param("d_debut") Timestamp date_debut, @Param("d_fin") Timestamp date_fin);

        // List<Movement> findAllByArticleAndMagasinAndEtat(Article article, Magasin
        // magasin,
        // Integer etat);
}
