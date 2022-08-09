package com.example.LibrarieSpring.repository;

import com.example.LibrarieSpring.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface UtilizatorRepository extends JpaRepository<Utilizator,Long> {

    Utilizator findFirstByEmail(String email);
    Utilizator findById(long id);
    @Modifying
    @Query("DELETE FROM Utilizator a WHERE a.id = :id")
    void deleteById1(@Param("id") Long id);
}
