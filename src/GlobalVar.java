import java.util.ArrayList;

public class GlobalVar {
    public static int TOTAL_STUDENTS;   // from file
    public static int TOTAL_COURSES;    // from file
    public static final int POPULATION_SIZE = 20;
    public static final double MUTATION_RATE = 0.01;
    public static final double MUTATION_VALUE = 0.45;
    public static int TIME_SLOTS;   // from file
    public static int DAYS; // from file
    public static int TOTAL_ROOMS;  // from file
    public static int TOTAL_SLOTS;  // from file

    public static ArrayList<Integer> possibleGenes = new ArrayList<>();
    public static ArrayList<DateSheet> population = new ArrayList<>();
    public static ArrayList<Student> students = null;
    public static ArrayList<Course> courses = null;
    public static ArrayList<Room> rooms = null;
}




