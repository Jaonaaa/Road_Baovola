package com.project.s5.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Materiaux;

import com.project.s5.models.Type;
import com.project.s5.models.TypeMateriaux;

public interface TypeMateriauxRepo extends JpaRepository<TypeMateriaux, Long> {

    List<TypeMateriaux> findAllByType(Type type);

    Optional<TypeMateriaux> findFirstByTypeAndMateriaux(Type type, Materiaux materaux);

}
