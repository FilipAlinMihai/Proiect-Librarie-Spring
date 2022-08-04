package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
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
public class AdaugaCarteController {

    @Autowired
    UtilizatorService us;

    @Autowired
    CarteService cs;

    @RequestMapping(value = "/adaugaCarte", method = RequestMethod.GET)
    public String getAddBookForm()
    {

        return "adaugaCarte";
    }

    @RequestMapping(value = "/adaugaCarte" , method=RequestMethod.POST)
    public String addBook(@ModelAttribute(name="addBook") Carte addBook, Model model)
    {
        String titlu=addBook.getTitlu();
        String autor=addBook.getAutor();
        int nr=addBook.getNrpagini();
        Carte carteDeAdaugat=new Carte(titlu,autor,nr,LoginController.utilizatorul);

        List<Carte> cartiDuplicate=cs.cautaCarteAutorTitluUtilizator(autor,titlu,LoginController.utilizatorul);

        if(cartiDuplicate.size()==0)
        {
            cs.adaugaCarte(carteDeAdaugat);
            return "home";
        }

        //System.out.println(carteDeAdaugat.afisareCarte());

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
        List<Carte> carti=cs.cautaCarteUtilizator(LoginController.utilizatorul);
        model.addAttribute("numeatribut","Cartile dumneavoastra");

        model.addAttribute("carti", carti);
        return "afisareCarti";
    }




}
