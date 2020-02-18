import java.util.*;

public class Main {


    public static void main(String[] args) {

        Set<Pair> points = new LinkedHashSet<>();

        Scanner reader= new Scanner(System.in);

        int numberOfCities = reader.nextInt();

        int[][] travelPrices = new int[numberOfCities][numberOfCities];

        Random random = new Random();
        for (int i = 0; i < numberOfCities; i++) {
            int x = random.nextInt(numberOfCities);
            int y = random.nextInt(numberOfCities);

            while (x == y) {
                x = random.nextInt(numberOfCities);
                y = random.nextInt(numberOfCities);
            }
            if (x != y) {
                points.add(new Pair(x, y));
            }

        }

        Iterator<Pair> from = points.iterator();

        int i = 0, j = 0;
        while (from.hasNext()) {
            Pair currentFrom = from.next();
            Iterator<Pair> destination = points.iterator();

            while (destination.hasNext()) {
                Pair currentDestination = destination.next();


                if (i == j) {
                    travelPrices[i][j] = 0;
                } else {
                    double distance = Math.sqrt((currentFrom.y - currentDestination.y) * (currentFrom.y - currentDestination.y) + (currentFrom.x - currentDestination.x) * (currentFrom.x - currentDestination.x));

                    travelPrices[i][j] = (int) distance;
                    travelPrices[j][i] = travelPrices[i][j];
                }
                j++;
            }
            i++;
            j = 0;
        }

        SalesPerson geneticAlgorithm = new SalesPerson(numberOfCities, travelPrices);
        geneticAlgorithm.solver();


    }

    private static class Pair {
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }


        @Override
        public int hashCode() {
            int hash = 3;
            hash = 47 * hash + this.x;
            hash = 47 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Pair other = (Pair) obj;
            if (this.x != other.x) {
                return false;
            }
            if (this.y != other.y) {
                return false;
            }
            return true;
        }
    }
}