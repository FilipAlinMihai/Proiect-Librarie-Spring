package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    CarteRepository cr;

    public void adaugaCarte(Carte c)
    {
        cr.save(c);
    }

    public List<Carte> getAllBooks()
    {
        List<Carte> carti=cr.findAll();
        return carti;
    }

    public Optional<Carte> getBookById(long id)
    {
        Optional<Carte> cartea= cr.findById(id);
        return cartea;
    }

    public List<Carte> cautaCarteAutorTitluUtilizator(String autor, String titlu, Utilizator utilizator)
    {
        List<Carte> cartiGasite=cr.findByAutorAndTitluAndUtilizator(autor,titlu,utilizator);

        return cartiGasite;
    }

    public List<Carte> cautaCarteUtilizator(Utilizator utilizator)
    {
        List<Carte> cartiGasite=cr.findByUtilizator(utilizator);

        return cartiGasite;
    }


}
