import java.util.LinkedList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> solvedBears;
    private List<Bed> solvedBeds;

    /*  create two list of beds,
        compare with bear, put less bed in one list,
        put big in another
        new deleted bear compare with new deleted bed,
        if bed less than last one, search list which stored
        smaller bears.
     */

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        solvedBears = new LinkedList<>();
        solvedBeds = new LinkedList<>();
        BuBSolverHelper(bears, beds, beds, beds.get(0));
    }

    private void BuBSolverHelper(
            List<Bear> bears,
            List<Bed> lessBeds,
            List<Bed> largerBeds,
            Bed last) {
        if (lessBeds.size() == 0 || largerBeds.size() == 0) {
            return;
        }

        List<Bed> less = new LinkedList<>();
        List<Bed> larger = new LinkedList<>();
        List<Bed> beds;
        Bed lastBed = last;

        Bear bear = bears.remove(0);

        if (bear.compareTo(last) < 0) {
            beds = lessBeds;
        } else {
            beds = largerBeds;
        }

        for (Bed bed : beds) {
            if (bear.compareTo(bed) > 0) {
                less.add(bed);
            } else if (bear.compareTo(bed) == 0) {
                lastBed = bed;
                solvedBeds.add(bed);
                solvedBears.add(bear);
            } else {
                larger.add(bed);
            }
        }

        BuBSolverHelper(bears, less, larger, lastBed);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
