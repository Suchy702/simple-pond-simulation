package alg;

import abstractions.Handler;
import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;

public class AlgHandler extends Handler {
    public AlgHandler() {
        int numberOfAlg = 6;
        for (int i = 0; i < numberOfAlg; i++) {
            Position new_pos = get_random_position(0, POND_WIDTH-1, POND_HEIGHT-1, POND_HEIGHT-1);
            add_one(new Alg(trim_desire_pos(new_pos)));
        }
    }

    @Override
    public void remove_useless() {

    }

    public ArrayList<PondObject> create_new_leaves() {
        ArrayList<PondObject> new_leaves = new ArrayList<>();

        for (PondObject alg_pond : get_all_objects())
            alg_pond.position = trim_desire_pos(alg_pond.position);


        for (PondObject alg_pond : get_all_objects()){
            Alg alg = (Alg) alg_pond;
            new_leaves.addAll(alg.create_new_leaves());
        }

        for (PondObject new_leaf : new_leaves)
            new_leaf.position = trim_desire_pos(new_leaf.position);

        return new_leaves;
    }

    public static void main(String[] args) {
        AlgHandler handler = new AlgHandler();
        ArrayList<PondObject> list = handler.get_all_objects();
        for (PondObject a: list) {
            System.out.println(a.type_name+" "+a.position.x+" "+a.position.y);
        }
    }
}
