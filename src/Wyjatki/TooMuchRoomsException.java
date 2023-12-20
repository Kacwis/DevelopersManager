package Wyjatki;

public class TooMuchRoomsException extends Exception {

    public String err(){
        return "Mozesz miec posiadać maksymalnie 5 pomieszczen";
    }
}
