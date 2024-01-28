package com.project.s5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.s5.models.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

}
