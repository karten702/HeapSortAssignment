package main.Parallel;

import main.java.Sequential.HeapSort;
import main.java.Utils.CsvReader;
import main.java.Utils.Kickstarter;

import java.io.IOException;
import java.util.List;

public class Parallell {

    public static void main(String[] args) {

        long elapsedRead = 0;
        long elapsedSort = 0;
        long startTime = 0;
        long endTime = 0;
        int counter = 0;

        try {
            CsvReader reader = new CsvReader("/main/Resources/ks-projects-201801-clean.csv");
            System.out.println("Start reading csv...");

            startTime = System.nanoTime();
            List<Kickstarter> projects = reader.getContent();
            endTime = System.nanoTime();
            elapsedRead = endTime-startTime;

            System.out.println("\nsorting project list of size: " + projects.size() + "\n");
            startTime = System.nanoTime();
            HeapSort.heapSort(projects);
            endTime = System.nanoTime();
            elapsedSort = endTime - startTime;

            counter = 0;
            for (Kickstarter project : projects) {
                if (counter < 10) {
                    System.out.println(project);
                    counter++;
                }
                else
                    break;
            }

            System.out.println("Read time: " + elapsedRead/1000000 + " - Sort time: " + elapsedSort/1000000);
            //projects.forEach(System.out::println);

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
