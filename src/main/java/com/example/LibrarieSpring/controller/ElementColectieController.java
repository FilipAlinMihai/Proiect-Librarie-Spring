package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import com.example.LibrarieSpring.entity.ElementDeAdaugat;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ElementColectieController {

    @Autowired
    private ElementColectieRepository ecr;

    @Autowired
    private CarteService cs;

    @Autowired
    private CarteRepository cr;

    @Autowired
    private ColectieRepository colr;

    @RequestMapping(value = "/adaugaElementColectie", method =RequestMethod.GET)
    public String getAddElementForm()
    {

        return "adaugaElementColectie";
    }

    @RequestMapping(value = "/adaugaElementColectie" , method = RequestMethod.POST)
    public String addElement(@ModelAttribute(name="element") ElementDeAdaugat elementDeAdaugat, Model model)
    {
       // System.out.println(elementDeAdaugat.getNume()+" ++ "+elementDeAdaugat.getTitlu());

        Carte cartea1=cr.findFirstByAutorAndTitluAndUtilizator(elementDeAdaugat.getAutor(),elementDeAdaugat.getTitlu(),LoginController.utilizatorul);

        Colectie colectie1=colr.findFirstByUtilizatorAndNume(LoginController.utilizatorul,elementDeAdaugat.getNume());

        if(cartea1!=null && colectie1!=null)
        {
            List<ElementColectie> elem=ecr.findByCarteAndColectie(cartea1,colectie1);
            if(elem.size()==0)
            {
                ElementColectie elementNou=new ElementColectie(cartea1,colectie1);
                ecr.save(elementNou);
                return "gestionareColectii";
            }
        }
        model.addAttribute("invalidData",true);

        return "adaugaElementColectie";
    }
}
