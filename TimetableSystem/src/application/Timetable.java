package application;

public class Timetable {
	private int timetableID;
    private String teacherName;
    private String courseName;
    private String roomNumber;
    private String timeslot;
    private String day;
    public Timetable(int id, String t, String c, String r, String ts, String d) {
        this.timetableID = id;
        this.teacherName = t;
        this.courseName=c;
        this.roomNumber=r;
        this.timeslot=ts;
        this.day=d;
        
    }
    public int getTimetableID() {
        return timetableID;
    }
    public void setTimetableID(int newId) { 
    	timetableID=newId;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String newName) { 
    	teacherName=newName; 
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String newName) { 
    	courseName=newName; 
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String newRoom) { 
    	roomNumber=newRoom; 
    }
    public String getTimeslot() {
        return timeslot;
    }
    public void setTimeslot(String newTime) { 
    	timeslot=newTime; 
    }
    public String getDay() {
        return day;
    }
    public void setDay(String newDay) { 
    	day=newDay; 
    }
    //@Override
    //public String toString() {
    //    return teacherName;
    //}
   

}
