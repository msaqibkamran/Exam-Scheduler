import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgo {


    public GeneticAlgo() { }


    public void makePopulation() {
        for (int i=0; i<GlobalVar.POPULATION_SIZE; i++) {
            GlobalVar.population.add(createRandomDateSheet());
        }
    }

    /* create one random dateSheet(chromosome) */
    public DateSheet createRandomDateSheet() {
        DateSheet d = new DateSheet();
        for (int i=0, j=0; i<GlobalVar.TOTAL_COURSES; i++, j++) {
            if (j >= GlobalVar.TIME_SLOTS*GlobalVar.DAYS)
                j = 0;
            d.getDateSheet().add(j);
        }
        Collections.shuffle(d.getDateSheet());
        return d;
    }


    /* sorts population according to fitness value */
    public void sort() {
        for (int i=0; i<GlobalVar.population.size()-1; i++) {
            for (int j=0; j<GlobalVar.population.size()-i-1; j++) {
                if(GlobalVar.population.get(j).getFitness() > GlobalVar.population.get(j+1).getFitness())
                    Collections.swap(GlobalVar.population, j, j+1);
            }
        }
    }


    public void computeFitness() {
        for (int i=0; i<GlobalVar.population.size(); i++) {
            GlobalVar.population.get(i).computeFitness();
        }
    }


    public void printPopulation() {
        for (int i=0; i<GlobalVar.POPULATION_SIZE; i++) {
            GlobalVar.population.get(i).printDateSheet();
        }
    }
}
