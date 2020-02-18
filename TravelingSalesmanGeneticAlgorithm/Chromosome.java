import java.util.List;

public class Chromosome {

    List<Integer> genome;
    int[][] fitnessMarks;
    int cityCount;
    int fitness;


    public Chromosome(List<Integer> permutationOfCities, int cityCount, int[][] fitnessMark) {
        this.genome = permutationOfCities;
        this.fitnessMarks = fitnessMark;
        this.cityCount = cityCount;
        this.fitness = this.calcFitness();
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public int getFitness() {
        return fitness;
    }

    public int calcFitness() {
        int fitness = 0;
        int currentCity = 0;

        for (int gene : genome) {
            fitness += fitnessMarks[currentCity][gene];
            currentCity = gene;
        }
        fitness += fitnessMarks[genome.get(cityCount - 2)][0];

        return fitness;
    }

}