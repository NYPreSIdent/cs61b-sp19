package bearmaps;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static Random rdm = new Random(500);

    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        return new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void testNearestSlides() {
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private KDTree createNewKDTree() {
        Point testcase1 = new Point(-1, -1);
        Point testcase2 = new Point(2, 2);
        Point testcase3 = new Point(0, 2);
        Point testcase4 = new Point(1, 2);
        Point testcase5 = new Point(-2, -2);

        LinkedList<Point> testCase = new LinkedList<>();
        testCase.addLast(testcase1);
        testCase.addLast(testcase2);
        testCase.addLast(testcase3);
        testCase.addLast(testcase4);
        testCase.addLast(testcase5);

        return new KDTree(testCase);
    }

    @Test
    public void testNearestNormal() {
        KDTree kd = createNewKDTree();
        Point actual = kd.nearest(1, 1);
        Point expected = new Point(1, 2);
        assertEquals(actual, expected);
    }

    private Point randomPoint() {
        double x = rdm.nextDouble();
        double y = rdm.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int N) {
        LinkedList<Point> points = new LinkedList<>();
        for (int i = 0; i < N; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }

    private void testWithNPointsAndQQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        for (int i = 0; i < queryCount; i += 1) {
            double x = points.get(i).getX();
            double y = points.get(i).getY();
            Point expected = nps.nearest(x, y);
            Point actual = kd.nearest(x, y);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testWith1000PointsAnd200Queries() {
        int pointCount = 1000;
        int queryCount = 200;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void testWith10000PointsAnd2000Queries() {
        int pointCount = 10000;
        int queryCount = 2000;
        testWithNPointsAndQQueries(pointCount, queryCount);
    }

    @Test
    public void compareTimingOfNaiveVsKDTreeLikeTheSpec() {
        List<Point> randomPoints = randomPoints(100000);
        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);
        List<Point> queryPoints = randomPoints(10000);

        long start = System.currentTimeMillis();
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("Naive 10000 queries on 100000 points: " + (end - start) / 1000.0 + "seconds.");

        start = System.currentTimeMillis();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        end = System.currentTimeMillis();
        System.out.println("KDTree 10000 queries on 100000 points: " + (end - start) / 1000.0 + "seconds.");
    }
}
