package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteRepository extends JpaRepository<Carte,Long> {

    List<Carte> findByAutorAndTitluAndUtilizator(String autor, String titlu, Utilizator utilizator);

    List<Carte> findByUtilizator(Utilizator utilizator);

    Carte findFirstByAutorAndTitluAndUtilizator(String autor, String titlu, Utilizator utilizator);

    Carte findFirstByAutorAndTitlu(String autor,String titlu);


}
