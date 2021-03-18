package Obiekty;

import Obiekty.Pomieszczenie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Mieszkanie extends Pomieszczenie {

    HashSet<Osoba> listaLokatorow;

    public Mieszkanie(double powierzchnia) {
        super(powierzchnia);
        this.listaLokatorow = new HashSet<>();
    }

    public Mieszkanie(double x, double y, double z) {
        this(x * y * z);
    }

    public void dodajLokatora(Osoba os ){
        listaLokatorow.add(os);
    }

    public void usunLokatora(Osoba os){
        listaLokatorow.remove(os);
    }

    @Override
    public String toString() {
        return "MIESZKANIE " + "ID: " + id.toString() + "POWIERZCHNIA: " + powierzchnia;

    }
}
