package com.playstilos.controllers;

import com.playstilos.domain.admin.Admin;
import com.playstilos.domain.admin.AuthenticationAdminDTO;
import com.playstilos.domain.user.LoginResponseDTO;
import com.playstilos.domain.user.User;
import com.playstilos.infra.security.TokenService;
import com.playstilos.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationAdminDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

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
