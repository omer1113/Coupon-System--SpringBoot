package com.jb.Exception;

public class AdminException extends Exception {
    public AdminException() {
        super("General Exception");
    }

    public AdminException(String message) {
        super(message);

    }
}

