import java.util.ArrayList;
import java.util.*;
import java.io.*;



public class Main {
    public static void getStdAndCourseCount() {  // function to get ros and columns of file
        File file = new File("Input/registration.data");

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int value = 0;
            int rows = 0, cols = 0, col = 0;

            while ((value= br.read()) != -1) {
                if (value == 13)
                {
                    rows++;
                    col = cols;
                    cols = 0;
                }
                if (value != 32 && value != 10 && value != 13 && value != 9) {
                    cols++;
                }
            }
            GlobalVar.TOTAL_STUDENTS = col;
            GlobalVar.TOTAL_COURSES = rows;
            br.close();
            fr.close();
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getRoomsCount() {  // function to get total rooms from file
        File file = new File("Input/capacity.room");

        try {
            Scanner val = new Scanner(file);
            int totalRooms = 0;

            while(val.hasNextInt()) {
                {
                    val.nextInt();
                    totalRooms++;
                }
            }
            GlobalVar.TOTAL_ROOMS = totalRooms;
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void getTimeSlotsAndDays() {  // function to get total days and slots from file
        File file = new File("Input/general.info");

        try {
            Scanner val = new Scanner(file);

            while(val.hasNextInt()) {
                {
                    GlobalVar.DAYS = val.nextInt();
                    GlobalVar.TIME_SLOTS = val.nextInt();
                }
            }
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static int[][] readFile() {    // function to store registration data in array
        File file = new File("Input/registration.data");
        int[][] data = new int[GlobalVar.TOTAL_COURSES][GlobalVar.TOTAL_STUDENTS];
        try {
            Scanner value = new Scanner(file);
            int count = 0;

            for(int i = 0; i < GlobalVar.TOTAL_COURSES; i++) {
                for(int j = 0; j < GlobalVar.TOTAL_STUDENTS; j++) {
                    data[i][j] = value.nextInt();
                    count++;
                }
            }
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ArrayList<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<>();
        int[][] regData = readFile();
        Student s = null;
        for(int std = 0; std < GlobalVar.TOTAL_STUDENTS; std++) {
            s = new Student(std);
            for(int course = 0; course < GlobalVar.TOTAL_COURSES; course++) {
                if(regData[course][std] == 1)
                {
                    s.addCourse(course);
                }
            }
            students.add(s);
        }


        return students;

    }


    public static ArrayList<Course> getCourses() {
        int[][] regData = readFile();
        ArrayList<Course> courses = new ArrayList<>();

        Course c = null;
        for(int course = 0; course < GlobalVar.TOTAL_COURSES; course++) {
            c = new Course(course);
            for(int std = 0; std < GlobalVar.TOTAL_STUDENTS; std++) {
                if(regData[course][std] == 1)
                    c.addStudent(std);

            }
            courses.add(c);
        }
        return courses;
    }


    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        File file = new File("Input/capacity.room");

        try {
            Scanner sc = new Scanner(file);

            for(int room = 0; room < GlobalVar.TOTAL_ROOMS; room++) {
                Room r = null;
                r = new Room(sc.nextInt());
                rooms.add(r);
            }
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return rooms;
    }


    public static ArrayList<DateSheet> generateSuccessors(DateSheet dateSheet) {  // function to generate successors of given datesheet and return with greater fitness
        int successor = 0;
        ArrayList<DateSheet> dateSheets = new ArrayList<>();
        for (int i=0; i<dateSheet.getDateSheet().size(); i++) {
            for (int j=0; j<GlobalVar.TOTAL_SLOTS && j!=dateSheet.getDateSheet().get(i); j++) {
                DateSheet d = dateSheet.clone();
                d.getDateSheet().set(i, j);
                dateSheets.add(d);
                System.gc();
            }
        }
        return dateSheets;
    }


    public static void main(String[] args) {
        getStdAndCourseCount();
        getRoomsCount();
        getTimeSlotsAndDays();
        GlobalVar.TOTAL_SLOTS = GlobalVar.TIME_SLOTS*GlobalVar.DAYS;
        GlobalVar.rooms = getRooms();
        GlobalVar.students = getStudents();
        GlobalVar.courses = getCourses();

        GeneticAlgo geneticAlgo = new GeneticAlgo();
        geneticAlgo.makePopulation();

        int generations = 0;
        while (true) {
            geneticAlgo.computeFitness();
            geneticAlgo.sort();
            if (generations >= 10)
                break;

            ArrayList<DateSheet> newGeneration = new ArrayList<>();

            int s = (10*GlobalVar.POPULATION_SIZE)/100;
            for (int i=0; i<s; i++) {
                newGeneration.add(GlobalVar.population.get(i));
            }

            s = (90*GlobalVar.POPULATION_SIZE)/100;
            for (int i=0; i<s; i++) {
                Random random = new Random();
                int r1 = random.nextInt(GlobalVar.POPULATION_SIZE-1);
                int r2 = random.nextInt(GlobalVar.POPULATION_SIZE-1);
                DateSheet d = GlobalVar.population.get(r1).crossOver(GlobalVar.population.get(r2));
                newGeneration.add(d);
            }

            GlobalVar.population = newGeneration;
            generations++;
            System.out.println("Generation: " + generations + ", Fitness: " + GlobalVar.population.get(0).getFitness());
            System.gc();
        }
        // Local Search
        DateSheet best = GlobalVar.population.get(0);
        GlobalVar.population = generateSuccessors(best);
        geneticAlgo.computeFitness();
        geneticAlgo.sort();

        System.out.println("Fitness: " + GlobalVar.population.get(0).getFitness());
        int v1 = GlobalVar.population.get(0).getNumberOfClashes();
        int v2 = GlobalVar.population.get(0).studentsWithConsecutiveExams();
        int v3 = GlobalVar.population.get(0).studentsWithMoreThan3ExamsInOneDay();
        System.out.println("Students With Exams In Consecutive Slots: "+ v1);
        System.out.println("Students With Multiple Exams On SameDay: "+ v2);
        System.out.println("Students With More Than 3 Exams In One Day: "+ v3);

        GlobalVar.population.get(0).printDateSheet();
    }
}

