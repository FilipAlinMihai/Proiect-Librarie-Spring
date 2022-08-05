package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.repository.ColectieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColectieService {

    @Autowired
    ColectieRepository cr;

    public boolean adaugaColectie(Colectie colectie)
    {

        Colectie colecteDeAdaugat= new Colectie(colectie.getNume(), LoginController.utilizatorul);

        if(cr.findByUtilizatorAndNume(LoginController.utilizatorul,colecteDeAdaugat.getNume()).size()==0)
        {
            cr.save(colecteDeAdaugat);
            return true;
        }

      return false;
    }

    public List<Colectie> getColectii()
    {
        return cr.findAll();
    }


}
