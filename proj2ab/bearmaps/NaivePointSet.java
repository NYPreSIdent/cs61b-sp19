package bearmaps;

import java.util.LinkedList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> pointSet;

    /** Assume points has at least size 1. */
    public NaivePointSet(List<Point> points) {
        this.pointSet = points;
    }

    /* Return the closest point to the inputted coordinates. */
    @Override
    public Point nearest(double x, double y) {
        Point result = pointSet.remove(0);
        for (Point elem : pointSet) {
            double bestDis = distance(x, y, result.getX(), result.getY());
            double dis = distance(x, y, elem.getX(), elem.getY());
            if (dis < bestDis) {
                result = elem;
            }
        }
        return result;
    }

    /* Return the distance between two different points. */
    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
