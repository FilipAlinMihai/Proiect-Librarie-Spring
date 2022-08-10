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

    public String situatieProvocareInUtilizator(Provocare provocare)
    {

        if(provocare.amReusit())
            return "Provocare indeplinita. Felicitari!";
        else if(provocare.termenValid())
            return "Mai ai de citit "+(provocare.getPagini()- provocare.getProgres())+" pagini pana in data de "+provocare.getDataFinal();
        else
            return "Termenul limita a expirat!";
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
                " **** A cititin in totalitate urmatoarele carti:<br><br>",utilizator.getEmail(),numarCarti,numarpagini,totalpagini,numarColectii);

        List<Carte> cartiFinalizate=cr.cartiFinalizate(utilizator);

        String s1="";
        for(Carte c:cartiFinalizate)
        {
            s1=s1+"################# "+c.getTitlu()+" scrisa de "+c.getAutor()+"<br>";
        }
        s=s+s1;

        String s3="";

        List<Provocare> provocari=pr.findByUtilizator(utilizator);
        s3=s3+"<br> **** Are "+provocari.size()+" provocari setate dintre care <br><br>";

        int finalizate=0,esuate=0,inDesfasurare=0;
        for(Provocare p:provocari)
        {
            if(this.situatieProvocareInUtilizator(p).compareTo("Provocare indeplinita. Felicitari!")==0)
                finalizate=finalizate+1;
            else if(this.situatieProvocareInUtilizator(p).compareTo("Termenul limita a expirat!")==0)
                esuate=esuate+1;
            else
                inDesfasurare=inDesfasurare+1;
        }
        s3=s3+"################# Finalizate: "+finalizate+"<br>";
        s3=s3+"################# In desfasurare: "+inDesfasurare+"<br>";
        s3=s3+"################# Esuate: "+esuate+"<br><br>";
        s=s+s3;
        return s;
    }

    public void modificaParola(Utilizator utilizator)
    {
        ur.save(utilizator);
    }
}
