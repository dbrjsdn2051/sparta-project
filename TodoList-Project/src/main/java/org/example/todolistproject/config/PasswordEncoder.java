package org.example.todolistproject.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.todolistproject.exception.CustomException;
import org.example.todolistproject.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }

    public void validPassword(String password, String encodedPassword) {
        if (!matches(password, encodedPassword)) {
            throw new CustomException(ErrorCode.MISS_MATCHER_PASSWORD);
        }
    }
}
