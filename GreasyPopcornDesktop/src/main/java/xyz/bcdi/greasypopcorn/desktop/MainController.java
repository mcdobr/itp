package xyz.bcdi.greasypopcorn.desktop;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {

	private static boolean isLoggedIn = false;
	private static String username = null;
	
	public static void loginUser(String user) {
		isLoggedIn = true;
		MainController.username = user;
	}
	
	public static void logoutUser(String user) {
		isLoggedIn = false;
		MainController.username = null;
	}
	
    @FXML private Button loginButton;
    @FXML private Button signupButton;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Label userLabel;
    @FXML private TextArea reviewTextArea;
    @FXML private Button submitReviewButton;

	@FXML
	public void initialize() {
		loginButton.setVisible(!isLoggedIn);
		signupButton.setVisible(!isLoggedIn);
		
		userLabel.setVisible(isLoggedIn);
		userLabel.setText(isLoggedIn ? username : "");
	}
    
    
    @FXML
    void onLoginButtonPressed(MouseEvent event) {
    	
    	/* Get the stage the event node belongs to */
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(DesktopApp.loginScene);
    }

    @FXML
    void onSearchButtonPressed(MouseEvent event) {

    }

    @FXML
    void onSignupButtonPressed(MouseEvent event) {

    }

    @FXML
    void onSubmitReviewButton(MouseEvent event) {

    }

}
