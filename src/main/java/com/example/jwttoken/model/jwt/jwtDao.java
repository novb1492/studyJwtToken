package com.example.jwttoken.model.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface jwtDao extends JpaRepository<jwtDto,Integer> {
    jwtDto findByTokenName(String name);
}
