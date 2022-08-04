package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.repository.ColectieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColectieService {

    @Autowired
    ColectieRepository cr;

    public void  adaugaColectie(Colectie colectie)
    {
        cr.save(colectie);
    }

    public List<Colectie> getColectii()
    {
        return cr.findAll();
    }


}
