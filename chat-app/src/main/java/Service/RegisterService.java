package Service;

import Repository.ClientRepository;
import helper.WarningDialog;

public class RegisterService {
    public ClientRepository repo = new ClientRepository();
    public PasswordService encoder = new PasswordService();

    public void register(String username, String password) {
        try {
            if(username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Username or password cannot be empty");
            }
            String encoded = encoder.getEncoder().encode(password);
            repo.registerClient(username, encoded);
            new WarningDialog("Success", "Successfully registered user");
        } catch (Exception e) {
            new WarningDialog("Error", "Error registering: " + e.getMessage());
        }
    }
}
