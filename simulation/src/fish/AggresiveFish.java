package fish;

import abstractions.Position;

public class AggresiveFish extends Fish{
    public AggresiveFish(Position birth_pos, int speed, int energy_spoil) {
        super(birth_pos, speed, energy_spoil);
        this.type_name = AGGRESSIVE_FISH_NAME;
    }
}
