/**
 * @author rliu
 */

package AlgorithmDesignAndAnalysis1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class programming02 {

    private static String FILE_NAME = "QuickSort.txt";

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach(line -> list.add(Integer.valueOf(line)));
            System.out.print(QuickSort(list, 0, list.size() - 1, 0));
            //System.out.print(QuickSort(list, 0, list.size() - 1,list.size()-1));

        } catch (Exception e) {
            System.err.println("can not read file");
        }
    }

    public static int QuickSort(ArrayList<Integer> list, int start, int end, int pivotPoint) {

        if (start >= end)
            return 0;
        int pivot = list.get(start);
        int pivotIndex = start + 1;
        for (int unpartitioned = start + 1; unpartitioned <= end; unpartitioned++) {
            if (pivot > list.get(unpartitioned)) {
                int smaller = list.get(unpartitioned);
                list.set(unpartitioned, list.get(pivotIndex));
                list.set(pivotIndex, smaller);
                pivotIndex++;
            }
        }
        pivotIndex--;
        list.set(start, list.get(pivotIndex));
        list.set(pivotIndex, pivot);
        //System.out.print(pivot+" "+list);
        return (end - start) + QuickSort(list, start, pivotIndex - 1, start) + QuickSort(list, pivotIndex + 1, end, start);
    }

    public static int getMedian(ArrayList<Integer> list, int start, int end) {
        int middleIndex;
        if ((end - start + 1) % 2 == 0)
            middleIndex = (end - start + 1) / 2 - 1;
        else
            middleIndex = (end - start + 1) / 2;
        int[] findM = new int[]{list.get(start), list.get(end), list.get(middleIndex)};
        Arrays.sort(findM);
        if (list.get(start) == findM[1])
            return start;
        else if (list.get(end) == findM[1])
            return end;
        else
            return middleIndex;

    }

    public static void swap(ArrayList<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        //System.out.print("="+list+">"
    }
}