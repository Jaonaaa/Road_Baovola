package com.project.s5.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.s5.models.Materiaux;
import com.project.s5.models.PriceMateriaux;

public interface MateriauxRepo extends JpaRepository<Materiaux, Long> {

    // @Query("SELECT ar FROM article_magasin ar WHERE ar.article.id = :ida AND
    // ar.magasin.id = :idm ORDER BY id DESC LIMIT 1")
    // Optional<ArticleMagasin> findByArticleAndMagasin(@Param("ida") long
    // id_article, @Param("idm") long id_magasin);

    // @Query("SELECT ar FROM article_magasin ar WHERE ar.magasin.id = :idm ORDER BY
    // id DESC")
    // List<ArticleMagasin> findByAllArticleAndMagasin(@Param("idm") long
    // id_magasin);
}
