import java.util.ArrayList;

public class Course {
    public int id;
    public ArrayList<Integer> students = null;


    public Course(int id) {
        this.id = id;
        this.students = new ArrayList();
    }


    /* get count of students common in two courses */
    public int commonStudents( Course course) {
        int count = 0;
        for (int i=0; i<this.students.size(); i++) {
            if (course.isEnrolled(this.students.get(i)));
            count++;
        }
        return count;
    }

    public void addStudent(int studentID) {
        this.students.add(studentID);
    }


    public void printCourses() {
        System.out.print("Courses ID: ");
        System.out.println(this.id);
        System.out.print("Students: ");
        for(int i=0; i<this.students.size(); i++)
            System.out.println(students.get(i) + " ");
    }


    public boolean isEnrolled(int studentID) {
        return this.students.contains(studentID);
    }


    public int getId() {
        return id;
    }
}
