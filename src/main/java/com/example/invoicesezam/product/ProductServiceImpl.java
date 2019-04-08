package com.example.invoicesezam.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository theProductRepository)
    {
        productRepository = theProductRepository;
    }

    @Override
    public void save(Product product)
    {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
