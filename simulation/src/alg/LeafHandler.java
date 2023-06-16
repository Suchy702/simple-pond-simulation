package alg;

import abstractions.Handler;
import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;

public class LeafHandler extends Handler{
    @Override
    public void remove_useless() {
        ArrayList<PondObject> list = get_all_objects();
        for (PondObject a : list) {
            if (a.position.y == 0) {
                remove_one(a);
            }
        }
    }

    public void move_all() {
        ArrayList<PondObject> empty = new ArrayList<>();
        for (PondObject leaf : get_all_objects()) {
            Position desire_pos = leaf.desire_move(empty);
            leaf.change_position(trim_desire_pos(desire_pos));
        }
    }
}
