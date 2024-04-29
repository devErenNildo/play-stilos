package com.playstilos.repositories;

import com.playstilos.domain.admin.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

public interface AdminRepository extends MongoRepository<Admin, String> {
}
