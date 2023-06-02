package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import model.Model;

public class AllCoursesController {
    @FXML
	private Button signout;
    @FXML
    private Label studentNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Accordion viewAllCourses;
    @FXML
    private Button backToDash;


    



    private Stage stage;
    private Stage dashBoardStage;
    private Stage loginStage;
    private Model model;


    public AllCoursesController(Stage dashBoardStage, Stage loginStage, Model model){
        this.stage = new Stage();
        this.dashBoardStage = dashBoardStage;
        this.loginStage = loginStage;
        this.model = model;

    }

    
	@FXML
	public void initialize() throws SQLException {	
        populateCourses();
        studentNumber.setText(Integer.toString(model.getCurrentStudent().getStudentNumber()));
        firstName.setText((model.getCurrentStudent().getFirstName()));
        lastName.setText(model.getCurrentStudent().getLastName());
        
		signout.setOnAction(event -> {
            stage.close();
            loginStage.getScene().lookup("#message").setVisible(false);
            loginStage.show();
        });

        backToDash.setOnAction(event -> {
            stage.close();
            dashBoardStage.show();
        });

        
	}

    public void populateCourses() throws SQLException{
        TitledPane tps[] = new TitledPane[model.getCourseDao().getNumberOfCourses()];

        int index = 0;

        for(Course course: model.getCourseDao().fetchAllCourses()){
            TitledPane coursePane = new TitledPane();
            coursePane.setText(course.getCourseName());

            GridPane grid = new GridPane();

            grid.add(new Label("Capacity: "), 0, 0);
            if(course.getCapacity() > 0){
                grid.add(new Label(Integer.toString(course.getCapacity())), 1, 0);
            }else{
                grid.add(new Label("Not Applicable"), 1, 0);
            }

            grid.add(new Label("Year:"), 0, 1);
            grid.add(new Label(course.getYear()), 1, 1);

            grid.add(new Label("Delivery:"), 0, 2);
            grid.add(new Label(course.getDeliveryMode()), 1, 2);        

            grid.add(new Label("Day:"), 0, 3);
            grid.add(new Label(course.getDayOfLecture()), 1, 3); 

            grid.add(new Label("Time:"), 0, 4);
            grid.add(new Label(course.getTime()), 1, 4); 

            grid.add(new Label("Duration of lecture:"), 0, 5);
            grid.add(new Label(Double.toString(course.getDuration())), 1, 5); 
            grid.setHgap(2);
            coursePane.setContent(grid);            
            tps[index] = coursePane;
            index = index + 1;
        }

        viewAllCourses.getPanes().addAll(tps);
    }


    public void showStage(Parent root) {
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("All courses");
		stage.show();
	}
    
}
