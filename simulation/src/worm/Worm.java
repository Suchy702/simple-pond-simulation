package worm;

import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;
import java.util.Random;

public class Worm extends PondObject {
    public Worm(Position birth_pos){
        super(birth_pos);
        this.energy = WORM_ENERGY_VAL;
        this.type_name = WORM_NAME;
    }

    @Override
    public Position desire_move(ArrayList<PondObject> seen_objects) {
        Random rand = new Random();
        int x_new = -1 + rand.nextInt(3);
        int x_new2 = position.x+x_new;
        int y_new= position.y+1;
        this.position = new Position(x_new2,y_new);
        return position;
    }

}
