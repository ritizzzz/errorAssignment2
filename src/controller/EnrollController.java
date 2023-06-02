package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Course;
import model.Model;

public class EnrollController {

    @FXML
	private Button signout;
    @FXML
    private Label studentNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Button backToDash;
    @FXML
    private GridPane matchedCourses;
    @FXML
    private Button searchButton;
    @FXML
    private TextField query;
    @FXML
    private Button enroll;

   
    private Stage stage;
    private Stage dashBoardStage;
    private Stage loginStage;
    private Model model;



    public EnrollController(Stage dashBoardStage, Stage loginStage, Model model){
        this.stage = new Stage();
        this.dashBoardStage = dashBoardStage;
        this.loginStage = loginStage;
        this.model = model;
    }

 
    @FXML
    public void initialize(){
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

        searchButton.setOnAction(event -> {
            try {
                fillGridWithResult(query.getText().trim());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });    
    }
    
    public void fillGridWithResult(String query) throws SQLException{
        HashMap<Integer, Course> result = returnCoursesBasedOnUserInput(query);
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        enroll.setVisible(true);
        matchedCourses.getChildren().clear();
        matchedCourses.setVgap(4);
        matchedCourses.setHgap(4);
        matchedCourses.add(new Label("Course"), 0, 1);
        matchedCourses.add(new Label("Enroll"), 1, 1);
        result.forEach((key, value) -> {
            matchedCourses.add(returnCoursePane(value), 0, key+1);
            CheckBox checkBox = new CheckBox();
            checkBoxes.add(checkBox);
            matchedCourses.add(checkBox, 1, key+1);
        });
        enroll.setOnAction(event -> enrollIntoCourse(result, checkBoxes));
    }

    public void enrollIntoCourse(HashMap<Integer, Course> givenCourses, ArrayList<CheckBox> checkBoxes){
        for(int i = 0; i<checkBoxes.size(); i++){
            if(checkBoxes.get(i).isSelected()){
                System.out.println(givenCourses.get(i+1).getCourseName());
            }
        }
    }



    public TitledPane returnCoursePane(Course course){
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
            coursePane.setExpanded(false);  
            return coursePane;
        }

    public HashMap<Integer, Course> returnCoursesBasedOnUserInput(String input) throws SQLException{
        HashMap<Integer, Course> courseNames = new HashMap<>();
        int index = 1;
        for(Course course : model.getCourseDao().fetchAllCourses() ){
            if(course.getCourseName().toLowerCase().contains(input)){
                courseNames.put(index, course);
                index++;
            }
        }
        return courseNames;
    } 

    public void showStage(Parent root) {
		Scene scene = new Scene(root);
        enroll.setVisible(false);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Enroll");
		stage.show();
	}

}
