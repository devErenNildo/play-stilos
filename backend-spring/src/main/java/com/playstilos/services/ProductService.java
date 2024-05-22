package com.playstilos.services;

import com.playstilos.domain.comment.Comment;
import com.playstilos.domain.product.Product;
import com.playstilos.domain.product.ProductAvailable;
import com.playstilos.domain.product.SimpleProductDTO;
import com.playstilos.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

//  Adicionar produto
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

//  Obter produto por id
    public Optional<Product> getById(String id){
        return productRepository.findById(id);
    }

//  Listar todos os produtos
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

//  Listar os produtos de forma simples
    public List<SimpleProductDTO> getAllSimpleProduct(){
        List<Product> product = productRepository.findAll();

        List<SimpleProductDTO> simpleProductDTO = product.stream()
                .filter(product1 -> product1.getAvailable() == ProductAvailable.AVAILABLE)
                .map(product1 -> new SimpleProductDTO(product1.getName(), product1.getPrice(), product1.getImage()))
                .collect(Collectors.toList());
        return simpleProductDTO;
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

//  Adicionar comentário
    public Product addComment(String productId, Comment comment){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.getComments().add(comment);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

}
