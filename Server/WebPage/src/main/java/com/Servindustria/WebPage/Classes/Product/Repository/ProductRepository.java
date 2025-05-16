package com.Servindustria.WebPage.Classes.Product.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Servindustria.WebPage.Classes.Product.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {

@Query(value = "SELECT * FROM producto WHERE id = :id", nativeQuery = true)
    Optional<Product> findProductById(@Param("id") Long id);
}
