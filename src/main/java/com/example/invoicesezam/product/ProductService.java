package com.example.invoicesezam.product;

import java.util.List;

public interface ProductService
{
    void save(Product product);

    List<Product> getAll();
}
