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

import javax.transaction.Transactional;
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
        try {
            Optional<Colectie> colectia = cs.cautaColectieDupaId(id);
            if (colectia.isPresent()) {
                return colectia.get();
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
             return new Colectie();
        }
         return new Colectie();
    }

    @RequestMapping(value = "/inserareColectie/{id}/{nume}" , method=RequestMethod.POST)
    public String inserareColectie(@PathVariable("id") long id,@PathVariable("nume") String nume) {
        Utilizator utilizator=null;
        try {
             utilizator = us.getUtilizatorById(id);
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Colectia NU a fost adaugata. Utilizatorul nu a fost gasit!";
        }
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
        try {
            Optional<Colectie> colectie = cs.cautaColectieDupaId(id);

            if (colectie.isPresent()) {
                cs.modificaNume(colectie.get(), nume);
                return "Modificare efectuata!";
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Colectia nu a fost gasita!";
        }
        return "Colectia nu a fost gasita!";
    }

    @DeleteMapping("/stergeColectiePrinID/{id}")
    @Transactional
    public String stergeColectiePrinId(@PathVariable("id") long id)
    {
        try{
            Optional<Colectie> colectie=cs.cautaColectieDupaId(id);
            if(colectie.isPresent())
            {
                cs.stergeColectieById(colectie.get().getId());
                return  "Colectia a fost stearsa!";
            }
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Colectia nu a fost gasita!";
        }
        return "Colectia nu a fost gasita!";
    }

}
