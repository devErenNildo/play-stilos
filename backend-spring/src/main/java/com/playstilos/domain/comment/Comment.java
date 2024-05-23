package com.playstilos.domain.comment;

import com.playstilos.domain.user.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Comment {
    private String comment;
    @DBRef(lazy = true)
    private User author;
    private LocalDate creationDate;

    public Comment(String comment, User author){
        this.comment = comment;
        this.author = author;
        this.creationDate = LocalDate.now();
    }
}
