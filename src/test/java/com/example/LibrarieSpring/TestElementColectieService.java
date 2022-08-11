package com.example.LibrarieSpring;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.*;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.ElementColectieRepository;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.ElementColectieService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestElementColectieService {
    @Autowired
    private  UtilizatorService us;
    @Autowired
    private  ElementColectieService ecs;
    @Autowired
    private  CarteService cs;
    @Autowired
    private  ColectieService cols;

    @Test
    @Order(1)
    public  void aduagaDate()
    {
        Utilizator utilizator=new Utilizator("admin@gmail.com","admin");
        us.adauaga(utilizator);
        LoginController.setUtilizatorul(utilizator);
        Carte carte=new Carte("Titlu","Autor",357);
        boolean reusit=cs.adaugaCarte(carte);
        Colectie colectie=new Colectie("colectieAdmin",LoginController.getUtilizatorul());
        boolean reusit1=cols.adaugaColectie(colectie);

        assertThat(reusit).isTrue();
        assertThat(reusit1).isTrue();
    }

    @Test
    @Order(2)
    public void testElementColectie()
    {
        Colectie colectie =new Colectie("Preferate");
        Carte carte =new Carte("Titlu","Autor",100);
        ElementColectie elementColectie=new ElementColectie(carte,colectie);

        assertThat(elementColectie.getColectie().getNume()).isEqualTo("Preferate");
        assertThat(elementColectie.getCarte().getTitlu()).isEqualTo("Titlu");
        assertThat(elementColectie.getCarte().getAutor()).isEqualTo("Autor");
        assertThat(elementColectie.getCarte().getNrpagini()).isEqualTo(100);

    }

    @Test
    @Transactional
    @Order(3)
    public void AdaugaElementColectie()
    {
        List<Colectie> colectii=cols.getColectii();
        Colectie primaColectie=colectii.get(0);
        Utilizator utilizatorColectie=primaColectie.getUtilizator();
        int i=ecs.toateElementele().size()+1;
        Carte carteNoua=new Carte("Mos Goriot","Honore de Balzac",357);
        LoginController.setUtilizatorul(utilizatorColectie);
        boolean adaugare=cs.adaugaCarte(carteNoua);

        Carte carteNoua1=cs.getByTitluUtilizator("Mos Goriot",utilizatorColectie);
        ElementDeAdaugat elementDeAdaugat=new ElementDeAdaugat(primaColectie.getNume(),carteNoua.getTitlu(),carteNoua.getAutor());
        boolean adaugareElement=ecs.aduagaElementColectie(elementDeAdaugat);
        int j=ecs.toateElementele().size();
        ElementColectie elemcol=ecs.cautaElementByCarteColectie(carteNoua1,primaColectie);

        cs.deleteByID(carteNoua1.getId());
        ecs.stergeElementColectie(elemcol.getId());
        int l=ecs.toateElementele().size();

        assertThat(adaugare).isTrue();
        assertThat(adaugareElement).isTrue();
        assertThat(i).isEqualTo(j);
        i=i-1;
        assertThat(i).isEqualTo(l);
    }

    @Test
    @Transactional
    @Order(4)
    public void EsecAdaugare()
    {
        List<Colectie> colectii=cols.getColectii();
        Colectie primaColectie=colectii.get(0);
        Utilizator utilizatorColectie=primaColectie.getUtilizator();

        Carte carteNoua=new Carte("Mos Goriot","Honore de Balzac",357);
        LoginController.setUtilizatorul(utilizatorColectie);

        ElementDeAdaugat elementDeAdaugat=new ElementDeAdaugat(primaColectie.getNume(),carteNoua.getTitlu(),carteNoua.getAutor());
        boolean adaugareElement=ecs.aduagaElementColectie(elementDeAdaugat);

        assertThat(adaugareElement).isFalse();

    }


    @Test
    @Transactional
    @Order(5)
    public void EsecAdaugare2()
    {
        List<Colectie> colectii=cols.getColectii();
        Colectie primaColectie=colectii.get(0);
        Utilizator utilizatorColectie=primaColectie.getUtilizator();

        LoginController.setUtilizatorul(utilizatorColectie);

        Carte carteNoua=new Carte("Mos Goriot","Honore de Balzac",357);

        boolean adaugare=cs.adaugaCarte(carteNoua);

        ElementDeAdaugat elementDeAdaugat=new ElementDeAdaugat("Colectie",carteNoua.getTitlu(),carteNoua.getAutor());
        boolean adaugareElement=ecs.aduagaElementColectie(elementDeAdaugat);
        Carte carteNoua1=cs.getByTitluUtilizator("Mos Goriot",utilizatorColectie);
        cs.deleteByID(carteNoua1.getId());
        assertThat(adaugare).isTrue();
        assertThat(adaugareElement).isFalse();

    }



    @Test
    @Order(6)
    @Transactional
    public  void stergeDate()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        us.stergeUtilizatorId(utilizator.getId());
        LoginController.setUtilizatorul(utilizator);

    }
}
