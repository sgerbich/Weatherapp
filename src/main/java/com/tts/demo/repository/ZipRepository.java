package com.tts.demo.repository;

import com.tts.demo.model.ZipCode;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ZipRepository extends JpaRepository<ZipCode, Long>{
    public ZipCode findByZip(String zip);

    
}