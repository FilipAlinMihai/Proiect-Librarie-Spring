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
        boolean reusit= ls.inregistrare(new SigninForm("admin@gmail.com","admin","admin"));
        assertThat(reusit).isTrue();
    }

    @Test
    @Order(2)
    public void testProvocare()
    {
        Provocare provocare=new Provocare(100,10,"De citit");

        assertThat(provocare.getNume()).isEqualTo("De citit");
        assertThat(provocare.getPagini()).isEqualTo(100);
        assertThat(provocare.getZile()).isEqualTo(10);

        provocare.setNume("De citit 2");
        provocare.setPagini(200);
        provocare.setZile(20);

        assertThat(provocare.getNume()).isEqualTo("De citit 2");
        assertThat(provocare.getPagini()).isEqualTo(200);
        assertThat(provocare.getZile()).isEqualTo(20);
    }
    @Test
    @Order(3)
    @Transactional
    public  void modificaProvocare()
    {

        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        Provocare provocare =new Provocare(100,10,utilizator,"Provocare");
        ps.adauga(provocare);

        assertThat(provocare.getZile()).isEqualTo(10);
        assertThat(provocare.getPagini()).isEqualTo(100);

        Provocare provocare1=ps.cautaProvocarileUnuiUtilizator(utilizator).get(0);
        ps.modificaPagini(provocare1.getId(),110);
        ps.modificaZile(provocare1.getId(),11);

        assertThat(provocare.getZile()).isEqualTo(11);
        assertThat(provocare.getPagini()).isEqualTo(110);

        List<Provocare> provocari=ps.cautaProvocarileUnuiUtilizator(utilizator);
        for(Provocare p:provocari)
        {
            ps.stergeDupaId(p);
        }

    }

    @Test
    @Transactional
    @Order(4)
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
    @Order(5)
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
    @Order(6)
    @Transactional
    public void verificareSituatieProvocare()
    {
        Utilizator utilizator=us.getPrimulUtilizatorByEmail("admin@gmail.com");
        Provocare p1 =new Provocare(100,0,utilizator,"Provocare1");
        Provocare p2 =new Provocare(0,100,utilizator,"Provocare2");
        Provocare p3 =new Provocare(100,100,utilizator,"Provocare3");
        ps.adauga(p1);
        ps.adauga(p2);
        ps.adauga(p3);

        List<Provocare> provocari=ps.cautaProvocarileUnuiUtilizator(utilizator);
        for(Provocare p:provocari)
        {
            if(p.getNume().compareTo("Provocare1")==0)
                assertThat(ps.situatieProvocare(p)).isEqualTo("Termenul limita a expirat!");
            else
            if(p.getNume().compareTo("Provocare2")==0)
                assertThat(ps.situatieProvocare(p)).isEqualTo("Provocare indeplinita. Felicitari!");
            else
            if(p.getNume().compareTo("Provocare3")==0)
                assertThat(ps.situatieProvocare(p)).isEqualTo("Mai ai de citit "+(p.getPagini()- p.getProgres())+" pagini pana in data de "+p.getDataFinal());

            ps.stergeDupaId(p);
        }

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
