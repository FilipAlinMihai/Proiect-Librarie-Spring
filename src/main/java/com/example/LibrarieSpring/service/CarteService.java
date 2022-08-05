package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    CarteRepository cr;

    public boolean adaugaCarte(Carte addBook)
    {

        String titlu=addBook.getTitlu();
        String autor=addBook.getAutor();
        int nr=addBook.getNrpagini();
        Carte carteDeAdaugat=new Carte(titlu,autor,nr,LoginController.utilizatorul);

        List<Carte> cartiDuplicate=cr.findByAutorAndTitluAndUtilizator(autor,titlu,LoginController.utilizatorul);

        if(cartiDuplicate.size()==0)
        {
            cr.save(carteDeAdaugat);
            return true;
        }
       return false;
    }

    public List<Carte> getAllBooks()
    {
        List<Carte> carti=cr.findAll();
        return carti;
    }

    public Optional<Carte> getBookById(long id)
    {
        Optional<Carte> cartea= cr.findById(id);
        return cartea;
    }

    public List<Carte> cautaCarteAutorTitluUtilizator(String autor, String titlu, Utilizator utilizator)
    {
        List<Carte> cartiGasite=cr.findByAutorAndTitluAndUtilizator(autor,titlu,utilizator);

        return cartiGasite;
    }

    public List<Carte> cautaCarteUtilizator(Utilizator utilizator)
    {
        List<Carte> cartiGasite=cr.findByUtilizator(utilizator);

        return cartiGasite;
    }

    public boolean adaugaProgres(Carte carte,int pagini)
    {
        List<Carte> carti=cr.findByAutorAndTitluAndUtilizator(carte.getAutor(),carte.getTitlu(), LoginController.utilizatorul);
        if(carti.size()==1)
        {
            Carte cartea =cr.findFirstByAutorAndTitluAndUtilizator(carte.getAutor(),carte.getTitlu(), LoginController.utilizatorul);
            cartea.setNrcitite(cartea.getNrcitite()+pagini);
            cr.save(cartea);
            return true;
        }
        return false;
    }


}
