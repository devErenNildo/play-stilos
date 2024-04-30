package com.playstilos.controllers;

import com.playstilos.domain.product.Product;
import com.playstilos.services.ImageUploadService;
import com.playstilos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageUploadService imageUploadService;

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
}
