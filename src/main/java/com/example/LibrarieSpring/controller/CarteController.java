package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarteController {

    @Autowired
    UtilizatorService us;

    @Autowired
    CarteService cs;

    @Autowired
    CarteRepository cr;

    @RequestMapping(value = "/arataCarte" , method= RequestMethod.GET)
    public List<Carte> afisareToateCartile() {
        return cs.getAllBooks();

    }

    @RequestMapping(value = "/afisareCaretId/{id}" , method=RequestMethod.GET)
    public Carte afisareCarteDupaId(@PathVariable long id) {
        return cs.getCarteById(id);
    }

    @GetMapping(value = "/adaugareFaraFormular/{titlu}/{autor}/{pagini}/{id}" )
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
            Carte carte=cs.getCarteById(id);
            String s=carte.getTitlu()+" citita in procent de "+carte.getProcent();
            return s;
    }


}
