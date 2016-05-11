package AlgorithmDesignAndAnalysis1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by rliu on 5/10/16.
 */
public class programming03 {
    private static String FILE = "kargerMinCut.txt";

    public static void main(String[] args) {
        ArrayList<Integer> res = new ArrayList<>();
        //the success probability of karger's mincut algorithm of a given cut is 2/n(n-1), which is pretty low(n is the #of node)
        //but if repeating trails n^2 times, the probability we do not get given cut is close to 1/e
        //if repeating trails n^2lnn, the probability we do not get given cut is close to 1/n
        //just by trails close to 10, the probability we do not get cut is(1-2/n(n-1))^k where k=iteration#, n=# of node , but if there are multiple min cut, the suscess probability will increase
        for (int j = 0; j < 10; j++) {
            HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
            try (Stream<String> stream = Files.lines(Paths.get(FILE))) {
                stream.forEach(line -> {
                    String[] sp = line.split("\t");
                    ArrayList<Integer> vertices = new ArrayList<>();

                    for (int i = 1; i < sp.length; i++) {
                        vertices.add(Integer.valueOf(sp[i]));
                    }
                    map.put(Integer.valueOf(sp[0]), vertices);
                });

            } catch (Exception e) {
                System.err.println(e);

            }
            res.add(minCut(new HashMap<>(map)));
        }
        System.out.println(Collections.min(res));

    }

    private static int minCut(HashMap<Integer, ArrayList<Integer>> map) {
        while (map.size() > 2) {
            Random ran = new Random();

            Object[] key = map.keySet().toArray();
            int randomHead = (int) key[ran.nextInt(map.size())];
            ArrayList<Integer> headList = map.get(randomHead);

            int randomTail = headList.get(ran.nextInt(headList.size()));
            ArrayList<Integer> tailList = map.get(randomTail);

            //iterate the node u in the randomTail
            for (Integer x : tailList) {
                //get the all node v of node u,
                ArrayList<Integer> child = map.get(x);
                for (int i = 0; i < child.size(); i++) {
                    if (child.get(i) == randomTail) {
                        child.set(i, randomHead);
                    }
                }
            }
            headList.addAll(tailList);
            Iterator<Integer> it = headList.iterator();
            while (it.hasNext()) {
                if (it.next() == randomHead) {
                    it.remove();
                }
            }
            map.remove(randomTail);

        }
        Map.Entry<Integer, ArrayList<Integer>> entry = map.entrySet().iterator().next();
        return (entry.getValue().size());
    }

}
