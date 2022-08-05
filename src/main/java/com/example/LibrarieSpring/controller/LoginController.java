package com.example.LibrarieSpring.controller;

import com.example.LibrarieSpring.LoginForm;
import com.example.LibrarieSpring.SigninForm;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.service.LoginService;
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

    @Autowired
    LoginService ls;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginForm()
    {

        return "login";
    }

    @RequestMapping(value = "/login" , method=RequestMethod.POST)
    public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model)
    {
       if(ls.login(loginForm))
       {
           Utilizator u=us.getPrimulUtilizatorByEmail(loginForm.getUsername());
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
        if(ls.inregistrare(signinForm))
        {
            Utilizator u1=us.getPrimulUtilizatorByEmail(signinForm.getEmail());
            utilizatorul=u1;
            return "home";
        }

        model.addAttribute("invalidData",true);

        return "inregistrare";
    }

}
