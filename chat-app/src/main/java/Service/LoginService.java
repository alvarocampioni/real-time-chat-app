package Service;

import Client.*;
import Repository.ClientRepository;
import helper.WarningDialog;

public class LoginService {
    public static Client logged;
    public ClientRepository repo = new ClientRepository();
    public PasswordService encoder = new PasswordService();

    public boolean authenticate(Client client) {
        String storedPass = repo.getPasswordByUsername(client.username);
        if(storedPass.isEmpty()) {
            new WarningDialog("Error", "Wrong username or password");
        }
        if(encoder.getEncoder().matches(client.password, storedPass)) {
            logged = client;
            return true;
        }
        return false;
    }
}
