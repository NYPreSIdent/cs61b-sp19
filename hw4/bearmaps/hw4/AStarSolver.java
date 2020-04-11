package bearmaps.hw4;

import bearmaps.hw4.AStarGraph;
import bearmaps.hw4.ShortestPathsSolver;
import bearmaps.hw4.SolverOutcome;
import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import javax.crypto.AEADBadTagException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private Hashtable<Vertex, Double> disTo;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double TotalWeight;
    private double timeSpent;
    private int TotalNum;

    /*  Constructor which finds the solution, computing everything necessary
        for all other methods
        to return their results in constant time.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        TotalNum = 0;
        solution = new LinkedList<>();
        outcome = SolverOutcome.SOLVED; // serve as a flag.
        Stopwatch st = new Stopwatch();
        disTo = new Hashtable<>();
        ArrayHeapMinPQ<Vertex> MinPQ = new ArrayHeapMinPQ<>();

        // Initial MinPQ by adding first vertex.
        MinPQ.add(start, 0);
        disTo.put(start, input.estimatedDistanceToGoal(start, end));

        while (true) {
            if (MinPQ.isEmpty()) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution = new LinkedList<>();
                break;
            }
            start = MinPQ.removeSmallest();
            solution.add(start);
            if (start == end) {
                break;
            }
            for (WeightedEdge<Vertex> neighbor : input.neighbors(start)) {
                release(neighbor, MinPQ, input, end);
            }
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
        double heuristic = input.estimatedDistanceToGoal(object, end);
        double relaxWeight = v.weight() + heuristic + disTo.get(origin);

        if (PQ.contains(object)) {
            if (relaxWeight < disTo.get(object)) {
                disTo.put(object, relaxWeight);
                PQ.changePriority(object, relaxWeight);
            }
        } else if (!disTo.containsKey(object)) {
            PQ.add(object, relaxWeight);
            disTo.put(object, relaxWeight);
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
        return solution;
    }

    /*
    The total weight of the given solution, taking into account edge weights.
    Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    @Override
    public double solutionWeight() {
        return TotalWeight;
    }

    /* The total number of priority queue dequeue operations. */
    @Override
    public int numStatesExplored() {
        return TotalNum;
    }

    /*  The total time spent in seconds by the constructor. */
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
