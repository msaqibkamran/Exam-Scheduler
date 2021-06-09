import javax.security.auth.login.AccountLockedException;
import java.util.ArrayList;

public class Room {
    int capacity;
    int filled;
    ArrayList<ExamStudent> examStudents = null;

    public Room(int capacity) {
        this.capacity = capacity;
        filled = 0;
        examStudents = new ArrayList<>();
    }

    public void addExamStudents(ExamStudent examStudent) {
        if(this.filled < this.capacity) {
            this.examStudents.add(examStudent);
            filled = filled + 1;
        }
    }

    public void printExamStudent() {
        for (int i=0; i<this.examStudents.size(); i++) {
            System.out.println("    " + this.examStudents.get(i).studentID + " : " + this.examStudents.get(i).examID);
        }
    }

    public Room clone() {
        Room r = new Room(this.capacity);
        r.capacity = this.capacity;
        r.filled = this.filled;
        for (ExamStudent es : this.examStudents) {
            r.examStudents.add(es);
        }
        return r;
    }
}
