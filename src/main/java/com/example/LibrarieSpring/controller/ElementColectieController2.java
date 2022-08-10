package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.ElementDeAdaugat;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.ElementColectieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ElementColectieController2 {

    @Autowired
    private CarteService cs;

    @Autowired
    private ColectieService cols;
    @Autowired
    private ElementColectieService ecs;

    @PostMapping("/adaugareElementColectieNou/{id1}/{id2}")
    public String aduagareElementColectieNou(@PathVariable("id1") long id1, @PathVariable("id2")  long id2) {

        Carte carte=cs.getCarteById(id1);
        Optional<Colectie> colectie=cols.cautaColectieDupaId(id2);

        if(carte!=null && colectie.isPresent() && carte.getUtilizator().getEmail().compareTo(colectie.get().getUtilizator().getEmail())==0)
        {
            LoginController.setUtilizatorul(carte.getUtilizator());
            ElementDeAdaugat elementDeAdaugat=new ElementDeAdaugat(colectie.get().getNume(), carte.getTitlu(), carte.getAutor());
            if(ecs.aduagaElementColectie(elementDeAdaugat))
                    return "Elementul a fost adaugat!";
            return "Elementul NU a fost adaugat";
        }
        return "Datele introduse sunt incorecte!";
    }

    @DeleteMapping("/stergeElementColectie/{id}")
    public String stergeElementColectie(@PathVariable("id") long id)
    {
        try {
            cols.stergeColectieById(id);
            return  "Elementul a fost sters";
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return  "Elementul nu a fost sters";
        }
    }
}
