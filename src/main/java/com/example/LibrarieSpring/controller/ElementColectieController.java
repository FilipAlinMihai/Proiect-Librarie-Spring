package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementColectie;
import com.example.LibrarieSpring.entity.ElementDeAdaugat;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.ElementColectieService;
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
    private ElementColectieService ecs;
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
       if(ecs.aduagaElementColectie(elementDeAdaugat))
       {
           return "gestionareColectii";
       }
        model.addAttribute("invalidData",true);

        return "adaugaElementColectie";
    }
}
