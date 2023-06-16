package fish;


import abstractions.PondObject;
import abstractions.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Fish extends PondObject {
    public int speed = DEFAULT_FISH_SPEED;
    public int energy_spoil = DEFAULT_FISH_ENERGY_SPOIL;
    public int sight = DEFAULT_FISH_SIGHT;

    public final List<Position> directions = Arrays.asList(
            new Position(1, 0), new Position(-1, 0), new Position(0, 1),
            new Position(0, -1), new Position(1, 1), new Position(1, -1),
            new Position(-1, 1), new Position(-1, -1)
    );


    public Fish(Position birth_pos){
        this.position = birth_pos;
        this.type_name = DEFAULT_FISH_NAME;
        this.energy = DEFAULT_FISH_ENERGY;
    }

    public Fish(Position birth_pos, int speed, int energy_spoil){
        this.position = birth_pos;
        this.type_name = DEFAULT_FISH_NAME;
        this.energy = DEFAULT_FISH_ENERGY;
        this.energy_spoil = energy_spoil;
        this.speed = speed;
    }

    public Fish(Fish fish){
        this.position = fish.position;
        this.energy_spoil = fish.energy_spoil;
        this.sight = fish.sight;
        this.speed = fish.speed;
        this.type_name = fish.type_name;
        this.energy = DEFAULT_FISH_ENERGY;
    }

    private Position random_move(){
        Position random_dir = directions.get(new Random().nextInt(directions.size()));
        int new_x = this.position.x + this.speed * random_dir.x;
        int new_y = this.position.y + this.speed * random_dir.y;
        return new Position(new_x, new_y);
    }

    private Position find_nearest_food(ArrayList<PondObject> food){
        Position nearest_pos = food.get(0).position;
        for (PondObject object : food)
            if (this.position.calc_distance(nearest_pos) > this.position.calc_distance(object.position))
                nearest_pos = object.position;
        return nearest_pos;
    }

    private int calc_optimal_cord_changing_to_catch_food(int diff){
        if (diff < 0)
            return Math.max(-speed, diff);
        else
            return Math.min(speed, diff);
    }

    private Position try_catch_food(Position food_pos){
        int new_x = this.position.x + calc_optimal_cord_changing_to_catch_food(food_pos.x - this.position.x);
        int new_y = this.position.y + calc_optimal_cord_changing_to_catch_food(food_pos.y - this.position.y);
        return new Position(new_x, new_y);
    }

    @Override
    public Position desire_move(ArrayList<PondObject> seen_objects) {
        if (seen_objects.size() == 0)
            return random_move();
        return try_catch_food(find_nearest_food(seen_objects));
    }

    public boolean is_alive(){
        return this.energy > AMOUNT_OF_ENERGY_CAUSING_DEATH;
    }

    public boolean is_ready_to_breed(){
        return this.energy >= ENERGY_NEED_TO_BREED;
    }

    public boolean see_food(Position food_pos){
        return Math.abs(food_pos.x - position.x) < sight && Math.abs(food_pos.y - position.y) < sight;
    }

    public List<PondObject> breed(){
        List<PondObject> children = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_CHILDREN; i++)
            children.add(new Fish(this));
        energy = AMOUNT_OF_ENERGY_CAUSING_DEATH;
        return children;
    }
}
