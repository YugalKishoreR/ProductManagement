package com.example.ProductManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.ProductManagement.entity.ProductRating;

@Repository
public interface ReviewRepository extends JpaRepository<ProductRating, Integer>, JpaSpecificationExecutor<ProductRating> {

	

}
