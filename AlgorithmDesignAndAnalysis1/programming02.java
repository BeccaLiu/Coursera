/**
 * @author rliu
 */

/*
1.Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. As you know, the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different pivoting rules.
You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of length m, you should simply add m−1 to your running total of comparisons. (This is because the pivot element is compared to each of the other m−1 elements in the subarray in this recursive call.)
2.Compute the number of comparisons (as in Problem 1), always using the final element of the given array as the pivot element. Again, be sure to implement the Partition subroutine exactly as it is described in the video lectures. Recall from the lectures that, just before the main Partition subroutine, you should exchange the pivot element (i.e., the last element) with the first element.
3.Compute the number of comparisons (as in Problem 1), using the "median-of-three" pivot rule. [The primary motivation behind this rule is to do a little bit of extra work to get much better performance on input arrays that are nearly sorted or reverse sorted.] In more detail, you should choose the pivot as follows. Consider the first, middle, and final elements of the given array. (If the array has odd length it should be clear what the "middle" element is; for an array with even length 2k, use the kth element as the "middle" element. So for the array 4 5 6 7, the "middle" element is the second one ---- 5 and not 6!) Identify which of these three elements is the median (i.e., the one whose value is in between the other two), and use this as your pivot.
 */

package AlgorithmDesignAndAnalysis1;

import java.util.ArrayList;
import java.util.Arrays;

public class programming02 {

    private static String FILE_NAME = "QuickSort.txt";

//    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<>();
//        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
//            stream.forEach(line -> list.add(Integer.valueOf(line)));
//            ArrayList<Integer> sList = new ArrayList<>(list);
//            ArrayList<Integer> eList = new ArrayList<>(list);
//            ArrayList<Integer> mList = new ArrayList<>(list);
//            System.out.print(QuickSort(sList, 0, sList.size() - 1,"start")+"\n");
//            System.out.print(QuickSort(eList, 0, eList.size() - 1,"end")+"\n");
//            System.out.print(QuickSort(mList, 0, mList.size() - 1,"median")+"\n");
//
//        } catch (Exception e) {
//            System.err.println("can not read file");
//        }
//    }

    private static int QuickSort(ArrayList<Integer> list, int start, int end, String pivotMethod) {
        if (start >= end)
            return 0;
        if (pivotMethod.equals("end")) {
            swap(list, start, end);
        } else if (pivotMethod.equals("median")) {
            int temp = getMedian(list, start, end);

            swap(list, start, temp);
        }

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
        int left = QuickSort(list, start, pivotIndex - 1, pivotMethod);
        int right = QuickSort(list, pivotIndex + 1, end, pivotMethod);
        return (end - start) + left + right;
    }

    private static int getMedian(ArrayList<Integer> list, int start, int end) {
        int length = end - start + 1;
        int middleIndex = start;
        if (length % 2 == 0)
            middleIndex += length / 2 - 1;
        else
            middleIndex += length / 2;
        int[] findM = new int[]{list.get(start), list.get(end), list.get(middleIndex)};
        Arrays.sort(findM);
        if (list.get(start) == findM[1])
            return start;
        else if (list.get(end) == findM[1])
            return end;
        else
            return middleIndex;

    }

    private static void swap(ArrayList<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}