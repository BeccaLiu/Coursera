package AlgorithmDesignAndAnalysis1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by rliu on 5/11/16.
 * Your task is to code up the algorithm from the video lectures for computing strongly connected components (SCCs), and to run this algorithm on the given graph.
 */
public class programming04 {
    public final static String FILE = "SCC.txt";
    public static HashMap<Integer, Integer> finishTime = new HashMap<>();
    public static HashSet<Integer> visited = new HashSet<>();
    public static HashSet<Integer> finished = new HashSet<>();
    public static int currentTime = 0;
    public static int currentSource = 0;
    public static HashMap<Integer, HashSet<Integer>> leaders = new HashMap<>();
    public static HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
    public static HashMap<Integer, HashSet<Integer>> mapRev = new HashMap<>();
    public static Boolean IT = true;

    public programming04() {
        try {
            Stream<String> stream = Files.lines(Paths.get(FILE));
            stream.forEach(line -> {
                String[] vertices = line.split(" ");
                int tail = Integer.valueOf(vertices[0]);
                int head = Integer.valueOf(vertices[1]);
                if (map.containsKey(tail)) {
                    HashSet<Integer> heads = map.get(tail);
                    heads.add(head);
                    map.put(tail, heads);
                } else {
                    HashSet<Integer> heads = new HashSet<>();
                    heads.add(head);
                    map.put(tail, heads);
                }
                if (mapRev.containsKey(head)) {
                    HashSet<Integer> tails = mapRev.get(head);
                    tails.add(tail);
                    mapRev.put(head, tails);
                } else {
                    HashSet<Integer> tails = new HashSet<>();
                    tails.add(tail);
                    mapRev.put(head, tails);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        new programming04();

        ArrayList<Integer> nodes = new ArrayList<>(mapRev.keySet());
        dfs_loop(mapRev, nodes);

        visited = new HashSet<>();
        currentSource = 0;
        currentTime = 0;
        nodes = sortHashMapByValues(finishTime);
        //   System.out.println(nodes);
        dfs_loop(map, nodes);
        //  System.out.println(leaders);
        ArrayList<Integer> res = new ArrayList<>();

        for (HashSet<Integer> hs : leaders.values()) {
            res.add(hs.size());
        }
        Collections.sort(res, Collections.reverseOrder());
        for (int i = 0; i < 3; i++) {
            System.out.print(res.get(i) + ",");
        }
    }


    public static void dfs_loop(HashMap<Integer, HashSet<Integer>> map, ArrayList<Integer> nodes) {
        for (int i = nodes.size() - 1; i >= 0; i--) {
            int node = nodes.get(i);
            if (!visited.contains(node)) {
                currentSource = node;
                leaders.put(currentSource, new HashSet<>());
                if (IT)
                    dfs_it(map, node);
                else
                    dfs(map, node);
            }
        }

    }

    //recurssive
    public static void dfs(HashMap<Integer, HashSet<Integer>> map, int node) {
        visited.add(node);
        HashSet<Integer> inner = leaders.get(currentSource);
        inner.add(node);
        if (map.get(node) != null) {
            for (int head : map.get(node)) {
                if (!visited.contains(head))
                    dfs(map, head);
            }
        }
        //System.out.println(node);
        currentTime++;
        finishTime.put(node, currentTime); // a node is set to finish states when all adj of that node is finished.
    }

    //iterative
    public static void dfs_it(HashMap<Integer, HashSet<Integer>> map, int node) {
        HashSet<Integer> inner = leaders.get(currentSource);
        Stack<Integer> st = new Stack();
        st.push(node);
        visited.add(node);
        inner.add(node);

        Stack<ArrayList<Integer>> backtrack = new Stack<>();
        //Stack<Integer> backtrack = new Stack();
        while (!st.isEmpty()) {
            int curr = st.pop();

            HashSet<Integer> heads = getUnvisitedHead(map, curr);

            if (heads != null && heads.size() != 0) {
                for (Integer head : heads) {
                    st.push(head);
                    visited.add(head);
                    inner.add(head);
                }
                ArrayList<Integer> tracking = new ArrayList(heads);
                tracking.add(curr);
                backtrack.add(tracking);
            } else {
                currentTime++;
                finishTime.put(curr, currentTime);
                finished.add(curr);

                while (!backtrack.isEmpty() && isAllChildrenFinished(backtrack.peek())) { // a node is set to finish states when all adj of that node is finished.
                    ArrayList<Integer> top = backtrack.peek();
                    currentTime++;
                    int parent = top.get(top.size() - 1);
                    finishTime.put(parent, currentTime);
                    finished.add(parent);
                    backtrack.pop();
                }
            }
        }
    }

    public static Boolean isAllChildrenFinished(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() - 2; i++) {
            if (!finished.contains(list.get(i)))
                return false;
        }
        return true;
    }

    public static HashSet<Integer> getUnvisitedHead(HashMap<Integer, HashSet<Integer>> map, int curr) {
        HashSet<Integer> heads = map.get(curr);
        if (heads == null)
            return null;
        else {
            HashSet<Integer> unvisitedHead = new HashSet<>();
            for (Integer i : heads) {
                if (!visited.contains(i))
                    unvisitedHead.add(i);
            }
            return unvisitedHead;
        }
    }

    public static ArrayList sortHashMapByValues(HashMap<Integer, Integer> map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue()));

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        ArrayList<Integer> arrayList = new ArrayList<>();
        //sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            arrayList.add((Integer) entry.getKey());
        }
        return arrayList;
    }
}

