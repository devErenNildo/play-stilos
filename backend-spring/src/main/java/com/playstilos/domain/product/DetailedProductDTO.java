package com.playstilos.domain.product;

import com.playstilos.domain.comment.CommentAndAuthor;

import java.util.List;

public record DetailedProductDTO(String id, String name, double price, String description, String image, List<CommentAndAuthor> commentAndAuthors) {
}
