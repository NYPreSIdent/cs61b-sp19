package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;
    private static final boolean HORIZON = true;

    /* Assume points have at least size 1. */
    public KDTree(List<Point> points) {
        for (Point p : points) {
            add(p);
        }
    }

    private static class Node extends Point {
        private Node left;
        private Node right;
        private boolean property;
        
        public Node(double x, double y, boolean property) {
            super(x, y);
            this.left = null;
            this.right = null;
            this.property = property;
        }

        public Node getLeft() {
            return this.left;
        }

        public Node getRight() {
            return this.right;
        }

        public boolean getProperty() {
            return this.property;
        }

    }

    /* add points into the KDTree. */
    private void add(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("points can not be null.");
        }
        this.root = addHelper(p, root, HORIZON);
    }

    private Node addHelper(Point p, Node n, boolean basis) {
        if (n == null) {
            return new Node(p.getX(), p.getY(), basis);
        }
        double cmp;
        if (basis) {
            cmp = p.getX() - n.getX();
        } else {
            cmp = p.getY() - n.getY();
        }
        if (cmp > 0) {
            n.right = addHelper(p, n.right, !basis);
        } else {
            n.left = addHelper(p, n.left, !basis);
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point result = nearestHelper(root, root, target);
        return new Point(result.getX(), result.getY());
    }

    private Point nearestHelper(Node currNode, Node bestNode, Point target) {
        if (currNode == null) {
            return bestNode;
        }

        double bestDistance = Point.distance(bestNode, target);
        double currentDist = Point.distance(currNode, target);

        if (Double.compare(currentDist, bestDistance) <= 0) {
            bestNode = currNode;
        }

        Node GoodSide;
        Node BadSide;
        double cmp = twoDotDistance(currNode, target, currNode.getProperty());
        if (cmp < 0) {
            GoodSide = currNode.getRight();
            BadSide = currNode.getLeft();
        } else {
            GoodSide = currNode.getLeft();
            BadSide = currNode.getRight();
        }
        // best part:
        bestNode = (Node) nearestHelper(GoodSide, bestNode, target);

        // bad part:
        if (isWorthSearch(BadSide, target, bestDistance)) {
            bestNode = (Node) nearestHelper(BadSide, bestNode, target);
        }

        return bestNode;
    }

    /* Check whether the badSide intersects with the circle that,
       centred at target point with radius of square distance between
       target point and best point. If intersects, then the badSide is
       worth looking. */
    private boolean isWorthSearch(Node p, Point target, double DistToBest) {
        if (p == null || target == null) {
            return false;
        }
        double distToBad;
        if (p.getProperty() != HORIZON) {
            distToBad = Point.distance(new Point(p.getX(), target.getY()), target);
        } else {
            distToBad = Point.distance(new Point(target.getX(), p.getY()), target);
        }
        return Double.compare(distToBad, DistToBest) <= 0;
    }

    /* Return negative if specific coordinate value of p1, or return positive. */
    private double twoDotDistance(Point p1, Point p2, boolean property) {
        if (property == HORIZON) {
            return p1.getX() - p2.getX();
        } else {
            return p1.getY() - p2.getY();
        }
    }
}
