package com.example.LibrarieSpring;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TesteCarteService {

    @Autowired
    private CarteService cs;

    @Autowired
    private UtilizatorService us;

    @Test
    @Transactional
    void adaugaCarte() {

        List<Carte> carti=cs.getAllBooks();
        int i=carti.size();
        i++;
        int l=0;
        Utilizator utilizator=us.getUtilizatorById(14);
        LoginController.setUtilizatorul(utilizator);
        Carte carteDeAdaugat=new Carte("Mos Goriot","Honore de Balzac",357);
        boolean adaugare=cs.adaugaCarte(carteDeAdaugat);


        List<Carte> carti2=cs.getAllBooks();
        l= carti2.size();
        Carte carte1=cs.getByTitluUtilizator("Mos Goriot",LoginController.getUtilizatorul());
        System.out.println(carte1.getId());
        cs.deleteByID(carte1.getId());

        assertTrue(adaugare);
        assertEquals(l,i);



    }

    @Test
    @Transactional
    void CautaDupaId() {

        Carte cartea =cs.getCarteById(2);

        assertThat(cartea).isNotNull();
        assertThat(cartea.getTitlu()).isEqualTo("Ferma animalelor");
        assertThat(cartea.getAutor()).isEqualTo("George Orwell");

    }


    @Test
    @Transactional
    void aduagaProgres() {

        Carte cartea =cs.getCarteById(8);

        LoginController.setUtilizatorul(us.getUtilizatorById(7));
        int i= cartea.getNrcitite();
        i++;
        boolean reusit=cs.adaugaProgres(cartea,1);
        int l=cartea.getNrcitite();

        assertThat(cartea).isNotNull();
        assertThat(reusit).isTrue();
        assertThat(i).isEqualTo(l);
    }

    @Test
    @Transactional
    void getAllBooks() {

        Carte cartea=new Carte("Mos Goriot","Honore de Balzac",357);

        Utilizator utilizator=us.getUtilizatorById(1);
        LoginController.setUtilizatorul(utilizator);

        int i=cs.getAllBooks().size();
        i++;
        boolean reusit=cs.adaugaCarte(cartea);
        int l=cs.getAllBooks().size();

        Carte carte1=cs.getByTitluUtilizator("Mos Goriot",utilizator);
        cs.deleteByID(carte1.getId());
        int j=i-1;
        int h=cs.getAllBooks().size();

        assertThat(reusit).isTrue();
        assertThat(i).isEqualTo(l);
        assertThat(j).isEqualTo(h);
    }


    @Test
    @Transactional
    void getBookById() {

        List<Carte> carti=cs.getAllBooks();
        Carte carte1=carti.get(0);

        Carte carte2=cs.getCarteById(carte1.getId());

        assertThat(carte1.getAutor()).isEqualTo(carte2.getAutor());
        assertThat(carte1.getNrcitite()).isEqualTo(carte2.getNrcitite());
        assertThat(carte1.getTitlu()).isEqualTo(carte2.getTitlu());
        assertThat(carte1.getId()).isEqualTo(carte2.getId());
        assertThat(carte1.getNrpagini()).isEqualTo(carte2.getNrpagini());
    }

    @Test
    @Transactional
    void adaugaCarteDublu() {


        Utilizator utilizator=us.getUtilizatorById(14);
        LoginController.setUtilizatorul(utilizator);
        Carte carteDeAdaugat=new Carte("Mos Goriot","Honore de Balzac",357);
        boolean adaugare=cs.adaugaCarte(carteDeAdaugat);
        boolean adaugare1=cs.adaugaCarte(carteDeAdaugat);
        Carte carte1=cs.getByTitluUtilizator("Mos Goriot",LoginController.getUtilizatorul());
        cs.deleteByID(carte1.getId());

        assertThat(adaugare).isTrue();
        assertThat(adaugare1).isFalse();
    }

    @Test
    @Transactional
    void getBookByTitluUtilizator() {

        List<Carte> carti=cs.getAllBooks();
        Carte carte1=carti.get(0);

        Carte carte2=cs.getByTitluUtilizator(carte1.getTitlu(),carte1.getUtilizator());

        assertThat(carte1.getAutor()).isEqualTo(carte2.getAutor());
        assertThat(carte1.getNrcitite()).isEqualTo(carte2.getNrcitite());
        assertThat(carte1.getTitlu()).isEqualTo(carte2.getTitlu());
        assertThat(carte1.getId()).isEqualTo(carte2.getId());
        assertThat(carte1.getNrpagini()).isEqualTo(carte2.getNrpagini());
    }

    @Test
    void aduagaProgresPreaMare() {

        List<Carte> carti=cs.getAllBooks();
        Carte cartea=carti.get(0);

        LoginController.setUtilizatorul(us.getUtilizatorById(carti.get(0).getUtilizator().getId()));
        boolean reusit=cs.adaugaProgres(cartea,1000);

        carti=cs.getAllBooks();
        Carte cartea2=carti.get(0);
        int l=cartea2.getNrcitite();


        assertThat(cartea).isNotNull();
        assertThat(reusit).isTrue();
        assertThat(cartea.getNrpagini()).isEqualTo(l);
    }

}
