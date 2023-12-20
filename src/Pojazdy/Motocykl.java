package Pojazdy;
import Obiekty.Pojazd;

public class Motocykl extends Pojazd{

    int iloscKol;
    double pojemnoscSilnika;

    public Motocykl(double wielkosc, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika , int iloscKol) {
        super(wielkosc, nazwa, typPojazdu, typSilnika);
        this.iloscKol = iloscKol;
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    public Motocykl(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, int iloscKol) {
        this(x * y * z, nazwa, typPojazdu, typSilnika, pojemnoscSilnika, iloscKol);
    }

}
