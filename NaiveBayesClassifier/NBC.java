import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NBC {

    public int[][] democratVotes = new int[16][2];
    public int[][] republicanVotes = new int[16][2];
    public int[] democratCount = new int[16];
    public int[] republicanCount = new int[16];


    public List<String> strList;
    public double[][] democratValues = new double[16][2];
    public double[][] republicanValues = new double[16][2];


    NBC(int from, int to) {

        strList = new LinkedList<>();
        int lineCounter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IntelijProj\\NaiveBayesClassifier\\DATA\\house-votes-84.data"))) {

            String line;
            while ((line = br.readLine()) != null) {

                lineCounter++;

                if (lineCounter >= from && lineCounter <= to) {
                    strList.add(line);
                } else {

                    String[] split = line.split(",");

                    if (split[0].contains("republican")) {

                        int counter = 0;

                        for (int i = 1; i < split.length; i++) {
                            String ch = split[i];

                            if (ch.contains("y")) {
                                republicanVotes[counter][0] += 1;
                                republicanCount[counter] += 1;
                            } else if (ch.contains("n")) {
                                republicanVotes[counter][1] += 1;
                                republicanCount[counter] += 1;
                            } else {
                                //  republicanCount[counter]+=1;
                            }
                            counter++;
                        }

                    } else if (split[0].contains("democrat")) {


                        int counter = 0;

                        for (int i = 1; i < split.length; i++) {
                            String ch = split[i];


                            if (ch.contains("y")) {
                                democratVotes[counter][0] += 1;
                                democratCount[counter] += 1;
                            } else if (ch.contains("n")) {
                                democratVotes[counter][1] += 1;
                                democratCount[counter] += 1;
                            } else {
                                // democratCount[counter]+=1;
                            }
                            counter++;
                        }
                    }
                }
            }
            for (int i = 0; i < 16; i++) {
                democratValues[i][0] = (((double) democratVotes[i][0])+1) / ((double) democratCount[i]);
                democratValues[i][1] = (((double) democratVotes[i][1])+1) / ((double) democratCount[i]);

                republicanValues[i][0] = (((double) republicanVotes[i][0])+1) / ((double) republicanCount[i]);
                republicanValues[i][1] = (((double) republicanVotes[i][1])+1) / ((double) republicanCount[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList calculate() {

        ArrayList<Integer> results = new ArrayList();

        for (String line : strList) {

            String[] split = line.split(",");
            int counter = 0;
            double demVal = 1;
            double repVal = 1;
            double yesFromAll = 1;
            for (int i = 0; i < split.length - 1; i++) {

                String ch = split[i];

                if (ch.contains("y")) {

                    demVal *= democratValues[counter][0];
                    repVal *= republicanValues[counter][0];
                    yesFromAll *= ((double) (democratVotes[counter][0] + republicanVotes[counter][0]) / 435);

                } else if (ch.contains("n")) {
                    demVal *= democratValues[counter][1];
                    repVal *= republicanValues[counter][1];
                    yesFromAll *= ((double) (democratVotes[counter][1] + republicanVotes[counter][1]) / 435);
                }
                counter++;
            }

            demVal = demVal * ((double) 267 / 435);
            repVal = repVal * ((double) 168 / 435);

            double isDemocrat = ((double) demVal / yesFromAll);
            double isRepublican = ((double) repVal / yesFromAll);


            if (isDemocrat > isRepublican) {
                results.add(1);
            } else {
                results.add(2);
            }
        }
        return results;
    }

    public static void main(String[] args) {


        int a = 1;
        int b = 30;
        ArrayList<ArrayList<Integer>> allResults = new ArrayList<ArrayList<Integer>>();


        for (int z = 0; z <= 10; z++) {

            NBC n = new NBC(a, b);
            a += 30;
            b += 30;
            ArrayList<Integer> result = n.calculate();
            allResults.add(result);
        }

        int not = 0;
        int identical = 0;

        double avgAccuracy=0;
        for (int i = 1; i < allResults.size(); i++) {
            ArrayList<Integer> first = allResults.get(i - 1);
            ArrayList<Integer> second = allResults.get(i);

            for (int z = 0; z < first.size(); z++) {

                if (first.get(z).equals(second.get(z))) {
                    identical++;
                } else {
                    not++;
                }
            }

            double accuracy=((double) identical/(identical+not));
            avgAccuracy+=accuracy;
            System.out.println("Matching: " + identical + " " + "Not matching: " + not + " => " +  "Accuracy: " + accuracy);
            not = 0;
            identical = 0;
        }

        System.out.println("Average accuracy: " + (avgAccuracy/10));
    }
}
