package main.Parallel;

import com.sun.scenario.effect.Merge;
import main.java.Utils.CsvReader;
import main.java.Utils.Kickstarter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Parallell {

    final static int NUM_JOBS = 4;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(NUM_JOBS);

        long elapsedRead;
        long elapsedSort;
        long startTime;
        long endTime;
        int counter = 0;

        try {
            CsvReader reader = new CsvReader("/main/Resources/ks-projects-201801-clean.csv");
            System.out.println("Start reading csv...");

            startTime = System.nanoTime();
            List<Kickstarter> projects = reader.getContent();
            endTime = System.nanoTime();
            elapsedRead = endTime-startTime;

            startTime = System.nanoTime();
            List<List<Kickstarter>> choppedList = chunkList(projects, NUM_JOBS);
            List<MyRunable> threadList = new ArrayList<>();
            int threadCounter = 1;
            for (List<Kickstarter> kickstarters : choppedList) {
                List<Kickstarter> newList = new ArrayList<>(kickstarters);
                threadList.add(new MyRunable(newList, "SomeThread" + threadCounter++, countDownLatch));
            }
            System.out.println("--------Starting all threads--------");
            threadList.forEach(MyRunable::run);

            System.out.println("--------Starting waiting for all threads--------");
            try {

                countDownLatch.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("--------Merging for all threads--------");
            List<Kickstarter> comb1 = Merge(threadList.get(0).getListToSort(), threadList.get(1).getListToSort());
            List<Kickstarter> comb2 = Merge(threadList.get(2).getListToSort(), threadList.get(3).getListToSort());
            List<Kickstarter> sortedProjects = Merge(projects, comb1, comb2);
            endTime = System.nanoTime();

            elapsedSort = endTime - startTime;
            System.out.println("Sorted project size: " + sortedProjects.size());
            System.out.println("Distinct project size: " + sortedProjects.stream().distinct().count());
            for (Kickstarter project : sortedProjects) {
                if (counter < 10) {
                    System.out.println(project);
                    counter++;
                }
                else
                    break;
            }

            System.out.println("Read time: " + elapsedRead/1000000 + " - Sort time: " + elapsedSort/1000000);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static List<Kickstarter> Merge(List<Kickstarter> left, List<Kickstarter> right){
        List<Kickstarter> comb = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()){
            if (left.get(i).compareTo(right.get(j)) >= 0)
                comb.add(left.get(i++));
            else
                comb.add(right.get(j++));
        }

        while (i < left.size())
            comb.add(left.get(i++));

        while (j < right.size())
            comb.add(right.get(j++));

        return comb;
    }

    private static List<Kickstarter> Merge(List<Kickstarter> comb, List<Kickstarter> left, List<Kickstarter> right){
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()){
            if (left.get(i).compareTo(right.get(j)) >= 0)
                comb.set(k++, left.get(i++));
            else
                comb.set(k++, right.get(j++));
        }

        while (i < left.size())
            comb.set(k++, left.get(i++));

        while (j < right.size())
            comb.set(k++, right.get(j++));

        return comb;
    }

    private static <T> List<List<T>> chunkList(List<T> list, int chunkSize) {
        final List<List<T>> lsParts = new ArrayList<>();
        final int iChunkSize = list.size() / chunkSize;
        int iLeftOver = list.size() % chunkSize;
        int iTake = iChunkSize;

        for( int i = 0, iT = list.size(); i < iT; i += iTake )
        {
            if( iLeftOver > 0 ) {
                iLeftOver--;
                iTake = iChunkSize + 1;
            }
            else
                iTake = iChunkSize;

            lsParts.add(new ArrayList<>(list.subList(i, Math.min(iT, i + iTake))) );
        }

        return lsParts;
    }
}
