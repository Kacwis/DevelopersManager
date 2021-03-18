package Obiekty;

import Wyjatki.TooManyThingsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class Pomieszczenie {
    public double powierzchnia; //Metry szescnienne
    double powierzchniaUzytkowa;
    UUID id;
    public boolean czyWynajete;

    public Pomieszczenie(double powierzchnia){
        this.powierzchnia = powierzchnia;
        this.id = UUID.randomUUID();
        this.powierzchniaUzytkowa = powierzchnia;
        this.czyWynajete = false;
    }

    public Pomieszczenie(double x, double y, double z){
        this(x * y * z);
    }

    public boolean getCzyWynajete(){
        return czyWynajete;
    }

    abstract public String toString();


    public double getPowierzchniaUzytkowa() {
        return powierzchniaUzytkowa;
    }




}

