package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class CarteController {

    @Autowired
    UtilizatorService us;

    @Autowired
    CarteService cs;

    @Autowired
    CarteRepository cr;

    @RequestMapping(value = "/arataCarti" , method= RequestMethod.GET)
    public List<Carte> afisareToateCartile() {
        return cs.getAllBooks();

    }

    @RequestMapping(value = "/afisareCaretId/{id}" , method=RequestMethod.GET)
    public Carte afisareCarteDupaId(@PathVariable long id) {

        try {
            Carte cartea = cs.getCarteById(id);
            if (cartea != null)
                return cartea;
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return new Carte("Cartea cu aces ID NU exista");
        }

        return new Carte("Cartea cu aces ID NU exista");
    }

    @PostMapping(value = "/adaugareFaraFormular/{titlu}/{autor}/{pagini}/{id}" )
    public String aduagareFaraFormular(@PathVariable("titlu") String titlu,@PathVariable("autor") String autor,@PathVariable("pagini") int pagini,@PathVariable("id") long id) {
        Carte cartea=new Carte(titlu,autor,pagini);
        if(us.getUtilizatorById(id)==null)
            return "Cartea NU a fost adaugata";
        LoginController.setUtilizatorul(us.getUtilizatorById(id));
        if(cs.adaugaCarte(cartea))
        {
            return "Am adaugat";
        }
        return "Cartea NU a fost adaugata";
    }
    @RequestMapping(value = "/arataProcentCarte/{id}" , method= RequestMethod.GET)
    public String afisareProcentCarte(@PathVariable("id") long id) {
        try {
            Carte carte = cs.getCarteById(id);
            if (carte != null) {
                String s = "Cartea " + carte.getTitlu() + " este citita in procent de " + carte.getProcent();
                return s;
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }

        return "Cartea nu a fost gasita";
    }

    @PostMapping("/modificareTitlu/{id}/{titluNou}")
    public String modificareTitlu(@PathVariable("id") long id,@PathVariable("titluNou") String titluNou)
    {
        try {
            Carte carte = cs.getCarteById(id);
            if (carte != null) {

                cs.modificareTitlu(carte, titluNou);
                return "Modificari efectuate";
            }
        }catch(RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }

        return "Cartea nu a fost gasita";
    }

    @PostMapping("/modificareAutor/{id}/{autorNou}")
    public String modificareAutor(@PathVariable("id") long id,@PathVariable("autorNou") String autorNou)
    {
        try {
            Carte carte = cs.getCarteById(id);
            if (carte != null) {

                cs.modificareAutor(carte, autorNou);
                return "Modificari efectuate";
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }
        return "Cartea nu a fost gasita";
    }

    @PostMapping("/modificareNrPagini/{id}/{pagini}")
    public String modificareNrPagini(@PathVariable("id") long id,@PathVariable("pagini") int pagini)
    {
        try {
            Carte carte = cs.getCarteById(id);
            if (carte != null) {

                cs.modificareNrPagini(carte, pagini);
                return "Modificari efectuate";
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }
        return "Cartea nu a fost gasita";
    }


    @PostMapping("/modificareNrCitite/{id}/{pagini}")
    public String modificareNrCitite(@PathVariable("id") long id,@PathVariable("pagini") int pagini)
    {
        try {
            Carte carte = cs.getCarteById(id);
            if (carte != null) {

                cs.modificaPaginiCitite(carte, pagini);
                return "Modificari efectuate";
            }
        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }

         return "Cartea nu a fost gasita";
    }

    @DeleteMapping("/stergeCarteId/{id}")
    @Transactional
    public String stergeCarteDupaId(@PathVariable("id") long id)
    {
        try{
            Carte carte= cs.getCarteById(id);
            cs.deleteByID(id);
            return "Cartea a fost stearsa!";
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Cartea nu a fost gasita";
        }

    }

    @PostMapping("/aduagaProgresCarte/{id}/{pagini}")
    public  String adaugaProgresPentruCarte(@PathVariable("id") long id,@PathVariable("pagini") int pagini)
    {
        try{
            Carte carte =cs.getCarteById(id);
            LoginController.setUtilizatorul(carte.getUtilizator());
            boolean reusit=cs.adaugaProgres(carte,pagini);
            if(reusit)
                return "Progres adaugat!";
            return "Progresul NU a fost adaugat!";

        }catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            return "Progresul NU a fost adaugat!";
        }
    }

}
