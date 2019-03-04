package com.morgage.utils;

import com.morgage.model.User;
import org.apache.commons.validator.routines.EmailValidator;

public class UserValidator {

    public static boolean validate(String userName) {
        EmailValidator validator = EmailValidator.getInstance();
       if (!validator.isValid(userName)) {
            return false;
        }
        return true;
    }
}

