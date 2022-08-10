package com.example.LibrarieSpring;


import com.example.LibrarieSpring.entity.Utilizator;
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
public class TestareUtilizatorService {

    @Autowired
    UtilizatorService us;

    @Test
    @Order(1)
    public void aduagaDate()
    {
        Utilizator utilizator=new Utilizator("admin@gmail.com","admin");
        us.adauaga(utilizator);
    }

    @Test
    @Order(2)
    public void testareUtilizator()
    {
        Utilizator utilizator= new Utilizator("email@gmail.com","parola");

        assertThat(utilizator.getParola()).isEqualTo("parola");
        assertThat(utilizator.getEmail()).isEqualTo("email@gmail.com");

        utilizator.setParola("Parola23");

        assertThat(utilizator.getParola()).isEqualTo("Parola23");
    }
    @Test
    @Order(3)
    public void testModificareUtilizator()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");

        assertThat(utilizator.getParola()).isEqualTo("admin");
        utilizator.setParola("ADMIN");
        us.modificaParola(utilizator);
        Utilizator utilizator1=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        assertThat(utilizator1.getParola()).isEqualTo("ADMIN");
    }


    @Test
    @Transactional
    @Order(4)
    public void adaugautilizatorBun()
    {
        int i=us.getAllutilizatori().size()+1;
        Utilizator utilizatorNou= new Utilizator("nume@gmail.com","paorla");
        us.adauaga(utilizatorNou);
        int j=us.getAllutilizatori().size();
        Utilizator utilizator1=us.getPrimulUtilizatorByEmail("nume@gmail.com");
        us.stergeUtilizatorId(utilizator1.getId());
        int l=us.getAllutilizatori().size();

        assertThat(i).isEqualTo(j);
        i=i-1;
        assertThat(i).isEqualTo(l);
    }

    @Test
    @Transactional
    @Order(5)
    public void adaugautilizatorEsec()
    {
        int i=us.getAllutilizatori().size()+1;
        Utilizator utilizatorNou= new Utilizator("nume@gmail.com","paorla");
        us.adauaga(utilizatorNou);
        us.adauaga(utilizatorNou);
        us.adauaga(utilizatorNou);
        int j=us.getAllutilizatori().size();
        Utilizator utilizator1=us.getPrimulUtilizatorByEmail("nume@gmail.com");
        us.stergeUtilizatorId(utilizator1.getId());
        int l=us.getAllutilizatori().size();

        assertThat(i).isEqualTo(j);
        i=i-1;
        assertThat(i).isEqualTo(l);
    }

    @Test
    @Transactional
    @Order(6)
    public void cautaDupaEmail()
    {
        List<Utilizator> utilizatori=us.getAllUtilizatori();
        Utilizator utilizator=utilizatori.get(0);

        Utilizator utilizatorGasit=us.getPrimulUtilizatorByEmail(utilizator.getEmail());

        assertThat(utilizatorGasit.getId()).isEqualTo(utilizator.getId());
        assertThat(utilizatorGasit.getEmail()).isEqualTo(utilizator.getEmail());
        assertThat(utilizatorGasit.getParola()).isEqualTo(utilizator.getParola());
    }

    @Test
    @Transactional
    @Order(7)
    public void cautaDupaId()
    {
        List<Utilizator> utilizatori=us.getAllUtilizatori();
        Utilizator utilizator=utilizatori.get(0);

        Utilizator utilizatorGasit=us.getUtilizatorById(utilizator.getId());

        assertThat(utilizatorGasit.getId()).isEqualTo(utilizator.getId());
        assertThat(utilizatorGasit.getEmail()).isEqualTo(utilizator.getEmail());
        assertThat(utilizatorGasit.getParola()).isEqualTo(utilizator.getParola());
    }
    @Test
    @Order(8)
    public void testExceptie()
    {
        assertThrows(RuntimeException.class,()->us.getUtilizatorById(1111111));
    }
    @Test
    @Order(9)
    @Transactional
    public void stergeDate()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        us.stergeUtilizatorId(utilizator.getId());
    }

}
