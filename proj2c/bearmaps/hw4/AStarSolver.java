package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private Hashtable<Vertex, Double> disTo;
    private Hashtable<Vertex, Vertex> edgeTo;
    private Hashtable<Vertex, Double> heuDisTo;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double totalWeight;
    private double timeSpent;
    private int totalNum;

    /*  Constructor which finds the solution, computing everything necessary
        for all other methods
        to return their results in constant time.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        totalNum = 0;
        solution = new LinkedList<>();
        edgeTo = new Hashtable<>();
        outcome = SolverOutcome.SOLVED; // serve as a flag.
        Stopwatch st = new Stopwatch();
        disTo = new Hashtable<>();
        heuDisTo = new Hashtable<>();
        ArrayHeapMinPQ<Vertex> MinPQ = new ArrayHeapMinPQ<>();

        // Initial MinPQ by adding first vertex.
        MinPQ.add(start, 0);
        heuDisTo.put(start, 0.0);
        disTo.put(start, 0.0);

        Vertex mark; // start must be immutable.

        while (true) {
            timeSpent = st.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            if (MinPQ.isEmpty()) {
                outcome = SolverOutcome.UNSOLVABLE;
                break;
            }
            
            mark = MinPQ.removeSmallest();
            totalNum += 1;

            if (mark.equals(end)) {
                break;
            }
            for (WeightedEdge<Vertex> neighbor : input.neighbors(mark)) {
                release(neighbor, MinPQ, input, end);
            }
        }
        if (outcome == SolverOutcome.SOLVED) {
            SolutionCollector(start, end);
            totalWeight = disTo.get(end);
        }
    }

    private void SolutionCollector(Vertex start, Vertex end) {
        Vertex p = end;
        Stack<Vertex> temp = new Stack<>();
        temp.add(p);
        while (!p.equals(start)) {
            p = edgeTo.get(p);
            temp.add(p);
        }
        while (!temp.isEmpty()) {
            solution.add(temp.pop());
        }
    }

    /* Update new vertex. */
    private void release(
            WeightedEdge<Vertex> v,
            ArrayHeapMinPQ<Vertex> PQ,
            AStarGraph<Vertex> input,
            Vertex end) {

        Vertex origin = v.from();
        Vertex object = v.to();
        Double heuristic = input.estimatedDistanceToGoal(object, end);
        Double NodeWeight = v.weight() + disTo.get(origin);
        Double relaxWeight = v.weight() + heuDisTo.get(origin) + heuristic;

        if (PQ.contains(object)) {
            if (relaxWeight.compareTo(disTo.get(object)) < 0) {
                disTo.put(object, NodeWeight);
                edgeTo.put(object, origin);
                heuDisTo.put(object, relaxWeight);
                PQ.changePriority(object, relaxWeight);
            }
        } else if (!disTo.containsKey(object)) {
            PQ.add(object, relaxWeight);
            disTo.put(object, NodeWeight);
            edgeTo.put(object, origin);
            heuDisTo.put(object, relaxWeight);
        }
    }

    /*
    Returns one of SolverOutcome.SOLVED,
    SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    /*
    A list of vertices corresponding to a solution.
    Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return solution = new LinkedList<>();
        }
        return solution;
    }

    /*
    The total weight of the given solution, taking into account edge weights.
    Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.TIMEOUT || outcome == SolverOutcome.UNSOLVABLE) {
            return 0;
        }
        return totalWeight;
    }

    /* The total number of priority queue dequeue operations. */
    @Override
    public int numStatesExplored() {
        return totalNum;
    }

    /*  The total time spent in seconds by the constructor. */
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
