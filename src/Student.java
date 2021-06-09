import java.util.ArrayList;
import java.util.List;

public class Student {
    public int id;
    public ArrayList<Integer> courses;

    public Student(int id) {
        this.id = id;
        this.courses = new ArrayList<>();
    }

    public void addCourse(int courseID) {
        this.courses.add(courseID);
    }

    public void printCourses() {
        System.out.print("Courses of Student: ");
        System.out.println(this.id);
        for(int i=0; i<this.courses.size(); i++)
            System.out.println(courses.get(i) + " ");
    }

    public boolean isEnrolled(int courseID) {
        return this.courses.contains(courseID);
    }
}
