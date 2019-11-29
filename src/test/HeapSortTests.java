package test;

import main.Parallel.Parallell;
import main.java.Utils.CsvReader;
import main.java.Utils.HeapSort;
import main.java.Utils.Kickstarter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class HeapSortTests {

    private final int NUM_DATASETS = 4;
    private List<List<Kickstarter>> Datasets;

    @BeforeEach
    void setUp() {
        CsvReader reader = new CsvReader("/main/Resources/ks-projects-201801-clean.csv");
        try {
            List<Kickstarter> Projects = reader.getContent();
            Datasets = chunkList(Projects, NUM_DATASETS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortsCorrectlySeq() {
        List<Kickstarter> dataset1Seq = new ArrayList<>(Datasets.get(0));
        List<Kickstarter> dataset1Coll = new ArrayList<>(Datasets.get(0));

        HeapSort.heapSort(dataset1Seq);
        dataset1Coll.sort(Kickstarter::compareTo);

        assertEquals(dataset1Seq, dataset1Coll);
    }

    @Test
    public void sortsCorrectlyPar() {
        List<Kickstarter> dataset1Par = new ArrayList<>(Datasets.get(0));
        List<Kickstarter> dataset1Coll = new ArrayList<>(Datasets.get(0));

        Parallell.SortParallel(dataset1Par);
        dataset1Coll.sort(Kickstarter::compareTo);

        assertEquals(dataset1Par, dataset1Coll);
    }

    @Test
    public void sortsCorrectly() {
        List<Kickstarter> dataset1Par = new ArrayList<>(Datasets.get(0));
        List<Kickstarter> dataset1Seq = new ArrayList<>(Datasets.get(0));

        Parallell.SortParallel(dataset1Par);
        HeapSort.heapSort(dataset1Seq);

        assertEquals(dataset1Par, dataset1Seq);
    }

    @Test
    public void sortsCorrectlyAll() {
        Datasets.forEach(dataset -> {
            List<Kickstarter> datasetSeq = new ArrayList<>(dataset);
            List<Kickstarter> datasetPar = new ArrayList<>(dataset);

            Parallell.SortParallel(datasetPar);
            HeapSort.heapSort(datasetSeq);

            assertEquals(datasetPar, datasetSeq);
        });
    }

    @Test
    public void timeAll() {
        Map<String, Long> resultsParallel = new HashMap<>();
        Map<String, Long> resultsSequential = new HashMap<>();
        AtomicInteger DatasetIterator = new AtomicInteger(0);
        AtomicLong startTime = new AtomicLong();
        AtomicLong endTime = new AtomicLong();

        Datasets.forEach(dataset -> {
            List<Kickstarter> datasetSeq = new ArrayList<>(dataset);
            List<Kickstarter> datasetPar = new ArrayList<>(dataset);
            DatasetIterator.incrementAndGet();

            startTime.set(System.nanoTime());
            Parallell.SortParallel(datasetPar);
            endTime.set(System.nanoTime());
            resultsParallel.put("Dataset " + DatasetIterator.get(), (endTime.get() - startTime.get())/1000000);

            startTime.set(System.nanoTime());
            HeapSort.heapSort(datasetSeq);
            endTime.set(System.nanoTime());
            resultsSequential.put("Dataset " + DatasetIterator.get(), (endTime.get() - startTime.get())/1000000);

            assertEquals(datasetPar, datasetSeq);
        });

        List<String> parallelList = new ArrayList<>(resultsParallel.keySet());
        List<String> sequentialList = new ArrayList<>(resultsParallel.keySet());
        parallelList.sort(String::compareTo);
        sequentialList.sort(String::compareTo);

        System.out.println("Parallel data: ");
        parallelList.forEach(key -> System.out.printf("%s%n", key));
        System.out.println();
        parallelList.forEach(key -> System.out.printf("%d%n", resultsParallel.get(key)));

        System.out.println("Sequential data: ");
        sequentialList.forEach(key -> System.out.printf("%s%n", key));
        System.out.println();
        sequentialList.forEach(key -> System.out.printf("%d%n", resultsSequential.get(key)));
    }

    private <T> List<List<T>> chunkList(List<T> list, int chunkSize) {
        final List<List<T>> lsParts = new ArrayList<>();
        final int iChunkSize = list.size() / chunkSize;
        int iLeftOver = list.size() % chunkSize;
        int iTake = iChunkSize;

        for (int i = 0, iT = list.size(); i < iT; i += iTake) {
            if (iLeftOver > 0) {
                iLeftOver--;
                iTake = iChunkSize + 1;
            } else
                iTake = iChunkSize;

            lsParts.add(new ArrayList<>(list.subList(i, Math.min(iT, i + iTake))));
        }

        return lsParts;
    }
}