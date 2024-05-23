package com.playstilos.services;

import com.playstilos.domain.comment.Comment;
import com.playstilos.domain.comment.CommentAndAuthor;
import com.playstilos.domain.product.DetailedProductDTO;
import com.playstilos.domain.product.Product;
import com.playstilos.domain.product.ProductAvailable;
import com.playstilos.domain.product.SimpleProductDTO;
import com.playstilos.repositories.ProductRepository;
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

//  Listar os produtos de forma simples
    public List<SimpleProductDTO> getAllSimpleProduct(){
        List<Product> product = productRepository.findAll();

        List<SimpleProductDTO> simpleProductDTO = product.stream()
                .filter(product1 -> product1.getAvailable() == ProductAvailable.AVAILABLE)
                .map(product1 -> new SimpleProductDTO(product1.getId(), product1.getName(), product1.getPrice(), product1.getImage()))
                .collect(Collectors.toList());
        return simpleProductDTO;
    }

//  listar o produto detalhado
    public DetailedProductDTO getDetailedProduct(String id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            Product product = productOptional.get();

            List<CommentAndAuthor> commentAndAuthors = product.getComments().stream()
                    .map(comment -> new CommentAndAuthor(comment.getComment(), comment.getAuthor().getName(), comment.getAuthor().getImage()))
                    .toList();

            DetailedProductDTO detailedProduct = new DetailedProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getImage(),
                    commentAndAuthors
            );
            return detailedProduct;
        }
        throw new RuntimeException("Product not found");
    }

    public void deleteProduct(String id){
        productRepository.deleteById(id);
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
