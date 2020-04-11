package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private Hashtable<Vertex, Double> disTo;
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
        outcome = SolverOutcome.SOLVED; // serve as a flag.
        Stopwatch st = new Stopwatch();
        disTo = new Hashtable<>();
        ArrayHeapMinPQ<Vertex> MinPQ = new ArrayHeapMinPQ<>();

        // Initial MinPQ by adding first vertex.
        MinPQ.add(start, 0);
        disTo.put(start, input.estimatedDistanceToGoal(start, end));

        while (true) {
            timeSpent = st.elapsedTime();
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = new LinkedList<>();
                totalWeight = 0;
                break;
            }
            if (MinPQ.isEmpty()) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution = new LinkedList<>();
                totalWeight = 0;
                break;
            }
            
            start = MinPQ.removeSmallest();
            solution.add(start);
            totalNum += 1;
            
            if (start == end) {
                break;
            }
            for (WeightedEdge<Vertex> neighbor : input.neighbors(start)) {
                release(neighbor, MinPQ, input, end);
            }
            totalWeight = disTo.get(start);
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
        double relaxWeight = v.weight() + disTo.get(origin) + heuristic;

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
