package com.playstilos.domain.product;

import com.playstilos.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private String category;
    private String description;
    private String image;
    private int stars;
    private List<Comment> comments;
    private VisibilityStatus visibility;

    public Product(String name, double price, String category, String image){
        this.id = null;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stars = 0;
        this.comments = new ArrayList<>();
        this.visibility = VisibilityStatus.AVAILABLE;
    }
}
