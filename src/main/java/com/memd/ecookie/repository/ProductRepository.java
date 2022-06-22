package com.memd.ecookie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.memd.ecookie.entity.ProductEntity;


@Transactional
public interface ProductRepository  extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
	ProductEntity findByName(String name);
}
