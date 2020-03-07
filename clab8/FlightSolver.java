import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private ArrayList<Flight> Flights;
    private PriorityQueue<Flight> StartQueue;
    private PriorityQueue<Flight> EndQueue;

    public FlightSolver(ArrayList<Flight> flights) {
        this.Flights = flights;
        Comparator<Flight> startTimeComparator = Comparator.comparingInt(f -> f.startTime);
        Comparator<Flight> EndTimeComparator = Comparator.comparingInt(f -> f.endTime);
        StartQueue = new PriorityQueue<>(Flights.size(), startTimeComparator);
        EndQueue = new PriorityQueue<>(Flights.size(), EndTimeComparator);
    }

    public int solve() {
        StartQueue.addAll(Flights);
        EndQueue.addAll(Flights);
        int current = 0;
        int temp = 0;
        while (StartQueue.peek() != null) {
            Flight start = StartQueue.peek();
            Flight end = EndQueue.peek();
            if (start.startTime <= end.endTime) {
                temp += start.passengers;
                StartQueue.remove();
            } else {
                temp -= end.passengers;
                EndQueue.remove();
            }
            if (temp > current) {
                current = temp;
            }
        }
        return current;
    }
}
