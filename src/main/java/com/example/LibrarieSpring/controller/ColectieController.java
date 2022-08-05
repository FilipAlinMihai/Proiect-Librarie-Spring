package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ColectieController {

    @Autowired
    ColectieService cs;
    @Autowired
    ColectieRepository cr;

    @Autowired
    ElementColectieRepository ecr;

    @RequestMapping(value = "/gestionareColectii", method = RequestMethod.GET)
    public String getPaginaGestionareColectii()
    {
        return "gestionareColectii";
    }

    @RequestMapping(value = "/adaugaColectie", method = RequestMethod.GET)
    public String getPaginaAdaugareColectie()
    {
        return "adaugaColectie";
    }


    @RequestMapping(value = "/adaugaColectie" , method=RequestMethod.POST)
    public String adaugaOcolectie(@ModelAttribute(name="colectie") Colectie colectie, Model model)
    {
        if(cs.adaugaColectie(colectie))
        {
            return "gestionareColectii";
        }
        model.addAttribute("invalidData",true);

        return "adaugaColectie";
    }


    @RequestMapping(value = "/afisareColectii", method = RequestMethod.GET)
    public String afisareColectii(Model model)
    {
        List<Colectie> colectii=cr.findByUtilizator(LoginController.utilizatorul);
        model.addAttribute("numeatribut","Colectiile dumneavoastra");

        model.addAttribute("colectii", colectii);
        return "afisareColectii";
    }

    @RequestMapping(value = "/alegeColectie", method = RequestMethod.GET)
    public String getPaginaAlegeColectie()
    {
        return "alegeColectie";
    }


    @RequestMapping(value = "/alegeColectie" , method=RequestMethod.POST)
    public String AlegeColectie(@ModelAttribute(name="colectie") Colectie colectie, Model model)
    {

        Colectie colecteDeAfisat= new Colectie(colectie.getNume(),LoginController.utilizatorul);


        if(cr.findByUtilizatorAndNume(LoginController.utilizatorul,colecteDeAfisat.getNume()).size()==1)
        {
            List<Colectie> colectiile =cr.findByUtilizatorAndNume(LoginController.utilizatorul,colecteDeAfisat.getNume());
            List<ElementColectie> elementeColectie=ecr.findByColectie(colectiile.get(0));
            model.addAttribute("numeatribut","Carti");

            model.addAttribute("colectie", elementeColectie);
            return "afisareColectie";
        }

        model.addAttribute("invalidData",true);

        return "alegeColectie";
    }

}
