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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
       boolean reusit= cs.adaugaCarte(carte);
       assertThat(reusit).isTrue();
    }

    @Test
    @Order(2)
    public void testColectie()
    {
        Colectie colectie =new Colectie("Preferate");

        assertThat(colectie.getNume()).isEqualTo("Preferate");

        colectie.setNume("Istorie");
        assertThat(colectie.getNume()).isEqualTo("Istorie");

    }


    @Test
    @Transactional
    @Order(3)
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
    @Order(4)
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
    @Order(5)
    public void testaruncaExceptie()
    {
        Utilizator utilizator=new Utilizator();
        assertThrows(RuntimeException.class,()->colectieService.cautaColectieDupaId(1111111));
        assertThrows(RuntimeException.class,()->colectieService.cautaColectieUtilizatorNume(utilizator,"Nume"));
    }

    @Test
    @Order(6)
    @Transactional
    public void stergeMultiple()
    {
        int i=us.getAllutilizatori().size();
        int j=cs.getAllBooks().size();
        int o=colectieService.getColectii().size();
        Utilizator utilizator=new Utilizator("persoana@gmail.com","parola");
        us.adauaga(utilizator);
        Utilizator utilizator1=us.getPrimulUtilizatorByEmail("persoana@gmail.com");

        Carte carte =new Carte("Titlul","Autorul",200,utilizator1);
        LoginController.setUtilizatorul(utilizator1);
        cs.adaugaCarte(carte);

        Colectie colectie=new Colectie("Numele Colectiei",utilizator1);
        colectieService.adaugaColectie(colectie);

        us.stergeUtilizatorId(utilizator1.getId());

        int l=us.getAllutilizatori().size();
        int h=cs.getAllBooks().size();
        int p=colectieService.getColectii().size();

        assertThat(i).isEqualTo(l);
        assertThat(j).isEqualTo(h);
        assertThat(o).isEqualTo(p);
    }

    @Test
    @Order(7)
    @Transactional
    public void stergeDate()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        us.stergeUtilizatorId(utilizator.getId());
    }

}
