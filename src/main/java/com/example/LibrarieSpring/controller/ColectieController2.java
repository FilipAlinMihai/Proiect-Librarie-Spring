package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ColectieController2 {


    @Autowired
    private UtilizatorService us;

    @Autowired
    private ColectieService cs;

    @Autowired
    private CarteRepository cr;

    @RequestMapping(value = "/arataColectii" , method= RequestMethod.GET)
    public List<Colectie> afisareToateColectiile() {
        return cs.getColectii();

    }

    @RequestMapping(value = "/afisareColectieId/{id}" , method=RequestMethod.GET)
    public Colectie afisareColectieDupaId(@PathVariable long id) {
        Optional<Colectie> colectia=cs.cautaColectieDupaId(id);
        if(colectia.isPresent())
        {
            return colectia.get();
        }
        else return new Colectie();
    }

    @RequestMapping(value = "/inserareColectie/{id}/{nume}" , method=RequestMethod.POST)
    public String inserareColectie(@PathVariable("id") long id,@PathVariable("nume") String nume) {
        Utilizator utilizator=us.getUtilizatorById(id);
        if(utilizator==null)
            return "Colectia NU a fost adaugata. Utilizatorul nu a fost gasit!";
        LoginController.setUtilizatorul(utilizator);
        if(cs.adaugaColectie(new Colectie(nume)))
            return "Colectia a fost adaugata";
        return "Colectia NU a fost adaugata";
    }

    @PostMapping("/modificareNumeColectie/{id}/{nume}")
    public String modificareNume(@PathVariable("id") long id,@PathVariable("nume") String nume)
    {
        Optional<Colectie> colectie=cs.cautaColectieDupaId(id);

        if(colectie.isPresent())
        {
            cs.modificaNume(colectie.get(),nume);
            return "Modificare efectuata!";
        }
        return "Provocarea nu a fost gasita!";
    }

}
