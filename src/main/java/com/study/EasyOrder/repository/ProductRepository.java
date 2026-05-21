package com.study.EasyOrder.repository;

import com.study.EasyOrder.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDeleteYnFalse();
    Optional<Product> findByProductIdAndDeleteYnFalse(Long productId);
    Page<Product> findByDeleteYnFalse(Pageable pageable);
}
