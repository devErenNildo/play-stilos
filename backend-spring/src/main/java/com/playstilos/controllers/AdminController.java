package com.playstilos.controllers;

import com.playstilos.domain.admin.Admin;
import com.playstilos.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public Admin creteAdmin(@RequestBody Admin admin){
        return adminService.createAdmin(admin);
    }
}
