package xyz.bcdi.greasypopcorn.desktop;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private AnchorPane loginRootPane;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    void onLoginButtonPressed(MouseEvent event) throws IOException {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	
    	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
    	
    	Client client = ClientBuilder.newBuilder()
    			.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();
    	client.register(feature);
    	
    	
    	WebTarget apiTarget = client.target("http://localhost:8080/GreasyPopcornWebServicesServer/webapi");
  
    	Response response = apiTarget.path("users/" + username)
    			.request()
    			.post(Entity.text(""), Response.class);
    	
 
    	if (response.getStatus() == 200) {
    		errorLabel.setText("You're logged in!");
    		errorLabel.setVisible(false);
    		
    		MainController.loginUser(username);
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    		MainController mainController = loader.getController();
    		
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        	stage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("main.fxml"))));
    		
    	} else {
    		errorLabel.setText("Invalid login information!");
    		errorLabel.setVisible(true);
    	}
    	
    }

}