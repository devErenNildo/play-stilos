package com.playstilos.services;

import com.playstilos.domain.admin.Admin;
import com.playstilos.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin createAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public void deleteAdmin(String id){
        adminRepository.deleteById(id);
    }

    public Optional<Admin> getById(String id){
        return adminRepository.findById(id);
    }
}
