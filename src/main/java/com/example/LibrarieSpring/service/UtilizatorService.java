package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.entity.*;
import com.example.LibrarieSpring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository ur;

    @Autowired
    private CarteRepository cr;
    @Autowired
    private ElementColectieRepository ecr;

    @Autowired
    private ColectieRepository colr;

    @Autowired
    private ProvocareRepository pr;
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
        Utilizator utilizator=ur.findById(id);
        if(utilizator!=null)
            return utilizator;
            else throw new RuntimeException("Nu am gasit un utilizator cu acest id: " + id);
    }

    public List<Utilizator> getAllutilizatori()
    {
        return ur.findAll();
    }

    @Transactional
    public void stergeUtilizatorId(long id)
    {

        Utilizator utilizator=ur.findById(id);
        List<Carte> carti = cr.findByUtilizator(utilizator);
        for(Carte c:carti)
            cr.deleteById1(c.getId());

        List<Colectie> colectii=colr.findByUtilizator(utilizator);
        for(Colectie c:colectii)
        {
            colr.deleteById1(c.getId());
            List<ElementColectie> elemente=ecr.findByColectie(c);
            for(ElementColectie e:elemente)
            {
                ecr.deleteById1(e.getId());
            }
        }

        List<Provocare> provocari=pr.findByUtilizator(utilizator);
        for(Provocare p:provocari)
            pr.deleteById1(p.getId());

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
                " **** Un total de %d carti <br>  ****  El a citit %d pagini dintr-un total de %d pagini <br> " +
                " **** Are %d colectii !<br>" +
                " **** A cititin in totalitate urmatoarele carti:<br>",utilizator.getEmail(),numarCarti,numarpagini,totalpagini,numarColectii);

        List<Carte> cartiFinalizate=cr.cartiFinalizate(utilizator);

        String s1="";
        for(Carte c:cartiFinalizate)
        {
            s1=s1+"################# "+c.getTitlu()+" scrisa de "+c.getAutor()+"<br>";
        }
        s=s+s1;
        return s;
    }

    public void modificaParola(Utilizator utilizator)
    {
        ur.save(utilizator);
    }
}
