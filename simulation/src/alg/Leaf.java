package alg;

import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;


public class Leaf extends PondObject {
    public Leaf(){
        energy = LEAF_ENERGY_VAL;
    }

    public Leaf(Position birth_pos){
        super(birth_pos);
        this.energy = LEAF_ENERGY_VAL;
        this.type_name = LEAF_NAME;
    }

    @Override
    public Position desire_move(ArrayList<PondObject> seen_objects) {
        int x_new = position.x;
        int y_new = position.y-1;
        return new Position(x_new, y_new);
    }
}


