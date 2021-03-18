package Obiekty;
import Wyjatki.TooManyThingsException;

import java.util.*;

public class MiejsceParkingowe extends Pomieszczenie {

    HashMap <UUID, Przedmiot> mapaPrzedmiotow;

    public MiejsceParkingowe(double powierzchnia) {
        super(powierzchnia);
        this.mapaPrzedmiotow = new HashMap<>();
    }

    public MiejsceParkingowe(double x, double y, double z) {
        this(x * y * z);
    }

    public void dodajPrzedmiot(Przedmiot przedmiot) throws TooManyThingsException {
        if (this.powierzchniaUzytkowa < przedmiot.getObjetosc()){
            throw new TooManyThingsException();
        }
        this.powierzchniaUzytkowa = this.getPowierzchniaUzytkowa() - przedmiot.getObjetosc();
        this.mapaPrzedmiotow.put(przedmiot.idPrzedmiotu, przedmiot);
        System.out.println(mapaPrzedmiotow);
        System.out.println(this.getPowierzchniaUzytkowa());
    }
    public void usunPrzedmiot(Przedmiot przedmiot){
        if(mapaPrzedmiotow.isEmpty()){
            System.out.println("NIE POSIADASZ ZADNYCH PRZEDMIOTOW");
            return;
        }
        else if(!czyJestPrzedmiot(przedmiot)){
            System.out.println("nie posiadasz tego przedmiotu");
            return;
        }
        this.powierzchniaUzytkowa = this.getPowierzchniaUzytkowa() + przedmiot.getObjetosc();
        mapaPrzedmiotow.remove(przedmiot);
        System.out.println(mapaPrzedmiotow);
        System.out.println(this.getPowierzchniaUzytkowa());
    }

    public boolean czyJestPojazd(){
        for(UUID id : mapaPrzedmiotow.keySet()){
            if(mapaPrzedmiotow.get(id) instanceof Pojazd)
                return true;
        }
        return  false;
    }

    public boolean czyJestPrzedmiot(Przedmiot przedmiot){
        if(mapaPrzedmiotow.containsKey(przedmiot.idPrzedmiotu)){
            return true;
        }
        return false;
    }

    public void usunPojazd(){
        for (UUID id: mapaPrzedmiotow.keySet()) {
            if(mapaPrzedmiotow.get(id) instanceof Pojazd){
                mapaPrzedmiotow.remove(id);
            }
        }
    }

    public HashMap<UUID, Przedmiot> getMapaPrzedmiotow() {
        return mapaPrzedmiotow;
    }

    @Override
    public String toString() {
        return "MiejsceParkingowe " + "ID: " + id.toString() + " POWIERZCHNIA: " + powierzchnia;

    }
}
