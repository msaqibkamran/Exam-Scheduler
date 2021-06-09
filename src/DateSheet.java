import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.DoubleAccumulator;

public class DateSheet {
    private ArrayList<Integer> dateSheet;
    private int fitness;


    public DateSheet() {
        dateSheet = new ArrayList<>();
        fitness = Integer.MAX_VALUE;
    }


    public ArrayList<Integer> getDateSheet() {
        return this.dateSheet;
    }


    public void setDateSheet(ArrayList<Integer> dateSheet) {
        for (int i=0; i<dateSheet.size(); i++) {
            this.dateSheet.add(dateSheet.get(i));
        }
    }


    public int getFitness() {
        return fitness;
    }


    public void setFitness(int fitness) {
        this.fitness = fitness;
    }


    public void computeFitness() {
        this.fitness = studentsWithConsecutiveExams() + getNumberOfClashes() + studentsWithMoreThan3ExamsInOneDay();
    }


    /* count of students having more than three exams in one day */
    public int studentsWithMoreThan3ExamsInOneDay() {
        int count=0, k = 0;
        for (int i=0; i<GlobalVar.DAYS; i++) {
            ArrayList<Integer> courses =  new ArrayList<>();
            for (int j=0; j<dateSheet.size(); j++) {
               if (dateSheet.get(j)/GlobalVar.TIME_SLOTS == i)
                   courses.add(j);
            }
            for (int j=0; j<GlobalVar.students.size(); j++) {
                if (getEnrollments(GlobalVar.students.get(j).id, courses) > 3)
                    count++;
            }
        }
        return count;
    }


    /* a student is enrolled in how many courses */
    public int getEnrollments(int stdID, ArrayList<Integer> courses) {
        int count = 0;
        for (int i=0; i<courses.size(); i++) {
            if (GlobalVar.courses.get(courses.get(i)).isEnrolled(stdID))
                count++;
        }
        return count;
    }


    /* count of cases of two consecutive exams */
    public int studentsWithConsecutiveExams() {
        int count=0, k = 0;
        for (int i=0; i<GlobalVar.DAYS; i++) {
            for (int j=0; j<GlobalVar.TIME_SLOTS-1; j++) {
                ArrayList<Integer> courses1 = new ArrayList<>();
                ArrayList<Integer> courses2 = new ArrayList<>();
                courses1 = getCourses(k++);
                courses2 = getCourses(k);
//                if (getCommonStudents(courses1, courses2) > 0)
//                    count++;
                count = count + getCommonStudents(courses1, courses2);
            }
        }
        return count;
    }


    /* count of clashes (having multiple exams in same time slot) */
    public int getNumberOfClashes() {
        int count = 0;
        ArrayList<Integer> coursesOnSameSlot = new ArrayList<>();
        for (int i=0; i<GlobalVar.TOTAL_SLOTS; i++) {
            coursesOnSameSlot = getCourses(i);
//            if (getCommonStudents(coursesOnSameSlot) > 0)
//                count++;
            count = count + getCommonStudents(coursesOnSameSlot);
        }
        return count;
    }


    /* count of students common in the given courses */
    private int getCommonStudents(ArrayList<Integer> coursesOnSameSlot) {
        int count = 0;
        for (int i=0; i<GlobalVar.students.size(); i++) {
            boolean found = true;
            for (int j=0; j<coursesOnSameSlot.size(); j++) {
                if (GlobalVar.courses.get(coursesOnSameSlot.get(j)).isEnrolled(GlobalVar.students.get(i).id) == false) {
                    found = false;
                    break;
                }
            }
            if (found)
                count++;
        }
        return count;
    }


    /* count of students common in the given two courses set */
    private int getCommonStudents(ArrayList<Integer> courses1, ArrayList<Integer> courses2) {
        int count = 0;
        for (int i=0; i<GlobalVar.students.size(); i++) {
            boolean found = true;
            for (int j=0; j<courses1.size(); j++) {
                if (GlobalVar.courses.get(courses1.get(j)).isEnrolled(GlobalVar.students.get(i).id) == false) {
                    found = false;
                    break;
                }
            }
            for (int j=0; j<courses2.size(); j++) {
                if (GlobalVar.courses.get(courses2.get(j)).isEnrolled(GlobalVar.students.get(i).id) == false) {
                    found = false;
                    break;
                }
            }
            if (found)
                count++;
        }
        return count;
    }


    /* courses scheduled in a given time slot */
    public ArrayList<Integer> getCourses(int slot) {
        ArrayList<Integer> coursesOnSameSlot = new ArrayList<>();
        for (int j=0; j<this.dateSheet.size(); j++) {
            if (dateSheet.get(j) == slot)
                coursesOnSameSlot.add(j);
        }
        return coursesOnSameSlot;
    }


    public void printDateSheet() {
        System.out.println(this.dateSheet);
    }


    public DateSheet crossOver(DateSheet dateSheet) {   // function to make new offspring using parents
        DateSheet d = new DateSheet();
        for (int i=0; i<this.dateSheet.size(); i++) {
            if (i%2 == 0)
                d.dateSheet.add(dateSheet.dateSheet.get(i));
            else
                d.dateSheet.add(this.dateSheet.get(i));
        }
        Random random = new Random();
        int r = random.nextInt(100)/100;
        if(r == GlobalVar.MUTATION_VALUE) {
            int i = random.nextInt(this.dateSheet.size());
            d.dateSheet.set(i, random.nextInt(GlobalVar.TOTAL_SLOTS));
        }
        return d;
    }

    public DateSheet clone() {
        DateSheet d = new DateSheet();
        d.fitness = this.fitness;
        for (int i=0; i<this.dateSheet.size(); i++) {
            d.dateSheet.add(this.dateSheet.get(i));
        }
        return d;
    }
}
