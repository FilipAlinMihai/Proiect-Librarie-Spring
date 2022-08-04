package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.SigninForm;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.UtilizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    public static Utilizator utilizatorul=new Utilizator();
    @Autowired
    UtilizatorService us;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginForm()
    {

        return "login";
    }

    @RequestMapping(value = "/login" , method=RequestMethod.POST)
    public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model)
    {
        String username=loginForm.getUsername();
        String password=loginForm.getPassword();
        Utilizator u=us.getPrimulUtilizatorByEmail(username);
        if(!(u ==null))
        if(u.getEmail().equals(username) && u.getParola().equals(password))
        {
            utilizatorul=u;
            return "home";
        }

        model.addAttribute("invalidCredentials",true);

        return "login";
    }

    @RequestMapping(value = "/inregistrare", method = RequestMethod.GET)
    public String getSigninForm()
    {

        return "inregistrare";
    }

    @RequestMapping(value = "/inregistrare" , method=RequestMethod.POST)
    public String inregistrare(@ModelAttribute(name="signinForm") SigninForm signinForm, Model model)
    {
        String email=signinForm.getEmail();
        String parola1=signinForm.getParola1();
        String parola2=signinForm.getParola2();

        if(parola2.compareTo(parola1)==0)
        {
            Utilizator u=us.getPrimulUtilizatorByEmail(email);
            if(u==null)
            {
                Utilizator u1=new Utilizator(email,parola1);
                us.adauaga(u1);
                utilizatorul=u1;
                return "home";
            }
        }




        model.addAttribute("invalidData",true);

        return "inregistrare";
    }

}
