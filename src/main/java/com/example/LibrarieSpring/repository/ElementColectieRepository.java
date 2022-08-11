package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementColectieRepository  extends JpaRepository<ElementColectie,Long> {

    List<ElementColectie> findByCarteAndColectie(Carte carte, Colectie colectie);
    ElementColectie findFirstByCarteAndColectie(Carte carte, Colectie colectie);
    List<ElementColectie> findByColectie(Colectie colectie);

    List<ElementColectie> findByCarte(Carte carte);
    @Modifying
    @Query("DELETE FROM ElementColectie a WHERE a.id = :id")
    void deleteById1(@Param("id") Long id);

}
