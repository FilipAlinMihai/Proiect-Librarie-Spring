package com.example.LibrarieSpring;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.ColectieService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestareColectieService {


    @Autowired
    public ColectieService colectieService;

    @Autowired
    private UtilizatorService us;

    @Test
    @Transactional
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
}
