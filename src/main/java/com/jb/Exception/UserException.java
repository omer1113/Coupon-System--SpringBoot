package com.jb.Exception;

public class UserException extends Exception{
    public UserException() { super("General message.");}
    public UserException(String message){super(message);}

}
