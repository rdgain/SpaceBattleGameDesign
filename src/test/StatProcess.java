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
public class StatProcess {

    public static void main(String[] args) {
        StatSummary ss1 = new StatSummary();
        StatSummary ss2 = new StatSummary();

        String line, prev = "";

        double max1 = 0, max2 = 0;
        String maxs1 = "", maxs2 = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("file.txt"));
            while ((line = br.readLine()) != null) {
                if (Objects.equals(prev, "")) prev = line;
                if (line.contains("Best fitness:")) {
                    double fitness = Double.parseDouble(line.split(":")[1]);
                    ss1.add(fitness);
                    if (fitness > max1) {
                        max1 = fitness;
                        maxs1 = prev.split(":")[1];
                    }
                }
                if (line.contains("Final fitness:")) {
                    double fitness = Double.parseDouble(line.split(":")[1]);
                    ss2.add(fitness);
                    if (fitness > max2) {
                        max2 = fitness;
                        maxs2 = prev.split(":")[1];
                    }
                }
                prev = line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ss1);
        System.out.println(maxs1);
        System.out.println();
        System.out.println(ss2);
        System.out.println(maxs2);

    }
}
