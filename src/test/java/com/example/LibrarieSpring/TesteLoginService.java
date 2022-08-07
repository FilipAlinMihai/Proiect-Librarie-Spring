package com.example.LibrarieSpring;

import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.CarteService;
import com.example.LibrarieSpring.service.LoginService;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteLoginService {
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
    public void loginEsuat()
    {
        LoginForm loginForm=new LoginForm("aaaaaaaa","bbbbb");
        boolean reusit=ls.login(loginForm);

        assertThat(reusit).isFalse();
    }

    @Test
    @Transactional
    @Order(3)
    public void loginReusit()
    {
        List<Utilizator> utilizatori=us.getAllutilizatori();
        LoginForm loginForm=new LoginForm(utilizatori.get(0).getEmail(),utilizatori.get(0).getParola());
        boolean reusit=ls.login(loginForm);

        assertThat(reusit).isTrue();
    }


    @Test
    @Transactional
    @Order(4)
    public void inregistraerEsuata()
    {
        List<Utilizator> utilizatori=us.getAllutilizatori();
        SigninForm signinForm=new SigninForm(utilizatori.get(0).getEmail(),utilizatori.get(0).getParola(),utilizatori.get(0).getParola());
        boolean reusit=ls.inregistrare(signinForm);

        assertThat(reusit).isFalse();
    }


    @Test
    @Transactional
    @Order(5)
    public void inregistraerReusita()
    {
        List<Utilizator> utilizatori=us.getAllutilizatori();
        int i=utilizatori.size()+1;
        SigninForm signinForm=new SigninForm("nume@gmail.com","parola","parola");
        boolean reusit=ls.inregistrare(signinForm);

        Utilizator utilizator=us.getPrimulUtilizatorByEmail("nume@gmail.com");
        utilizatori=us.getAllutilizatori();
        int j= utilizatori.size();
        us.stergeUtilizatorId(utilizator.getId());

        assertThat(reusit).isTrue();
        assertThat(i).isEqualTo(j);
    }

    @Test
    @Transactional
    @Order(6)
    public void inregistraerEmilInvalid()
    {

        SigninForm signinForm1=new SigninForm("numegmail.com","parola","parola");
        SigninForm signinForm2=new SigninForm("nume@gmail.","parola","parola");
        SigninForm signinForm3=new SigninForm("nume@gmailcom","parola","parola");
        boolean reusit1=ls.inregistrare(signinForm1);
        boolean reusit2=ls.inregistrare(signinForm2);
        boolean reusit3=ls.inregistrare(signinForm3);

        assertThat(reusit1).isFalse();
        assertThat(reusit2).isFalse();
        assertThat(reusit3).isFalse();
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
