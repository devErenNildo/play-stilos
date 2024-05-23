package com.playstilos.repositories;

import com.playstilos.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByLogin(String login);

    @Query("{ 'login' : ?0}")
    User findByUserLogin(String login);
}
