package com.playstilos.controllers;

import com.playstilos.domain.product.Product;
import com.playstilos.services.ImageUploadService;
import com.playstilos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageUploadService imageUploadService;

//    rota para adicionar um produto
    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(
            @RequestParam("image") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("category") String category
    ) throws IOException {
        if (file.isEmpty() || name.isEmpty() || Double.isNaN(price) || category.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("send all fields");
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        String imageUrl = imageUploadService.getImageUrl(tempFile);

        Product product = new Product(null, name, price, category, imageUrl);

        productService.addProduct(product);

        return ResponseEntity.ok().body("product added");
    }

//    Rota para apagar um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        Optional<Product> productOptional = productService.getById(id);
        if (productOptional.isPresent()){
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product successfully deleted");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
    }

//    Rota para editar um produto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productUpdate){
        return ResponseEntity.ok(productService.updateProduct(id, productUpdate));
    }
}
