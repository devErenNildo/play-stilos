package com.playstilos.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String email;
    private String image;

    public UserDTO(User user){
        this.name = user.getName();
        this.email = user.getLogin();
        this.image = user.getImage();
    }
}
