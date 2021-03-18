package Obiekty;

import java.util.UUID;

public class Przedmiot {
    double objetosc;
    String nazwa;
    UUID idPrzedmiotu;
    public Przedmiot(double objetosc,String nazwa){
        this.objetosc = objetosc;
        this.nazwa = nazwa;
        this.idPrzedmiotu = UUID.randomUUID();
    }
    public Przedmiot(double x, double y, double z, String nazwa){
       this(x * y * z, nazwa);
    }

    public double getObjetosc() {
        return objetosc;
    }

    public  String toString(){
        return nazwa + "  ZAJMOWANE MIEJSCE: " + objetosc;
    }

}
