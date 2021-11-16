package com.jb.Exception;

public class CustomerException extends Exception{
    public CustomerException(){super("General message");}
    public CustomerException(String message){super(message);}
}
