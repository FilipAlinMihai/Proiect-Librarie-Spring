package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvocareRepository extends JpaRepository<Provocare,Long> {


    List<Provocare> findByUtilizator(Utilizator utilizator);

    @Modifying
    @Query("DELETE FROM Provocare a WHERE a.id = :id")
    void deleteById1(@Param("id") Long id);
}
