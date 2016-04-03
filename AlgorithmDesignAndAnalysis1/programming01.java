/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmDesignAndAnalysis1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * @author rliu
 */
public class programming01 {
    private static String FILE_NAME = "IntegerArray.txt";

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            stream.forEach(line -> list.add(Integer.valueOf(line)));
            System.out.print(divideAndConquer(list, 0, list.size() - 1));

        } catch (Exception e) {
            System.err.println("can not read file");
        }
    }

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
