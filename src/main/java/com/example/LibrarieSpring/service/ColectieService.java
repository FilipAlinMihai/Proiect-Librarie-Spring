package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.ColectieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColectieService {

    @Autowired
    ColectieRepository cr;

    public boolean adaugaColectie(Colectie colectie)
    {

        Colectie colecteDeAdaugat= new Colectie(colectie.getNume(), LoginController.getUtilizatorul());

        if(cr.findByUtilizatorAndNume(LoginController.getUtilizatorul(),colecteDeAdaugat.getNume()).size()==0)
        {
            cr.save(colecteDeAdaugat);
            return true;
        }

      return false;
    }

    public Colectie cautaColectieUtilizatorNume(Utilizator utilizator, String nume)
    {
        return cr.findFirstByUtilizatorAndNume(utilizator,nume);
    }

    public List<Colectie> getColectii()
    {
        return cr.findAll();
    }

    public void stergeColectieById(long id)
    {
        cr.deleteById1(id);
    }

    public Optional<Colectie> cautaColectieDupaId(long id)
    {
        return cr.findById(id);
    }

}
