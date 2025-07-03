package com.example.simpleloginapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity


public class User {

    @Id // 기본 키임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
