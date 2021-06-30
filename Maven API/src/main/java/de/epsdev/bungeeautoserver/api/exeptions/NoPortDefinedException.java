package de.epsdev.bungeeautoserver.api.exeptions;

public class NoPortDefinedException extends Exception{
    public NoPortDefinedException(){
        super("No port was defined.");
    }
}
