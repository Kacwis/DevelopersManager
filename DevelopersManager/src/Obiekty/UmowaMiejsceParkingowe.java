package Obiekty;

import Utilites.Zegar;
import Wyjatki.TooManyThingsException;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

public class UmowaMiejsceParkingowe extends Umowa{

    HashMap<UUID,Przedmiot> listaPrzedmiotow;


    public UmowaMiejsceParkingowe(MiejsceParkingowe miejsceParkingowe, LocalDate poczatekNajmu, String pesel, int dlugoscNajmu){
        super(miejsceParkingowe, poczatekNajmu, pesel, dlugoscNajmu);
        this.listaPrzedmiotow = new HashMap<>();
    }


    public String toString(){
        return "Miejsce Parkingowe \n" + "   Poczatek najmu: " + poczatekNajmu.toString() + "\n   Koniec najmu: " + koniecNajmu.toString() + " \n   " + pomieszczenie.toString();
    }

    public Pomieszczenie getMiejsceParkingowe(){
        return pomieszczenie;
    }

    public boolean czySieZmiesci(Przedmiot przedmiot){
        return pomieszczenie.powierzchniaUzytkowa > przedmiot.getObjetosc();
    }

    public void dodajPrzedmiot(Przedmiot przedmiot){
        try {
            ((MiejsceParkingowe) this.pomieszczenie).dodajPrzedmiot(przedmiot);
        }
        catch(TooManyThingsException e){
            System.out.println(e.err());
        }
        listaPrzedmiotow.put(przedmiot.idPrzedmiotu,przedmiot);
    }

    public void usunPrzedmiot(Przedmiot przedmiot){
        if(listaPrzedmiotow.isEmpty())
            return;
        listaPrzedmiotow.remove(przedmiot.idPrzedmiotu);
    }

    public HashMap<UUID, Przedmiot> getListaPrzedmiotow() {
        return listaPrzedmiotow;
    }
}
