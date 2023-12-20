package Pojazdy;
import Obiekty.Pojazd;

public class SamochodTerenowy extends Pojazd{

    String rodzajOpon;
    double pojemnoscSilnika;

    public SamochodTerenowy(double wielkosc, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, String rodzajOpon) {
        super(wielkosc, nazwa, typPojazdu, typSilnika);
        this.rodzajOpon = rodzajOpon;
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    public SamochodTerenowy(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, String rodzajOpon ) {
        this(x * y * z, nazwa, typPojazdu, typSilnika, pojemnoscSilnika, rodzajOpon);
    }

}
