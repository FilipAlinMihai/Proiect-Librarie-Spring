package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementColectieRepository  extends JpaRepository<ElementColectie,Long> {

    List<ElementColectie> findByCarteAndColectie(Carte carte, Colectie colectie);
}
