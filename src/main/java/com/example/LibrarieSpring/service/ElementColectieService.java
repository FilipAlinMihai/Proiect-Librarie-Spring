package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import com.example.LibrarieSpring.entity.ElementDeAdaugat;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementColectieService {

    @Autowired
    private ElementColectieRepository ecr;
    @Autowired
    private CarteRepository cr;
    @Autowired
    private ColectieRepository colr;

    public boolean aduagaElementColectie(ElementDeAdaugat elementDeAdaugat)
    {

        Carte cartea1=cr.findFirstByAutorAndTitluAndUtilizator(elementDeAdaugat.getAutor(),elementDeAdaugat.getTitlu(), LoginController.getUtilizatorul());

        Colectie colectie1=colr.findFirstByUtilizatorAndNume(LoginController.getUtilizatorul(),elementDeAdaugat.getNume());

        if(cartea1!=null && colectie1!=null)
        {
            List<ElementColectie> elem=ecr.findByCarteAndColectie(cartea1,colectie1);
            if(elem.size()==0)
            {
                ElementColectie elementNou=new ElementColectie(cartea1,colectie1);
                ecr.save(elementNou);
                return true;
            }
        }
        return false;
    }
}
