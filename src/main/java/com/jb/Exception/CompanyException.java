package com.jb.Exception;

public class CompanyException extends Exception {
    public CompanyException() { super("General message.");}
    public CompanyException(String message){super(message);}
}
