package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Colectie;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import com.example.LibrarieSpring.repository.ColectieRepository;
import com.example.LibrarieSpring.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository ur;

    @Autowired
    private CarteRepository cr;

    @Autowired
    private ColectieRepository colr;

    public void adauaga(Utilizator utilizator)
    {
        ur.saveAndFlush(utilizator);
    }

    public List<Utilizator> getAllUtilizatori()
    {
        List<Utilizator> utilizatori=ur.findAll();

        return utilizatori;
    }

    public Utilizator getPrimulUtilizatorByEmail(String email)
    {

        Utilizator u=ur.findFirstByEmail( email);
        return u;
    }

    public Utilizator getUtilizatorById(long id)
    {
        return ur.findById(id);
    }

    public List<Utilizator> getAllutilizatori()
    {
        return ur.findAll();
    }

    public void stergeUtilizatorId(long id)
    {
        ur.deleteById1(id);
    }


    public String procesareDateUtilizator(Utilizator utilizator)
    {
        String s="";
        List<Carte> cartiUtilizator= cr.findByUtilizator(utilizator);
        List<Colectie> colectiiUtilizator=colr.findByUtilizator(utilizator);
        int numarCarti=cartiUtilizator.size();
        int numarColectii=colectiiUtilizator.size();
        int numarpagini=0;
        int totalpagini=0;
        for(Carte c:cartiUtilizator)
        {
            numarpagini=numarpagini+c.getNrcitite();
            totalpagini=totalpagini+c.getNrpagini();
        }

        s=String.format("Utilizatorul cu adresa de email %s are :<br> " +
                " **** Un total de %d carti <br>  ****  El a citit %d pagini dintrun total de %d pagini <br> " +
                " **** Are %d colectii !",utilizator.getEmail(),numarCarti,numarpagini,totalpagini,numarColectii);

        return s;
    }
}
