package application;

public class Teacher {
	private int id;
    private String name;
    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int newId) { 
    	id=newId;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) { 
    	name=newName; 
    }
    @Override
    public String toString() {
        return name;
    }
}
