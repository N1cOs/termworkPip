package ru.ifmo.se.termwork.support.exception;

public interface InputErrors {

    interface Duplicate{

        InputError EMAIL = new InputError("email", "exception.signUp.email");

        InputError SERIAL_NUMBER = new InputError("serial_number", "exception.signUp.serialNumber");

        InputError PHONE = new InputError("phone", "exception.signUp.phone");
    }

    interface Invalid{

        InputError EMAIL = new InputError("email", "exception.email.notFound");

        InputError PASSWORD = new InputError("password", "exception.password.invalid");
    }
}
