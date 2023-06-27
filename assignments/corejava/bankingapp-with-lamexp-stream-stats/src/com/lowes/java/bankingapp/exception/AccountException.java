package com.lowes.java.bankingapp.exception;

import java.util.ConcurrentModificationException;

public class AccountException extends Exception {

    public AccountException()
    {
        super();
    }

    public AccountException(String message)
    {
        super(message);
    }

    public AccountException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    public AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
