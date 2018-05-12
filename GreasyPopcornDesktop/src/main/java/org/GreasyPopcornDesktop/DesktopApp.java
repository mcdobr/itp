package org.GreasyPopcornDesktop;

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
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainPresentation.fxml"));
			
			Scene scene = new Scene(root);
			// aia cu stylesheets
			primaryStage.setScene(scene);
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
