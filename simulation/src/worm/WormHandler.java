package worm;

import abstractions.Handler;
import abstractions.PondObject;
import abstractions.Position;
import fish.Fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class WormHandler extends Handler {
    @Override
    public void remove_useless() {
        ArrayList<PondObject> list = get_all_objects();
        for (PondObject a : list) {
            if (a.position.y == POND_HEIGHT - 1) {
                remove_one(a);
            }
        }
    }

    public void create_new_worms() {
        int worm_number = 10;
        for (int i = 0; i < worm_number; i++) {
            Random rand = new Random();
            int x = rand.nextInt(POND_WIDTH);
            Worm worm = new Worm(new Position(x, 0));
            this.add_one(worm);
        }
    }

    private List<PondObject> get_worms() {
        List<PondObject> worms = new ArrayList<>();
        for (PondObject worm : get_all_objects())
            if (Objects.equals(worm.type_name, WORM_NAME))
                worms.add(worm);
        return worms;
    }

    public List<PondObject> get_all_worms() {
        return get_worms();
    }

    public void move_all() {
        ArrayList<PondObject> empty = new ArrayList<>();
        for (PondObject worm : get_all_worms()) {
            Position desire_pos = worm.desire_move(empty);
            worm.change_position(trim_desire_pos(desire_pos));
        }
    }
}
