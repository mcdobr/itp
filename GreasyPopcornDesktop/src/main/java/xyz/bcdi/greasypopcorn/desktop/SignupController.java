package xyz.bcdi.greasypopcorn.desktop;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.bcdi.greasypopcorn.core.User;
import xyz.bcdi.greasypopcorn.core.User.UserBuilder;

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

    @FXML
    void onSignupButtonPressed(MouseEvent event) throws IOException {
    	String username = usernameField.getText();
    	String name = nameField.getText();
    	String password = passwordField.getText();
    	String confirmPass = confirmPasswordField.getText();
    	
    	if (!password.equals(confirmPass)) {
    		errorLabel.setText("Passwords don't match!");
    		errorLabel.setVisible(true);
    	} else {
    		User user = new User(username, name, password, false);
    		
    		
    		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
    		Client client = ClientBuilder.newBuilder()
        			.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                    .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                    .build();
        	client.register(feature);
        	
        	WebTarget apiTarget = client.target("http://localhost:8080/GreasyPopcornWebServicesServer/webapi");
        	Response response = apiTarget.path("users/")
        			.request(MediaType.APPLICATION_JSON)
        			.post(Entity.entity(user, MediaType.APPLICATION_JSON), Response.class);

        	if (response.getStatus() == 201) {
        		errorLabel.setVisible(false);
        		
        		MainController.loginUser(username);
        		
        		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            	stage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("main.fxml"))));
        	} else {
        		errorLabel.setText("Username taken!");
        		errorLabel.setVisible(true);
        	}
    	}
    }
}
