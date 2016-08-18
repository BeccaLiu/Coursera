package Algorithms.part1.PatternRecognition.collinear;

import Algorithms.part1.PatternRecognition.LineSegment;
import Algorithms.part1.PatternRecognition.Point;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by rliu on 7/4/16.
 */
public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegment;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        this.points = points;
    }

    public static void merge(Point[] points, Point[] aux, int lo, int mid, int hi, int p) {
        int i = lo;
        int j = mid + 1;
        for (int k = p + 1; k <= hi; k++) {
            if (points[p].slopeTo(points[i]) > points[p].slopeTo(points[j]))
                aux[k] = points[j++];
            else if (points[p].slopeTo(points[i]) < points[p].slopeTo(points[j]))
                aux[k] = points[i++];
            else {
                if (points[i].compareTo(points[j]) < 0)
                    aux[k] = points[i++];
                else if (points[i].compareTo(points[j]) > 0)
                    aux[k] = points[j++];
                else {
                    aux[k] = points[i++];
                    aux[++k] = points[j++];
                }
            }
        }


    }

    public static void main(String[] args) {
        String fileName = "./src/Algorithms/part1/PatternRecognition/collinear/grid4X4.txt";
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.show(0);
        StdDraw.setXscale(-10, 32768);
        StdDraw.setYscale(-10, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        if (points.length == 0)
            throw new IllegalArgumentException();

        FastCollinearPoints bp = new FastCollinearPoints(points);
        for (LineSegment segment : bp.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        System.out.println(bp.numberOfSegments());

    }

    public int numberOfSegments() {        // the number of line segments
        return lineSegment.length;
    }

    public LineSegment[] segments() {// the line segments

        for (int i = 0; i < points.length; i++) {
            sortPoints(points, new Point[points.length], i + 1, points.length - 1, i);
        }
        return lineSegment;
    }

    public void sortPoints(Point[] points, Point[] aux, int lo, int hi, int p) {
        if (hi <= lo)
            return;
        int mid = (lo + hi) / 2;
        sortPoints(aux, points, lo, mid, p);
        sortPoints(aux, points, mid + 1, hi, p);
        merge(points, aux, lo, mid, hi, p);
        System.out.println(lo + "/" + mid + "/" + hi);
    }
}
