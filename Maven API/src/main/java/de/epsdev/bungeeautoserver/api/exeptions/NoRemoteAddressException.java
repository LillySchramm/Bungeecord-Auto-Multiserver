package de.epsdev.bungeeautoserver.api.exeptions;

public class NoRemoteAddressException extends Exception{
    public NoRemoteAddressException(){
        super("No remote address for client use defined.");
    }
}
