package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarteRepository extends JpaRepository<Carte,Long> {

    List<Carte> findByAutorAndTitluAndUtilizator(String autor, String titlu, Utilizator utilizator);

    List<Carte> findByUtilizator(Utilizator utilizator);

    Carte findFirstByAutorAndTitluAndUtilizator(String autor, String titlu, Utilizator utilizator);

    Carte findFirstByAutorAndTitlu(String autor,String titlu);

    Carte cautaDupaId(@Param("id") long id);
    void stergeDupaId(@Param("id") long id);

    Carte findByTitluAndUtilizator(String titlu,Utilizator utilizator);
    void deleteByTitluAndUtilizator(String titlu,Utilizator utilizator);

    @Modifying
    @Query("DELETE FROM Carte a WHERE a.id = :id")
    void deleteById1(@Param("id") Long id);
}
