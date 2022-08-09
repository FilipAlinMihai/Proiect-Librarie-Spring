package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.SigninForm;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
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
    @Autowired
    private CarteService cs;

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

    @PostMapping("/adaugareFaraFormular/{email}/{parola}")
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

    @PostMapping("/inregstrare/{email}/{parola}")
    public String inregistrare(@PathVariable("email") String email,@PathVariable("parola") String parola) {
        if(ls.inregistrare(new SigninForm(email,parola,parola)))
            return "Utilizatorul a fost aduagat";
        else
            return "Utilizator nu a fost adaugat";
    }

    @GetMapping("/afisareStstistici/{id}")
    public String vizualizareStatistici(@PathVariable("id") long id) {

        try {
            Utilizator utilizator = utilizatorService.getUtilizatorById(id);

            if (utilizator != null) {
                return utilizatorService.procesareDateUtilizator(utilizator);
            }
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Utilizatorul Nu a fost Gasit";
        }

        return "Utilizatorul Nu a fost Gasit";
    }

    @PostMapping("/modificareParola/{id}/{parolaNoua}")
    public String modificareParola(@PathVariable("id") long id,@PathVariable("parolaNoua") String parolaNoua)
    {
        try {
            Utilizator utilizator = utilizatorService.getUtilizatorById(id);
            if (utilizator != null) {
                utilizator.setParola(parolaNoua);
                utilizatorService.modificaParola(utilizator);
                return "Date modificate";
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Utilizatorul Nu a fost Gasit";
        }

        return "Utilizatorul Nu a fost Gasit";
    }

    @DeleteMapping("/stergeUtilizator/{id}")
    public String stergeUtilizator(@PathVariable("id") long id)
    {
        try {
            Utilizator utilizator = utilizatorService.getUtilizatorById(id);
            if (utilizator != null) {
                utilizatorService.stergeUtilizatorId(utilizator.getId());
                return "Utilizator Sters";
            }
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Utilizatorul NU a fost gasit!";
        }

        return "Utilizatorul NU a fost gasit!";
    }

    @GetMapping("/afisareCartiFinalizate/{id}")
    public List<Carte> afisareCartiFinalizate(@PathVariable("id") long id)
    {
        Utilizator utilizator=utilizatorService.getUtilizatorById(id);

        return cs.cautaCartiFinalizate(utilizator);
    }

}
