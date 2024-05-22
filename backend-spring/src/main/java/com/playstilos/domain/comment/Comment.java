package com.playstilos.domain.comment;

import com.playstilos.domain.user.User;
import com.playstilos.domain.user.UserCommentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class Comment {
    private String comment;
    private String idAuthor;
    private LocalDate creationDate;

    public Comment(String comment, String idAuthor){
        this.comment = comment;
        this.idAuthor = idAuthor;
        this.creationDate = LocalDate.now();
    }
}
