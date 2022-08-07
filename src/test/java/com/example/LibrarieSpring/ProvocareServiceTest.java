package com.example.LibrarieSpring;

import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.LoginService;
import com.example.LibrarieSpring.service.ProvocareService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProvocareServiceTest {

    @Autowired
    private ProvocareService ps;

    @Autowired
    private CarteService cs;

    @Autowired
    private UtilizatorService us;

    @Autowired
    private LoginService ls;

    @Test
    @Order(1)
    public void aduagaDate()
    {
        Utilizator utilizator=new Utilizator("admin@gmail.com","admin");
        us.adauaga(utilizator);
    }

    @Test
    @Transactional
    @Order(2)
    public void adaugaProvocare()
    {
        int i=ps.cautaToateProvocarile().size()+1;
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        Provocare provocare =new Provocare(100,10,utilizator,"Provocare");
        ps.adauga(provocare);
        int j=ps.cautaToateProvocarile().size();
        List<Provocare> provocari=ps.cautaProvocarileUnuiUtilizator(utilizator);
        for(Provocare p:provocari)
        {
            ps.stergeDupaId(p);
        }

        int l=ps.cautaToateProvocarile().size();

        assertThat(i).isEqualTo(j);
        i=i-1;
        assertThat(l).isEqualTo(i);


    }

    @Test
    @Order(3)
    @Transactional
    public void adaugaProvocareEsec()
    {
        int i=ps.cautaToateProvocarile().size();
        Utilizator utilizator=new Utilizator("ovidiu@gmail.com","Parola");
        Provocare provocare =new Provocare(100,10,utilizator,"Provocare");
        ps.adauga(provocare);
        int j=ps.cautaToateProvocarile().size();

        assertThat(i).isEqualTo(j);

    }

    @Test
    @Order(4)
    @Transactional
    public void stergeDate()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        us.stergeUtilizatorId(utilizator.getId());
    }
}
