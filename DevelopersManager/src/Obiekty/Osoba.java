package Obiekty;
import Wyjatki.*;
import Utilites.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Osoba implements Serializable {
    String imie;
    String nazwisko;
    String pesel;
    String adres;
    LocalDate dataUrodzenia;

    List <Umowa> listaUmow;

    public Osoba(String imie, String nazwisko, String pesel, String adres, int dzien, int miesiac, int rok){
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.adres = adres;
        this.dataUrodzenia = LocalDate.of(rok,dzien,miesiac);
        this.listaUmow = new ArrayList<>();
    }


    public boolean czyPrzekroczonyLimit(){
        return listaUmow.size() >= 5 ;
    }

    public void dodajMieszkanie(Pomieszczenie pomieszczenie, int dlugoscNajmu) throws ProblematicTenantException, TooMuchRoomsException {
        if(!czyPrzekroczonoZadluzenia()) {
            throw new ProblematicTenantException();
        }
        if(czyPrzekroczonyLimit()) {
            throw new TooMuchRoomsException();
        }
        if(!czyMaszMieszkanie(pomieszczenie)) {
            mieszkanieCzyMiejsceDodanie(pomieszczenie, dlugoscNajmu);
        }
        else{
            for (int i = 0; i < listaUmow.size() ; i++) {
                if(listaUmow.get(i).pomieszczenie == pomieszczenie){
                    listaUmow.get(i).odnowUmowe(pomieszczenie, Zegar.INSTANCE.getCzasGlobalny(),dlugoscNajmu);
                }
            }
        }
    }

    public void mieszkanieCzyMiejsceDodanie(Pomieszczenie pomieszczenie, int dlugoscNajmu){
        if (pomieszczenie.getClass() == Mieszkanie.class) {
            listaUmow.add(new UmowaMieszkanie((Mieszkanie)pomieszczenie, Zegar.INSTANCE.getCzasGlobalny(), pesel, dlugoscNajmu));
            pomieszczenie.czyWynajete = true;
            System.out.println("WYNAJETE");
        } else if (pomieszczenie.getClass() == MiejsceParkingowe.class) {
            listaUmow.add(new UmowaMiejsceParkingowe((MiejsceParkingowe) pomieszczenie, Zegar.INSTANCE.getCzasGlobalny(), pesel, dlugoscNajmu));
            pomieszczenie.czyWynajete = true;
        }

    }

    public boolean czyPrzekroczonoZadluzenia(){
        int licznik = 0;
        if(!listaUmow.isEmpty()) {
            for (int i = 0; i < listaUmow.size(); i++) {
                    if (!listaUmow.get(i).czyNadalZadluzony(Zegar.INSTANCE.getCzasGlobalny())) {
                        licznik++;
                    }
            }
        }
        return licznik < 3;
    }

    public void zamelduj(Osoba os, Umowa umowa){
        ((UmowaMieszkanie) umowa).zamelduj(os);
    }

    public void wymelduj(Osoba os, Umowa umowa){
        ((UmowaMieszkanie) umowa).wymelduj(os);
    }

    public boolean czyMaszMieszkanie(Pomieszczenie pomieszczenie){
        int licznik = 0;
        for (int i = 0; i < listaUmow.size() ; i++) {
            if(listaUmow.get(i).getPomieszczenie() == pomieszczenie) {
               return true;
            }
        }
        return false;
    }

    public void dodajPrzedmiot(UmowaMiejsceParkingowe miejsceParkingowe, Przedmiot przedmiot) throws TooManyThingsException{
        if(!miejsceParkingowe.czySieZmiesci(przedmiot)){
            throw new TooManyThingsException();
        }
        miejsceParkingowe.listaPrzedmiotow.put(przedmiot.idPrzedmiotu, przedmiot);
        miejsceParkingowe.dodajPrzedmiot(przedmiot);
    }


    public void usunPrzedmiot(UmowaMiejsceParkingowe miejsceParkingowe, Przedmiot przedmiot){
        miejsceParkingowe.usunPrzedmiot(przedmiot);
        ((MiejsceParkingowe) miejsceParkingowe.getPomieszczenie()).usunPrzedmiot(przedmiot);
    }

    public boolean czyNajemca(){
        return !listaUmow.isEmpty();
    }

    @Override
    public String toString() {
        return this.imie + " " + nazwisko;
    }

    public List<Umowa> getListaUmow(){
        return listaUmow;
    }


    public String getStringWszystkieDane(){
        return imie + " " + nazwisko + "\nPesel: " + pesel + "\nData urodzenia: " + dataUrodzenia + "\nAdres zamieszkania: " + adres;
    }


    public LocalDate dataNajpozniejszegoKoncaNajmu(){
        LocalDate max = listaUmow.get(0).koniecNajmu;
        for (int i = 1; i <listaUmow.size() ; i++) {
            if(listaUmow.get(i).koniecNajmu.isAfter(max)){
                max = listaUmow.get(i).koniecNajmu;
            }
        }
        return max;
    }
}
