package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColectieRepository extends JpaRepository<Colectie,Long> {

    List<Colectie> findByNume(String nume);
    List<Colectie> findByUtilizator(Utilizator utilizator);
    List<Colectie> findByUtilizatorAndNume(Utilizator utilizator,String nume);
    Colectie findFirstByUtilizatorAndNume(Utilizator utilizator,String nume);
}
