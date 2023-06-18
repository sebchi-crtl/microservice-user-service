package com.multree.userservice.exception;

import java.io.Serial;

public class UserAuthenticationConflictException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UserAuthenticationConflictException() {
        super();
    }

    public UserAuthenticationConflictException(String s) {
        super(s);
    }

    public UserAuthenticationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserAuthenticationConflictException(Throwable cause) {
        super(cause);
    }
}
