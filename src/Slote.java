import java.util.ArrayList;

public class Slote {
    private ArrayList<Room> rooms;
    private int fitness;

    public Slote(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }


    public ArrayList<Room> getRooms() {
        return rooms;
    }


    public int getFitness() {
        return fitness;
    }


    public void computeFitness() {
        this.fitness = studentsWithMultipleExamsInOneSlot();
    }


    /* returns number of students who have multiple exams in the same time slot */
    private int studentsWithMultipleExamsInOneSlot() {
        int count = 0;
        ArrayList<Room> r = this.rooms;
        for (int k=0; k<r.size(); k++) {
            ArrayList<ExamStudent> es = r.get(k).examStudents;
            for (int l=0; l<es.size(); l++) {
                for (int m=l+1; m<r.size(); m++) {
                    ArrayList<ExamStudent> es1 = r.get(m).examStudents;
                    for (int n=0; n<es1.size(); n++) {
                        if(es.get(l).studentID == es.get(n).studentID)
                            count++;
                    }
                }
            }
        }
        return count;
    }


    /* does a particular student have exam in this slot or not */
    public boolean isPresent(int stdID) {
        for (int i = 0; i < this.rooms.size(); i++) {
            for (int j = 0; j < this.rooms.get(i).examStudents.size(); j++) {
                if (this.rooms.get(i).examStudents.get(j).studentID == stdID)
                    return true;
            }
        }
        return false;
    }


    /* returns all students who have exam in this slot */
    public ArrayList<Integer> getStudent() {
        ArrayList<Integer> stdIDs = new ArrayList<>();
        for (int i = 0; i < this.rooms.size(); i++) {
            for (int j = 0; j < this.rooms.get(i).examStudents.size(); j++) {
                stdIDs.add(this.rooms.get(i).examStudents.get(j).studentID);
            }
        }
        return stdIDs;
    }


    public Slote clone() {
        ArrayList<Room> rooms = new ArrayList<>();
        for(int i=0; i<this.rooms.size(); i++) {
            rooms.add(this.rooms.get(i).clone());
        }
        Slote s = new Slote(rooms);
        return s;
    }
}
