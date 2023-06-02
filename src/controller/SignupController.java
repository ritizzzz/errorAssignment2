package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField studentNumber;
    @FXML
	private TextField firstname; 
    @FXML
	private TextField lastname;
    @FXML
	private PasswordField password; 
	@FXML
	private Button createUser;
	@FXML
	private Button login;
	@FXML
	private Label status;
	
	private Stage stage;
    private Stage parentStage;
    private Model model;

    public SignupController(Stage parentStage, Model model){
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;

    }

    @FXML
    public void initialize(){
        login.setOnAction(event -> {
            stage.close();
            parentStage.getScene().lookup("#message").setVisible(false);
            parentStage.show();
        });
        createUser.setOnAction(event -> {
            status.setVisible(true);
            if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
                if (!firstname.getText().isEmpty() && !lastname.getText().isEmpty()) {
                    User user;
                    try {
                        user = model.getUserDao().createUser(username.getText(), password.getText());
                        if (user != null) {
                            model.getStudentDao().createStudent(firstname.getText(), lastname.getText(), username.getText());
                            status.setText("Created " + user.getUsername());
                            status.setTextFill(Color.GREEN);
                            username.clear();
                            firstname.clear();
                            lastname.clear();
                            password.clear();
                        } else {
                            status.setText("Cannot create user");
                            status.setTextFill(Color.RED);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                        status.setText("Username already taken!");
                        status.setTextFill(Color.RED);
                    }
                }else{
                    status.setText("Empty firstname or lastname");
                    status.setTextFill(Color.RED);                    
                }	
			} else {
				status.setText("Empty username or password");
				status.setTextFill(Color.RED);
			}

        });
    }

    public void showStage(Parent root) {
		Scene scene = new Scene(root);
        status.setVisible(false);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
    
}
