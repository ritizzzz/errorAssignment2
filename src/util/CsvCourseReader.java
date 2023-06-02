package util;

import java.util.HashSet;
import java.util.Scanner;

import model.Days;
import model.Course;

import java.io.File;
import java.io.FileNotFoundException;


public class CsvCourseReader{
    private static final HashSet<Course> allCourses = new HashSet<>();

    static {
        try (Scanner sc = new Scanner(new File("/home/user/Desktop/Java/furtherProgramming/cosc2931_assignment_2/src/data/course.csv"))) {
            int headerCounter = 0;
            sc.useDelimiter("\n"); 
            while (sc.hasNext()) {
                Scanner scRow = new Scanner(sc.next());
                if(headerCounter > 0){
                    scRow.useDelimiter(",");
                    while(scRow.hasNext()){
                        String courseName = scRow.next();
                        int capacity = scRow.nextInt();
                        String year = scRow.next();
                        String deliveryMode = scRow.next();
                        Days day = Days.valueOf(scRow.next().toUpperCase());
                        String time = scRow.next();
                        double duration = Double.parseDouble(scRow.next());
                        allCourses.add(new Course(courseName, capacity, year, deliveryMode, day, time, duration));
                    }
                }
                scRow.close();
                headerCounter++;
            }   

            sc.close();
        } catch (FileNotFoundException fileNotFound) {
            fileNotFound.printStackTrace();
        }  catch (Exception e){
            e.printStackTrace();
        }
    }    

    public static HashSet<Course> returnAllCourses(){
        return allCourses;
    }
}
