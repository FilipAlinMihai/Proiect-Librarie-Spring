package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtilizatorController {

    @Autowired
    UtilizatorService utilizatorService;

    @RequestMapping(value="/adauga",method ={RequestMethod.POST,RequestMethod.GET} )
    public void add() {
        Utilizator u=new Utilizator("mihai@gmail.com","parola00");
        utilizatorService.adauaga(u);
    }

    @GetMapping("/afisare")
    public List<Utilizator> getAllPersoane() {
        return utilizatorService.getAllUtilizatori();
    }

    @GetMapping("/afisareById/{id}")
    public Utilizator getPersonaById(@PathVariable long id) {
        return utilizatorService.getUtilizatorById(id);
    }

    @GetMapping("/afisareByEmail/{email}")
    public Utilizator getPersonaByEmail(@PathVariable("email") String email) {
        return utilizatorService.getPrimulUtilizatorByEmail(email);
    }

    @GetMapping("/adaugareFaraFormular/{email}/{parola}")
    public String aduagareFaraFormular(@PathVariable("email") String email,@PathVariable("parola") String parola) {
        Utilizator u=new Utilizator(email,parola);
        utilizatorService.adauaga(u);

        return "Am adaugat";
    }

    @GetMapping("/cuParametru/{parametru}")
    public String probaparametru(@PathVariable("parametru") String parametru) {


        return "Am primit: "+parametru;
    }


}
