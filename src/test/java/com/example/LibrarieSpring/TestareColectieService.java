package com.example.LibrarieSpring;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestareColectieService {


    @Autowired
    public ColectieService colectieService;

    @Autowired
    private UtilizatorService us;
    @Autowired
    private CarteService cs;

    @Test
    @Order(1)
    public void aduagaDate()
    {
        Utilizator utilizator=new Utilizator("admin@gmail.com","admin");
        us.adauaga(utilizator);
        LoginController.setUtilizatorul(utilizator);
        Carte carte=new Carte("Titlu","Autor",357);
        cs.adaugaCarte(carte);
    }
    @Test
    @Transactional
    @Order(2)
    public void colectieBuna()
    {
        List<Utilizator> utilizatori=us.getAllutilizatori();

        Utilizator utilizator=utilizatori.get(0);

        LoginController.setUtilizatorul(utilizator);
        List<Colectie> colectii=colectieService.getColectii();
        int i=colectii.size()+1;

        Colectie colectieNoua=new Colectie("Istorie");

        boolean reusit=colectieService.adaugaColectie(colectieNoua);
        colectii=colectieService.getColectii();
        int j=colectii.size();

        Colectie colectiaAdaugata=colectieService.cautaColectieUtilizatorNume(utilizator,"Istorie");
        colectieService.stergeColectieById(colectiaAdaugata.getId());

        int l= colectieService.getColectii().size();

        assertThat(reusit).isTrue();
        assertThat(i).isEqualTo(j);
        j=j-1;
        assertThat(j).isEqualTo(l);

    }

    @Test
    @Transactional
    @Order(3)
    public void colectieEsec()
    {
        List<Utilizator> utilizatori=us.getAllutilizatori();

        Utilizator utilizator=utilizatori.get(0);
        Colectie colectieNoua=new Colectie("Istorie");

        boolean reusit=colectieService.adaugaColectie(colectieNoua);
        boolean reusit1=colectieService.adaugaColectie(colectieNoua);

        Colectie colectiaAdaugata=colectieService.cautaColectieUtilizatorNume(utilizator,"Istorie");
        colectieService.stergeColectieById(colectiaAdaugata.getId());

        assertThat(reusit).isTrue();
        assertThat(reusit1).isFalse();

    }

    @Test
    @Order(4)
    @Transactional
    public void stergeDate()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        us.stergeUtilizatorId(utilizator.getId());
        LoginController.setUtilizatorul(utilizator);
        Carte carte=cs.getByTitluUtilizator("Titlu",LoginController.getUtilizatorul());
        cs.deleteByID(carte.getId());
    }

}
