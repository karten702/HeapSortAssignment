package main.java.Utils;

import main.java.Kickstarter;

import java.util.List;

public class HeapSort {


    public static List<Kickstarter> heapSort(List<Kickstarter> projects){
        int size = projects.size();

        for (int i = size /2 - 1; i >= 0; i--)
            heapify(projects, size, i);

        for (int i=size-1; i>=0; i--) {
            //arrA[0] is a root of the heap and is the max element in heap
            Kickstarter x = projects.get(0);
            projects.set(0, projects.get(i));
            projects.set(i, x);

            // call max heapify on the reduced heap
            heapify(projects, i, 0);
        }

        return projects;
    }

    private static void heapify(List<Kickstarter> projects, int heapSize, int i){
        int largest = i; // Initialize largest as root
        int leftChildIdx  = 2*i + 1; // left = 2*i + 1
        int rightChildIdx  = 2*i + 2; // right = 2*i + 2

        if (leftChildIdx  < heapSize && projects.get(leftChildIdx).compareTo(projects.get(largest)) < 0)
            largest = leftChildIdx;

        if (rightChildIdx  < heapSize && projects.get(rightChildIdx).compareTo(projects.get(largest)) < 0)
            largest = rightChildIdx ;

        // If largest is not root
        if (largest != i) {
            Kickstarter swap = projects.get(i);
            projects.set(i, projects.get(largest));
            projects.set(largest, swap);

            // Recursive call to  heapify the sub-tree
            heapify(projects, heapSize, largest);
        }
    }

}
