/*
 * Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
 */
package AlgorithmDesignAndAnalysis1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rliu
 */
public class programming01 {
    private static String FILE_NAME = "IntegerArray.txt";

//    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<>();
//        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
//            stream.forEach(line -> list.add(Integer.valueOf(line)));
//            System.out.print(divideAndConquer(list, 0, list.size() - 1));
//
//        } catch (Exception e) {
//            System.err.println("can not read file");
//        }
//    }

    private static long divideAndConquer(ArrayList<Integer> list, int start, int end) {
        if (start >= end) {
            return 0;
        } else {
            int mid = (start + end) / 2;
            long x = divideAndConquer(list, start, mid);
            long y = divideAndConquer(list, mid + 1, end);
            long z = countsplit(list, start, end);
            return x + y + z;
        }

    }

    private static long countsplit(ArrayList<Integer> list, int start, int end) {
        long inversion = 0;
        int mid = (start + end) / 2;

        List<Integer> left = new ArrayList<>(list.subList(start, mid + 1));
        List<Integer> right = new ArrayList<>(list.subList(mid + 1, end + 1));
        int lIndex = 0;
        int rIndex = 0;
        int insertIndex = start;
        while (lIndex < left.size() && rIndex < right.size()) {
            if (right.get(rIndex) < left.get(lIndex)) {
                inversion += (left.size() - lIndex);
                list.set(insertIndex, right.get(rIndex));
                rIndex++;
            } else {
                list.set(insertIndex, left.get(lIndex));
                lIndex++;
            }
            insertIndex++;
        }
        if (lIndex >= left.size()) {
            for (; insertIndex <= end; insertIndex++) {
                list.set(insertIndex, right.get(rIndex));
                rIndex++;
            }
        }

        if (rIndex >= right.size()) {
            for (; insertIndex <= end; insertIndex++) {
                list.set(insertIndex, left.get(lIndex));
                lIndex++;
            }
        }

        return inversion;
    }

}
