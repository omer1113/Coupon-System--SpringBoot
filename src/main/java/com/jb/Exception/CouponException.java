package com.jb.Exception;

public class CouponException extends Exception{
    public CouponException() { super("General message.");}
    public CouponException(String message){super(message);}

}
