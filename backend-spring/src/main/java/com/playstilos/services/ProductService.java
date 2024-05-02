package com.playstilos.services;

import com.playstilos.domain.product.Product;
import com.playstilos.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> getById(String id){
        return productRepository.findById(id);
    }


    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(String id, Product productUpdate){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        BeanUtils.copyProperties(productUpdate, product, "id");

        return productRepository.save(product);
    }
}
