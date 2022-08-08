package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdaugaCarteController {

    @Autowired
    private CarteService cs;

    @RequestMapping(value = "/adaugaCarte", method = RequestMethod.GET)
    public String getAddBookForm()
    {
        return "adaugaCarte";
    }

    @RequestMapping(value = "/adaugaCarte" , method=RequestMethod.POST)
    public String addBook(@ModelAttribute(name="addBook") Carte addBook, Model model)
    {
        if(cs.adaugaCarte(addBook))
        {
            return "home";
        }
        model.addAttribute("invalidData",true);

        return "adaugaCarte";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String mergiAcasa()
    {

        return "home";
    }

    @RequestMapping(value = "/afisareCarti", method = RequestMethod.GET)
    public String getPersoana(Model model)
    {
        List<Carte> carti=cs.cautaCarteUtilizator(LoginController.getUtilizatorul());
        model.addAttribute("numeatribut","Cartile dumneavoastra");

        model.addAttribute("carti", carti);
        return "afisareCarti";
    }

    @RequestMapping(value = "/amCitit", method = RequestMethod.GET)
    public String getAdaugaProgres()
    {
        return "amCitit";
    }

    @RequestMapping(value = "/amCitit", method = RequestMethod.POST)
    public String AdaugaProgres(@ModelAttribute(name="carte") Carte carte, Model model)
    {
        if(cs.adaugaProgres(carte,carte.getNrcitite()))
        {
            return "home";
        }

        model.addAttribute("invalidData",true);
        model.addAttribute("procent",carte.getProcent());
        return "amCitit";
    }



}
