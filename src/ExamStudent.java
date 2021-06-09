public class ExamStudent {
    int studentID;
    int examID;

    public ExamStudent() { }

    public ExamStudent(int studentID, int examID) {
        this.studentID = studentID;
        this.examID = examID;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getExamID() {
        return examID;
    }
}