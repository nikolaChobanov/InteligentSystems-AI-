import java.util.*;
import java.util.stream.Collectors;

public class SalesPerson {

    private int cityCount;
    private int[][] fitnessMarks;
    private int sizeSelected;

    public SalesPerson(int cityCount, int[][] fitnessMarks) {
        this.cityCount = cityCount;
        this.fitnessMarks = fitnessMarks;
        this.sizeSelected = (cityCount * 3) / 4;
    }

    public List<Chromosome> randomPopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < cityCount * cityCount; i++) {

            List<Integer> randomPath = new ArrayList<Integer>();
            for (int j = 0; j < cityCount; j++) {
                if (j != 0)
                    randomPath.add(j);
            }
            Collections.shuffle(randomPath);
            population.add(new Chromosome(randomPath, cityCount, fitnessMarks));
        }
        return population;
    }


    public List<Chromosome> tournamentSelection(List<Chromosome> population) {
        List<Chromosome> randomized = new ArrayList<>();
        List<Chromosome> ordered = new ArrayList<>();
        if (population.size() < sizeSelected) {
            return null;
        }

        randomized = population;

        for (int i = 0; i < (cityCount * 3) / 2; i++) {

            Collections.shuffle(randomized);

            ordered.addAll(randomized.stream().collect(Collectors.toList())
                    .subList(population.size() - sizeSelected, population.size())
                    .stream().sorted(Comparator.comparing(Chromosome::getFitness)).limit(1).collect(Collectors.toList()));
        }
        return ordered;
    }

    public List<Chromosome> crossover(List<Chromosome> parents) {


        List<Integer> genome1 = new ArrayList<>(parents.get(0).getGenome());
        List<Integer> genome2 = new ArrayList<>(parents.get(1).getGenome());
        Random random = new Random();
        int breakaway = random.nextInt(cityCount - 1);
        List<Chromosome> kids = new ArrayList<>();
        int val;

        for (int i = 0; i < cityCount - 1; i++) {

            if (i < breakaway) {
                val = parents.get(1).getGenome().get(i);
                Collections.swap(genome1, genome1.indexOf(val), i);
            } else if (i >= breakaway) {
                val = parents.get(0).getGenome().get(i);
                Collections.swap(genome2, genome2.indexOf(val), i);
            }
        }

        kids.add(new Chromosome(genome1, cityCount, fitnessMarks));
        kids.add(new Chromosome(genome2, cityCount, fitnessMarks));

        return kids;
    }


    public List<Integer> mutation(Chromosome salesman) {
        Random random = new Random();
        List<Integer> genome = salesman.getGenome();
        int rate = random.nextInt(10);
        if (rate <= 5) {
            Collections.swap(genome, random.nextInt(cityCount - 1), random.nextInt(cityCount - 1));
            return genome;
        }
        return genome;
    }


    public List<Chromosome> newGeneration(List<Chromosome> population) {
        List<Chromosome> generation = new ArrayList<>();
        int currentGenerationSize = 0;
        while (currentGenerationSize < cityCount * 4) {


            Random r = new Random();
            List<Chromosome> parents = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                parents.add(population.get(r.nextInt(population.size())));
            }

            List<Chromosome> children = crossover(parents);
            List<Integer> genome1 = mutation(children.get(0));
            List<Integer> genome2 = mutation(children.get(1));
            children.set(0, new Chromosome(genome1, cityCount, fitnessMarks));
            children.set(1, new Chromosome(genome2, cityCount, fitnessMarks));
            generation.addAll(children);
            currentGenerationSize += 2;
        }
        return generation;
    }

    public void solver() {
        List<Chromosome> population = randomPopulation();
        Chromosome globalBestGenome = population.get(0);
        Chromosome lastBestGenome = null;
        Chromosome beforeLastGenome = null;
        Chromosome beforebeforeLastGenome;
        int i = 0;
        while (true) {
            List<Chromosome> selected = tournamentSelection(population);
            population = newGeneration(selected);
            if (i == 0) {
                lastBestGenome = globalBestGenome;

                globalBestGenome = population.stream().min(Comparator.comparing(o -> o.getFitness())).orElseThrow(NoSuchElementException::new);

            }
            if (i == 1) {
                beforeLastGenome = lastBestGenome;
                lastBestGenome = globalBestGenome;

                globalBestGenome = population.stream().min(Comparator.comparing(o -> o.getFitness())).orElseThrow(NoSuchElementException::new);
                if (globalBestGenome.getFitness() >= beforeLastGenome.getFitness()) {
                    break;
                }

            }
            if (i >= 2) {
                beforebeforeLastGenome = beforeLastGenome;
                beforeLastGenome = lastBestGenome;
                lastBestGenome = globalBestGenome;
                globalBestGenome = population.stream().min(Comparator.comparing(o -> o.getFitness())).orElseThrow(NoSuchElementException::new);
                if (globalBestGenome.getFitness() >= beforebeforeLastGenome.getFitness()) {
                    break;
                }
            }

            if (i == 10) {
                System.out.println("For 10th generation: ");
                System.out.println("Fitness: " + globalBestGenome.getFitness());
                System.out.println("Path: " + globalBestGenome.getGenome());
            }
            if (i == 25) {
                System.out.println("For 25th generation: ");
                System.out.println("Fitness: " + globalBestGenome.getFitness());
                System.out.println("Path: " + globalBestGenome.getGenome());
            }
            if (i == 40) {
                System.out.println("For 40th generation: ");
                System.out.println("Fitness: " + globalBestGenome.getFitness());
                System.out.println("Path: " + globalBestGenome.getGenome());
            }
            if (i == 65) {
                System.out.println("For 65th generation: ");
                System.out.println("Fitness: " + globalBestGenome.getFitness());
                System.out.println("Path: " + globalBestGenome.getGenome());
            }

            i++;

        }

        System.out.println("For: " + i + "th generation: ");
        System.out.println("Fitness: " + globalBestGenome.getFitness());
        System.out.println("Path: " + globalBestGenome.getGenome());
    }
}
