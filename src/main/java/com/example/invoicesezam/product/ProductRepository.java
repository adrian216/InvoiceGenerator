package com.example.invoicesezam.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByNameLikeIgnoreCase(String search);

    @Query("select p from Product p where p.name like concat('%',?1,'%') or p.details like concat('%',?1,'%') or p.sku like concat('%',?1,'%')")
    List<Product> searchProducts(String search);
}
