package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.Student;
import model.User;

public class LoginController {
    @FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private Button login;
	@FXML
	private Button signup;
    
    
    private Stage stage;
    private Model model;
    
    public  LoginController(Stage stage, Model model){
        this.stage = stage;
        this.model = model;
    }

    public void initialize() {		
		login.setOnAction(event -> {
            message.setVisible(true);
            if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
                User user;
                Student student;
				try {
					user = model.getUserDao().getUser(username.getText(), password.getText());
                    student = model.getStudentDao().getStudent(username.getText());
					if (user != null) {
						model.setCurrentUser(user);
                        model.setCurrentStudent(student);
						try {
                            stage.close();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/HomeView.fxml"));    
                            HomeController homeController = new HomeController(stage, model);
                            loader.setController(homeController);
                            Parent root = loader.load();
                            homeController.showStage(root);
                    
                        } catch (IOException | RuntimeException e) {
                            Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
                            stage.setTitle("Error");
                            stage.setScene(scene);
                            stage.show();
                        }
					} else {
						message.setText("Wrong username or password");
						message.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					message.setText(e.getMessage());
					message.setTextFill(Color.RED);
				}
				
			} else {
				message.setText("Empty username or password");
				message.setTextFill(Color.RED);
			}
			username.clear();
			password.clear();
		});
		
		signup.setOnAction(event -> {
            try {
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SignupView.fxml"));
                
                SignupController signupController = new SignupController(stage, model);
    
                loader.setController(signupController);
    
                Parent root = loader.load();
    
                signupController.showStage(root);
        
            } catch (IOException | RuntimeException e) {
                Scene scene = new Scene(new Label(e.getMessage()), 200, 100);
                stage.setTitle("Error");
                stage.setScene(scene);
                stage.show();
            }
            
        });
	}

    public void showStage(Parent root) {
		Scene scene = new Scene(root);
        message.setVisible(false);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Welcome");
		stage.show();
	}
    

}
