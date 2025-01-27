package Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordService {
    private final BCryptPasswordEncoder encoder;

    public PasswordService() {
        encoder = new BCryptPasswordEncoder();
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
