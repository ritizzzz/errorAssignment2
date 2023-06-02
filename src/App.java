
import java.io.IOException;
import java.sql.SQLException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Model;
 
public class App extends Application {
    private Model model;

	@Override
	public void init() {
		model = new Model();
	}


    @Override
    public void start(Stage primaryStage)  {
 

        try {
            model.setup();

            
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/LoginView.fxml"));
			
			LoginController loginController = new LoginController(primaryStage, model);

			loader.setController(loginController);

			Parent root = loader.load();

            loginController.showStage(root);
	
		} catch (IOException | RuntimeException  | SQLException e) {
			Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
			primaryStage.setTitle("Error");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
    }
 public static void main(String[] args) {
        launch(args);
    }
}