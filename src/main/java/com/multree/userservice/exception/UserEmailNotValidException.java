package com.multree.userservice.exception;

import java.io.Serial;

public class UserEmailNotValidException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserEmailNotValidException() {
        super();
    }

    public UserEmailNotValidException(String s) {
        super(s);
    }

    public UserEmailNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserEmailNotValidException(Throwable cause) {
        super(cause);
    }
}
