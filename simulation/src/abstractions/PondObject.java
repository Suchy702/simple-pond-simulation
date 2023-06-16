package abstractions;

import java.util.ArrayList;

public abstract class PondObject implements Constants{
    public int id = -1;
    public Position position = new Position(-1,-1);
    public String type_name = DEFAULT_POND_OBJECT_NAME;
    public int energy = 0;

    public PondObject(){
    }

    public PondObject(Position birth_pos){
        this.position = birth_pos;
    }
    public abstract Position desire_move(ArrayList<PondObject> seen_objects);

    @Override
    public String toString() {
        return type_name + id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void change_position(Position pos){
        position = pos;
    }
}
