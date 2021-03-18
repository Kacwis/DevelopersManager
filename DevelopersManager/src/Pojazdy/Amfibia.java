package Pojazdy;
import Obiekty.Pojazd;

public class Amfibia extends Pojazd {

    String budowaPojazdu;
    double pojemnoscSilnika;

    public Amfibia(double objetosc, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, String budowaPojazdu) {
        super(objetosc, nazwa, typPojazdu, typSilnika);
        this.budowaPojazdu = budowaPojazdu;
        this.pojemnoscSilnika = pojemnoscSilnika;
    }

    public Amfibia(double x, double y, double z, String nazwa, String typPojazdu, String typSilnika, double pojemnoscSilnika, String budowaPojazdu) {
        this(x*y*z, nazwa, typPojazdu, typSilnika, pojemnoscSilnika, budowaPojazdu);
    }

}
