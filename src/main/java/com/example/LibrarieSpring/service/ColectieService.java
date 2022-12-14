package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColectieService {

    @Autowired
    private ColectieRepository cr;

    @Autowired
    private ElementColectieRepository ecolr;

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
        if(cr.findFirstByUtilizatorAndNume(utilizator,nume)!=null)
            return cr.findFirstByUtilizatorAndNume(utilizator,nume);
        else throw new RuntimeException("Nu am gasit colectia cu acest nume: " + nume+" si utilizator "+utilizator.getEmail());

    }

    public List<Colectie> getColectii()
    {
        return cr.findAll();
    }

    public void stergeColectieById(long id)
    {
        Optional<Colectie> colectie=cr.findById(id);
        if(colectie.isPresent()) {

            List<ElementColectie> elementeColectie=ecolr.findByColectie(colectie.get());

            for(ElementColectie ec:elementeColectie)
            {
                ecolr.deleteById1(ec.getId());
            }

            cr.deleteById1(id);
        }
    }

    public Optional<Colectie> cautaColectieDupaId(long id)
    {
        Optional<Colectie> colectie=cr.findById(id);
        if(colectie.isPresent())
            return colectie;
        else throw new RuntimeException("Nu am gasit colectia cu acest id: " + id);
    }

    public void modificaNume(Colectie colectie,String nume)
    {
        colectie.setNume(nume);
        cr.save(colectie);
    }
}
