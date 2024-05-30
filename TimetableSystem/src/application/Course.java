package application;

public class Course {
		private int idCourse;
	    private String nameCourse;
	    public Course(int id, String name) {
	        this.idCourse = id;
	        this.nameCourse = name;
	    }
	    public int getId() {
	        return idCourse;
	    }
	    public void setId(int newId) { 
	    	idCourse=newId;
	    }
	    public String getName() {
	        return nameCourse;
	    }
	    public void setName(String newName) { 
	    	nameCourse=newName; 
	    }
	    @Override
	    public String toString() {
	        return nameCourse;
	    }
}
