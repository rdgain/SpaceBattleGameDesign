package test;

import tools.StatSummary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by Raluca on 28-Jan-17.
 */
public class StatProcess1 {

    public static void main(String[] args) {
        StatSummary ss1 = new StatSummary();
        StatSummary ss2 = new StatSummary();

        String line, prev = "";

        double max1 = -2000, max2 = 0;
        String maxs1 = "", maxs2 = "";

        boolean first = true;

        try {
            BufferedReader br = new BufferedReader(new FileReader("file1.txt"));
            while ((line = br.readLine()) != null) {
                if (Objects.equals(prev, "")) prev = line;
//                if (line.contains("Initialising")) {
//                    if (!first)
//                        ss1.add(max1);
//                    else first = false;
//
//                    if(max1 > max2) {
//                        max2 = max1;
//                        maxs2 = maxs1;
//                    }
//                    max1 = 0;
//                }
                if (line.contains("50 (BestFitness:")) {
                    double fitness = Double.parseDouble(line.split(":")[1].split("\\)")[0]);
                    ss1.add(max1);
                    if (fitness > max1) {
                        max1 = fitness;
                        maxs1 = prev.split(":")[1];
                    }
                }
//                if (line.contains("Final fitness")) {
//                    double fitness = Double.parseDouble(line.split(":")[1]);
//                    ss2.add(fitness);
//                    if (fitness > max2) {
//                        max2 = fitness;
//                        maxs2 = prev.split(":")[1];
//                    }
//                }
                prev = line;
            }

    } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ss1.add(max1);

        System.out.println(ss1);
        System.out.println(maxs1);
        System.out.println();
//        System.out.println(ss2);
//        System.out.println(maxs2);

    }
}
