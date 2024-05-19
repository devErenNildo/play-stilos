package com.playstilos.domain.comment;

import com.playstilos.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Data
public class Comment {
    private String comment;
    private String idAuthor;

    public Comment(String comment, String idAuthor){
        this.comment = comment;
        this.idAuthor = comment;
    }
}
