package alg;

import abstractions.Constants;
import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;
import java.util.Random;

public class Alg extends PondObject {
    public Alg(Position birth_pos) {
        super(birth_pos);
        this.type_name = ALG_NAME;
    }
    @Override
    public Position desire_move(ArrayList<PondObject> seen_objects) {
        return null;
    }

    public ArrayList<Leaf> create_new_leaves() {
        ArrayList<Leaf> newLeaves = new ArrayList<>();
        int numberOfLeaves = 2;
        int spread = 2;
        for (int i = 0; i < numberOfLeaves; i++) {
            Random rand = new Random();
            int x_new = -spread + rand.nextInt(spread);
            int x_new2 = position.x + x_new;
            rand = new Random();
            int y_new = -spread + rand.nextInt(spread);
            int y_new2 = position.y + y_new;
            Leaf leaf = new Leaf(new Position (x_new2,y_new2));
            newLeaves.add(leaf);
        }
        return newLeaves;
    }
}
