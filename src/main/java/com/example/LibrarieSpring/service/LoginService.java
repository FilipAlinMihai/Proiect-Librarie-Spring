package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.SigninForm;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {

    @Autowired
    UtilizatorRepository ur;

    @Autowired
    UtilizatorService us;


    public boolean login(LoginForm loginForm)
    {
        String username=loginForm.getUsername();
        String password=loginForm.getPassword();
        Utilizator u=us.getPrimulUtilizatorByEmail(username);
        if(!(u ==null))
            if(u.getEmail().equals(username) && u.getParola().equals(password))
            {
                return true;
            }
        return false;
    }

    public boolean inregistrare(SigninForm signinForm)
    {
        String email=signinForm.getEmail();
        String parola1=signinForm.getParola1();
        String parola2=signinForm.getParola2();

        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches())
        if(parola2.compareTo(parola1)==0)
        {
            Utilizator u=us.getPrimulUtilizatorByEmail(email);
            if(u==null)
            {
                Utilizator u1=new Utilizator(email,parola1);
                us.adauaga(u1);
                return  true;
            }
        }
        return false;
    }


}
