package abstractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class Handler implements Constants {
    public HashMap<Integer, PondObject> objects = new HashMap<>();
    public int count_objects_id = 0;

    public void add_one(PondObject object_to_add){
        object_to_add.setId(count_objects_id);
        objects.put(object_to_add.id, object_to_add);
        this.count_objects_id++;
    }

    public void add_many(List<PondObject> objects_to_add){
        for (PondObject object_to_add : objects_to_add)
            add_one(object_to_add);
    }

    public void remove_one(PondObject object_to_remove){
        objects.remove(object_to_remove.id);
    }

    public void remove_many(Iterable<PondObject> objects_to_remove){
        for (PondObject object_to_remove : objects_to_remove)
            remove_one(object_to_remove);
    }

    public ArrayList<PondObject> get_all_objects(){
        return new ArrayList<>(this.objects.values());
    }

    public int get_number_of_objects() {
        return objects.size();
    }

    public Position trim_desire_pos(Position desire_pos){
        int new_x = Math.max(Math.min(desire_pos.x, POND_WIDTH-1), 0);
        int new_y = Math.max(Math.min(desire_pos.y, POND_HEIGHT-1), 0);
        return new Position(new_x, new_y);
    }

    public Position get_random_position(int x_min, int x_max, int y_min, int y_max){
        Random ran = new Random();
        int x = ran.nextInt(x_max+1) + x_min;
        int y = ran.nextInt(y_max+1) + y_min;
        return new Position(x, y);
    }

    public abstract void remove_useless();
}
