package xyz.bcdi.greasypopcorn.desktop;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

/**
 * Hello world!
 *
 */
public class DesktopApp extends Application
{
	public static Scene mainScene;
	public static Scene loginScene;
	public static Scene signupScene;
	
	
	
	public DesktopApp() throws IOException {
		if (mainScene == null)
			mainScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("main.fxml")));
		if (loginScene == null)
			loginScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("login.fxml")));
		if (signupScene == null)
			signupScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("signup.fxml")));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			// aia cu stylesheets
			Scene mainScene = new Scene((Parent)FXMLLoader.load(getClass().getResource("main.fxml")));
			
			
			primaryStage.setScene(mainScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
    public static void main( String[] args )
    {
    	launch(args);
    }
}
