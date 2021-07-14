package com.example.jwttoken.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userDao  extends JpaRepository<userDto,Integer>{
    userDto findByEmail(String email);
}
