package com.playstilos.controllers;

import com.playstilos.domain.comment.Comment;
import com.playstilos.domain.product.Product;
import com.playstilos.domain.product.ProductAvailable;
import com.playstilos.domain.product.SimpleProductDTO;
import com.playstilos.domain.user.User;
import com.playstilos.domain.user.UserCommentDTO;
import com.playstilos.services.AuthorizationService;
import com.playstilos.services.ImageUploadService;
import com.playstilos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private AuthorizationService userService;

//  rota para adicionar um produto
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

        Product product = new Product(name, price, category, imageUrl);

        productService.addProduct(product);

        return ResponseEntity.ok().body("product added");
    }

//  Rota para apagar um produto
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


//  rota para listar todos os produtos, imagem, preço e nome
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

//  rota para listar os produtos de forma simples
    @GetMapping("/home")
    public ResponseEntity<List<SimpleProductDTO>> getAllSimpleProducts(){
        List<SimpleProductDTO> productDTOS = productService.getAllSimpleProduct();
        return ResponseEntity.ok(productDTOS);
    }


//  adcionar comentário
    @PostMapping("/comment/{idProduct}")
    public ResponseEntity<Product> addComment(@PathVariable String idProduct, @RequestBody String textComment){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserEmail(authentication.getName());

        Comment comment = new Comment(textComment, user.getId());

        return ResponseEntity.ok(productService.addComment(idProduct, comment));
    }


    ////    Rota para editar um produto
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productUpdate){
//        return ResponseEntity.ok(productService.updateProduct(id, productUpdate));
//    }
}
