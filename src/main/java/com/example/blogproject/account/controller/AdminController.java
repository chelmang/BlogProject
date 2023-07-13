package com.example.blogproject.account.controller;

import com.example.blogproject.account.dto.AccountReqDto;
import com.example.blogproject.account.service.AdminService;
import com.example.blogproject.global.dto.GlobalResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/") //테스트용
    @Secured("ROLE_ADMIN")
    ResponseEntity<GlobalResDto> adminOnly(){
        return new ResponseEntity(new GlobalResDto("success",200),HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    @Secured("ROLE_ADMIN")
    ResponseEntity<List<AccountReqDto>> getAllUsers(){
        List<AccountReqDto> accountDtos = adminService.getAllUsers();
        return ResponseEntity.ok(accountDtos);
    }
}
