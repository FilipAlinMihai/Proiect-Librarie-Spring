package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizatorService {

    @Autowired
    UtilizatorRepository ur;

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
}
