package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.ProvocareService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProvocareController {

    @Autowired
    private ProvocareService ps;

    @Autowired
    private UtilizatorService us;

    @GetMapping(path = "/arataProvocari" )
    public List<Provocare> afisareToateProvocarile() {
        return ps.cautaToateProvocarile();

    }

    @GetMapping(path = "/cautaProvocareId/{id}" )
    public Provocare AfisareProvocareDupaId(@PathVariable("id") long  id) {
        Optional<Provocare> provocare= ps.cautaProvocareDupaId(id);
        if(provocare.isPresent())
            return provocare.get();
        else
            return null;
    }

    @GetMapping(path = "/cautaProvocariUtilizator/{id}" )
    public List<Provocare> AfisareProvocariDupaUtilizator(@PathVariable("id") long  id) {
            Utilizator utilizator=us.getUtilizatorById(id);
        return ps.cautaProvocarileUnuiUtilizator(utilizator);
    }


    @RequestMapping(value="/adaugaProvocare/{pagini}/{zile}/{id}/{nume}",method ={RequestMethod.POST,RequestMethod.GET} )
    public String  AdaugaProvocare(@PathVariable("pagini") int  pagini,@PathVariable("zile") int  zile,@PathVariable("id") long  id,@PathVariable("nume") String  nume) {

        Utilizator utilizator=us.getUtilizatorById(id);
        Provocare provocare=new Provocare(pagini,zile,utilizator,nume);
        ps.adauga(provocare);

        return "Am adaugat provocarea";
    }


    @GetMapping(path = "/afisareSituatieProvocare/{id}" )
    public String afisareSituatieProvocare(@PathVariable("id") long  id) {
        Optional<Provocare> provocare=ps.cautaProvocareDupaId(id);
        if(provocare.isPresent())
        {
            return ps.situatieProvocare(provocare.get());
        }
        else return "Provocarea nu a fost gasita!";
    }

    @PostMapping("/modificareNumeProvocare/{id}/{nume}")
    public String modificareNume(@PathVariable("id") long id,@PathVariable("nume") String nume)
    {
        Optional<Provocare> provocare=ps.cautaProvocareDupaId(id);

        if(provocare.isPresent())
        {
            ps.modificaNume(provocare.get(),nume);
            return "Modificare efectuata!";
        }
        return "Provocarea nu a fost gasita!";
    }

}
