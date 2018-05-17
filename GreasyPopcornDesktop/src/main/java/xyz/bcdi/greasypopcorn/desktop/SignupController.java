package xyz.bcdi.greasypopcorn.desktop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML private Label usernameLabel;
    @FXML private TextField usernameField;
    @FXML private Label nameLabel;
    @FXML private TextField nameField;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label confirmPasswordLabel;

    @FXML private PasswordField confirmPasswordField;

    @FXML private Button signupButton;

    @FXML private Label errorLabel;

}
