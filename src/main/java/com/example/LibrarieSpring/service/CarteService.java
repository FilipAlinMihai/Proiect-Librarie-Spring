package com.example.LibrarieSpring.service;

import com.example.LibrarieSpring.controller.LoginController;
import com.example.LibrarieSpring.entity.Carte;
import com.example.LibrarieSpring.entity.Provocare;
import com.example.LibrarieSpring.entity.Utilizator;
import com.example.LibrarieSpring.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    private CarteRepository cr;

    @Autowired
    private ProvocareService ps;

    public boolean adaugaCarte(Carte addBook)
    {

        String titlu=addBook.getTitlu();
        String autor=addBook.getAutor();
        int nr=addBook.getNrpagini();
        Carte carteDeAdaugat=new Carte(titlu,autor,nr,LoginController.getUtilizatorul());

        List<Carte> cartiDuplicate=cr.findByAutorAndTitluAndUtilizator(autor,titlu,LoginController.getUtilizatorul());

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

    public Carte getCarteById(long id)
    {
        return cr.cautaDupaId(id);
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
        List<Carte> carti=cr.findByAutorAndTitluAndUtilizator(carte.getAutor(),carte.getTitlu(), LoginController.getUtilizatorul());
        if(carti.size()==1) {
            Carte cartea = cr.findFirstByAutorAndTitluAndUtilizator(carte.getAutor(), carte.getTitlu(), LoginController.getUtilizatorul());
            if ((cartea.getNrcitite() + pagini) > cartea.getNrpagini()) {
                cartea.setNrcitite(cartea.getNrpagini());
            } else {
                cartea.setNrcitite(cartea.getNrcitite() + pagini);
            }
            List<Provocare> provocari = ps.cautaProvocarileUnuiUtilizator(LoginController.getUtilizatorul());
            for (Provocare p : provocari)
            {
                p.adaugaProgres(pagini);
            }
            cr.save(cartea);
            return true;
        }

        return false;
    }

    public boolean deleteByID(long id) {
        cr.deleteById1(id);
        return true;
    }
    public boolean stergeDupaId(long id) {
        cr.stergeDupaId(id);
        return true;
    }

    public Carte getByTitluUtilizator(String titlu,Utilizator utilizator)
    {
        return cr.findByTitluAndUtilizator(titlu,utilizator);
    }

    public void stergeDupaTitluUtilizator(String titlu,Utilizator utilizator)
    {
        cr.deleteByTitluAndUtilizator(titlu,utilizator);
    }

    public void modificaPaginiCitite(Carte carte,int pagini)
    {
        carte.setNrcitite(pagini);
        cr.save(carte);
    }


    public String getProcent(Carte carte)
    {
        return carte.getProcent();
    }

}
