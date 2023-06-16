package fish;

import abstractions.Position;

public class PassiveFish extends Fish{
    public PassiveFish(Position birth_pos) {
        super(birth_pos);
        this.type_name = PASSIVE_FISH_NAME;
        this.speed = 2;
    }
}
