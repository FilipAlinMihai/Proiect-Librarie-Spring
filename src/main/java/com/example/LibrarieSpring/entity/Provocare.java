package com.example.LibrarieSpring.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Provocare {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    private int pagini;
    private int zile;
    private int progres;

    private Date dataStart;

    @ManyToOne
    @JoinColumn(name = "UTILIZATOR_ID", referencedColumnName = "ID")
    private Utilizator utilizator;

    private String nume;


    public Provocare()
    {

    }

    public Provocare(int pagini, int zile, String nume)
    {
        this.dataStart=new Date();
        this.pagini=pagini;
        this.zile=zile;
        progres=0;
        this.nume=nume;

    }

    public Provocare(int pagini, int zile,Utilizator utilizator,String nume)
    {
        this.dataStart=new Date();
        this.pagini=pagini;
        this.zile=zile;
        progres=0;
        this.utilizator=utilizator;
        this.nume=nume;
    }

    public int getPagini() {
        return pagini;
    }

    public void setPagini(int pagini) {
        this.pagini = pagini;
    }

    public int getZile() {
        return zile;
    }

    public void setZile(int zile) {
        this.zile = zile;
    }

    public Date getDataStart() {
        return dataStart;
    }

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Date getDataFinal()
    {
        Calendar c = Calendar.getInstance();
        try{

            c.setTime(dataStart);
        }catch(Exception e){
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_MONTH, zile);

        Date dataFinal=new Date();
        dataFinal=c.getTime();

        return  dataFinal;
    }

    public boolean termenValid()
    {
        Date dataCurenta=new Date();
        if(this.getDataFinal().compareTo(dataCurenta) > 0)
            return true;
        return false;
    }

    public void adaugaProgres(int p)
    {
        progres=progres+p;
    }
    public boolean amReusit()
    {
        return progres>=pagini;
    }
}
