package model;

public class Course {
    private String courseName;
    private int capacity;
    private String year;
    private String deliveryMode;
    private Days dayOflecture;
    private String time;
    private double duration;

    public Course(String courseName, int capacity, String year, String deliveryMode, Days dayOflecture, String time, double duration){
        this.courseName = courseName;
        this.capacity = capacity;
        this.year = year;
        this.deliveryMode = deliveryMode;
        this.dayOflecture = dayOflecture;
        this.time = time;
        this.duration = duration;
    }
    public String getDayOfLecture(){
        return dayOflecture.toString().substring(0, 1) + dayOflecture.toString().substring(1).toLowerCase();
    }

    public String getTime(){
        return time;
    }

    public double getDuration(){
        return duration;
    }


    public String getCourseName(){
        return courseName;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getYear(){
        return year;
    }

    public String getDeliveryMode(){
        return deliveryMode;
    }

    public String getTimeFrame(){
        int totalMinute = (int)(getDuration() * 60);
        String[] time = getTime().split(":");
        totalMinute = Integer.parseInt(time[1]) + totalMinute;
        int hours = (int)totalMinute/60;
        int minute = (int)totalMinute % 60;
        
     
        return String.format("%s-%d:%02d", getTime(), hours+Integer.parseInt(time[0]), minute);
    }

}
