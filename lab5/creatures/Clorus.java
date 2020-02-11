package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    // red color
    private int r;

    // green color
    private int g;

    // blue color
    private int b;

    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     * @param e
     */
    public Clorus(double e) {
        super("clorus");

        //default color
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus c = new Clorus(energy / 2);
        energy /= 2;
        return c;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        ArrayDeque<Direction> emptyNeighbors = new ArrayDeque<>();
        ArrayDeque<Direction> enemyNeighbors = new ArrayDeque<>();
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.addFirst(key);
            }
        }
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("plip")) {
                enemyNeighbors.addFirst(key);
            }
        }
        if (enemyNeighbors.size() > 0) {
            Direction DirectionOfObj = randomEntry(enemyNeighbors);
            return new Action(Action.ActionType.ATTACK, DirectionOfObj);
        }
        Direction direct = randomEntry(emptyNeighbors);
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, direct);
        }
        return new Action(Action.ActionType.MOVE, direct);
    }

    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }
}
