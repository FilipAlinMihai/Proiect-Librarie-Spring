package com.example.LibrarieSpring.service;


import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.ProvocareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ProvocareService {

    @Autowired
    private UtilizatorService us;
    @Autowired
    private ProvocareRepository pr;

    public void adauga(Provocare provocare)
    {
        Utilizator utilizator=provocare.getUtilizator();
        Utilizator utilizator1=us.getPrimulUtilizatorByEmail(utilizator.getEmail());
        if(utilizator1!=null)
            pr.save(provocare);
    }

    public void stergeDupaId(Provocare provocare)
    {
        long id=provocare.getId();
        pr.deleteById1(id);
    }

    public List<Provocare> cautaToateProvocarile()
    {
        return  pr.findAll();
    }

    public Optional<Provocare> cautaProvocareDupaId(long id)
    {
        Optional<Provocare> provocare=pr.findById(id);
        if(provocare.isPresent())
            return provocare;
        else throw new RuntimeException("Nu am gasit provocarea cu acest id: " + id);
    }

    public List<Provocare> cautaProvocarileUnuiUtilizator(Utilizator utilizator)
    {
        return pr.findByUtilizator(utilizator);
    }

    public void stergeProvocare(Provocare provocare)
    {
        pr.deleteById1(provocare.getId());
    }

    public String situatieProvocare(Provocare provocare)
    {

        if(provocare.amReusit())
            return "Provocare indeplinita. Felicitari!";
        else if(provocare.termenValid())
            return "Mai ai de citit "+(provocare.getPagini()- provocare.getProgres())+" pagini pana in data de "+provocare.getDataFinal();
        else
            return "Termenul limita a expirat!";
    }

    public void modificaNume(Provocare provocare,String nume)
    {
        provocare.setNume(nume);
        pr.save(provocare);
    }

}
