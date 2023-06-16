package abstractions;

public interface Constants {
    int WORM_ENERGY_VAL = 10;
    int LEAF_ENERGY_VAL = 10;

    int POND_WIDTH = 20;
    int POND_HEIGHT = 20;

    int CYCLE_LIMIT = 100;

    String DEFAULT_POND_OBJECT_NAME = "O";
    String DEFAULT_FISH_NAME = "F";
    String ALG_NAME = "A";
    String LEAF_NAME = "L";
    String WORM_NAME = "W";
    String PASSIVE_FISH_NAME = "P";
    String AGGRESSIVE_FISH_NAME = "G";


    int STARTING_NUM_OF_PASSIVE_FISH = 20;
    int STARTING_NUM_OF_AGGRESSIVE_FISH = 8;

    int ENERGY_NEED_TO_BREED = 40;
    int AMOUNT_OF_ENERGY_CAUSING_DEATH = 0;
    int AMOUNT_OF_CHILDREN = 2;
    int DEFAULT_FISH_ENERGY = 20;

    int DEFAULT_FISH_SPEED = 2;
    int DEFAULT_FISH_ENERGY_SPOIL = 3;
    int DEFAULT_FISH_SIGHT = 2;
}
