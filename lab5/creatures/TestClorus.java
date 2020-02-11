package creatures;
import static  org.junit.Assert.*;
import org.junit.Test;

public class TestClorus {

    @Test
    public void testReplicate() {
        Clorus C = new Clorus(2);
        Clorus nC = C.replicate();

        assertEquals(C.energy(), nC.energy(), 0.01);
        assertEquals(C.energy(), 1, 0.01);
        assertEquals(nC.energy(), 1, 0.01);
    }

    @Test
    public void testAttack() {
        Clorus C = new Clorus(2);
        Plip p = new Plip(1);
        Plip np = new Plip(1);

        C.attack(p);
        assertEquals(C.energy(), 3, 0.01);

        p.stay();
        C.attack(p);
        assertEquals(4.2, C.energy(), 0.01);
    }
}
