package xyz.bcdi.greasypopcorn.desktop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.bcdi.greasypopcorn.core.Movie;

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
    @FXML private Button signoutButton;
    
    
    @FXML private TableView<Movie> tableView;
    @FXML private TableColumn<Movie, ?> name;
    @FXML private TableColumn<Movie, ?> description;
    @FXML private TableColumn<Movie, ?> genre;
    @FXML private TableColumn<Movie, ?> releaseDate;
    
    
    
	@FXML
	public void initialize() {
		loginButton.setVisible(!isLoggedIn);
		signupButton.setVisible(!isLoggedIn);
		
		
		userLabel.setVisible(isLoggedIn);
		userLabel.setText(isLoggedIn ? username : "");
		signoutButton.setVisible(isLoggedIn);
	}
    
    
    @FXML
    void onLoginButtonPressed(MouseEvent event) throws IOException {
    	
    	/* Get the stage the event node belongs to */
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("login.fxml"))));
    }

    @FXML
    void onSignoutButtonPressed(MouseEvent event) {
    	MainController.logoutUser(this.username);
    	initialize();
    }
    
    @FXML
    void onSearchButtonPressed(MouseEvent event) {
    	String nameStr = searchField.getText();
    	
    	
    	Client client = ClientBuilder.newBuilder()
    			.property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
                .build();
    	WebTarget apiTarget = client.target("http://localhost:8080/GreasyPopcornWebServicesServer/webapi");
    	  
    	
    	List<Movie> movies;
    	if (nameStr == null || nameStr.isEmpty()) {
    		movies = apiTarget.path("movies")
    							.request(MediaType.APPLICATION_JSON)
    							.get(new GenericType<List<Movie>>(){});
    	} else {
    		movies = apiTarget.path("movies").path("query").queryParam("name", nameStr)
					.request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<Movie>>(){});
    	}
    	

    	/* Seteaza tabelul vizibil si pune detaliile */
    	tableView.setVisible(true);
    	
    	
    	
    	//List<Movie> movies = Arrays.asList(response.readEntity(Movie[].class));
    	
    	//List<Movie> movies = response.new GenericType<List<Person>>(){}
    	//List<Movie> movies = response.readEntity(new GenericType<List<Movie>>() {});
    	for (Movie movie : movies) {
        	tableView.getItems().add(movie);
    	}
    }

    @FXML
    void onSignupButtonPressed(MouseEvent event) throws IOException {
    	Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("signup.fxml"))));
    }

    @FXML
    void onSubmitReviewButton(MouseEvent event) {
    	
    }
}
