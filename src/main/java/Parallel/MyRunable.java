package main.java.Parallel;

import main.java.Utils.HeapSort;
import main.java.Utils.Kickstarter;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyRunable implements Runnable {
    private List<Kickstarter> listToSort;
    private CountDownLatch countDownLatch;

    public MyRunable(List<Kickstarter> listToSort, CountDownLatch countDownLatch) {
        this.listToSort = listToSort;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        //System.out.println("Started sorting: " + this.hashCode());
        HeapSort.heapSort(listToSort);
        //System.out.println("Done sorting: " + this.hashCode());
        countDownLatch.countDown();
    }

    public List<Kickstarter> getListToSort() {
        return listToSort;
    }
}
