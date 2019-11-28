package main.Parallel;

import main.java.Utils.Kickstarter;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyRunable implements Runnable {
    private List<Kickstarter> listToSort;
    private String name;
    private CountDownLatch countDownLatch;

    public MyRunable(List<Kickstarter> listToSort, String name, CountDownLatch countDownLatch) {
        this.listToSort = listToSort;
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Started sorting: " + name);
        HeapSortParallel.heapSort(listToSort);
        System.out.println("Done sorting: " + name);
        countDownLatch.countDown();
    }

    public List<Kickstarter> getListToSort() {
        return listToSort;
    }
}
