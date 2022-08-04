package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator,Long> {

    Utilizator findFirstByEmail(String email);
    Utilizator findById(long id);
}
