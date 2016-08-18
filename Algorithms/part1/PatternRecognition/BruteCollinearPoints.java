package Algorithms.part1.PatternRecognition;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by rliu on 6/29/16.
 */
public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegment;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        this.points = points;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "./src/Algorithms/part1/PatternRecognition/collinear/horizontal50.txt";
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

        BruteCollinearPoints bp = new BruteCollinearPoints(points);
        for (LineSegment segment : bp.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        System.out.println(bp.numberOfSegments());


    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegment.length;
    }

    // the line segments
    public LineSegment[] segments() {
        ArrayList<LineSegment> ls = new ArrayList();

        for (int p = 0; p < points.length; p++) {
            Point start = points[p];
            Point end = points[p];
            for (int q = p + 1; q < points.length; q++) {
                Point start1 = start;
                Point end1 = end;
                if (start.compareTo(points[q]) > 0)
                    start1 = points[q];
                if (end.compareTo(points[q]) < 0)
                    end1 = points[q];
                for (int r = q + 1; r < points.length; r++) {
                    Point start2 = start;
                    Point end2 = end;
                    if (start1.compareTo(points[r]) > 0)
                        start2 = points[r];
                    if (end1.compareTo(points[r]) < 0)
                        end2 = points[r];
                    for (int s = r + 1; s < points.length; s++) {
                        Point start3 = start;
                        Point end3 = end;
                        if (start2.compareTo(points[s]) > 0)
                            start3 = points[s];
                        if (end2.compareTo(points[s]) < 0)
                            end3 = points[s];
                        if (points[p].slopeTo(points[q]) == points[q].slopeTo(points[r]) && points[q].slopeTo(points[r]) == points[r].slopeTo(points[s])) {
                            ls.add(new LineSegment(start3, end3));
                        }
                    }
                }
            }
        }
        lineSegment = ls.toArray(new LineSegment[0]);
        return lineSegment;
    }

}
