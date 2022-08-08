package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.SigninForm;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.LoginService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtilizatorController {

    @Autowired
    private UtilizatorService utilizatorService;

    @Autowired
    private LoginService ls;

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

    @GetMapping("/validare/{email}/{parola}")
    public String Verificare(@PathVariable("email") String email,@PathVariable("parola") String parola) {
       if(ls.login(new LoginForm(email,parola)))
           return "Utilizatorul a fost gasit";
       else
           return "Utilizator inexistent";
    }

    @GetMapping("/inregstrare/{email}/{parola}")
    public String inregistrare(@PathVariable("email") String email,@PathVariable("parola") String parola) {
        if(ls.inregistrare(new SigninForm(email,parola,parola)))
            return "Utilizatorul a fost aduagat";
        else
            return "Utilizator nu a fost adaugat";
    }

    @GetMapping("/afisareStstistici/{id}")
    public String vizualizareStatistici(@PathVariable("id") long id) {

        Utilizator utilizator =utilizatorService.getUtilizatorById(id);

        if(utilizator!=null)
        {
           return utilizatorService.procesareDateUtilizator(utilizator);
        }

        return "Utilizatorul Nu a fost Gasit";
    }

}
