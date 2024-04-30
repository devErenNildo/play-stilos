package com.playstilos.controllers;

import com.playstilos.domain.admin.Admin;
import com.playstilos.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public Admin creteAdmin(@RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String id){
        Optional<Admin> adminOptional = adminService.getById(id);
        if(adminOptional.isPresent()){
            adminService.deleteAdmin(id);
            return ResponseEntity.ok("user successfully deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }

}
