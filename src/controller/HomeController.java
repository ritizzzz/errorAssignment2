package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Model;

public class HomeController {
    @FXML
	private Button signout;
    @FXML
    private Label studentNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Button showAllCourse;
    @FXML
    private Button enrollIntoCourse;

    private Stage stage;
    private Stage parentStage;
    private Model model;

    public HomeController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;

    }

    
	@FXML
	public void initialize() {	
        studentNumber.setText(Integer.toString(model.getCurrentStudent().getStudentNumber()));
        firstName.setText((model.getCurrentStudent().getFirstName()));
        lastName.setText(model.getCurrentStudent().getLastName());

        showAllCourse.setOnAction(event -> {
            try {
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AllCoursesView.fxml"));    
                AllCoursesController allCoursesController = new AllCoursesController(stage, parentStage, model);
                loader.setController(allCoursesController);
                Parent root = loader.load();
                allCoursesController.showStage(root);
        
            } catch (IOException | RuntimeException e) {
                Scene scene = new Scene(new Label(e.getMessage()));
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });

        enrollIntoCourse.setOnAction(event -> {
            try {
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EnrollView.fxml"));    
                EnrollController searchController = new EnrollController(stage, parentStage, model);
                loader.setController(searchController);
                Parent root = loader.load();
                searchController.showStage(root);
        
            } catch (IOException | RuntimeException e) {
                Scene scene = new Scene(new Label(e.getMessage()));
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
        });


		signout.setOnAction(event -> {
            stage.close();
            parentStage.getScene().lookup("#message").setVisible(false);
            parentStage.show();
        });
	}


    public void showStage(Parent root) {
		Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("../view/style/HomeController.css").toExternalForm());  // add the CSS stylesheet

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Dashboard");
		stage.show();
	}
    
}
