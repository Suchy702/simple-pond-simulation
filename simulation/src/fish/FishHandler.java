package fish;

import abstractions.Handler;
import abstractions.PondObject;
import abstractions.Position;

import java.util.*;

public class FishHandler extends Handler {
    public FishHandler(int aggressive_fish_speed_compare_to_passive) {
        for (int i = 0; i < STARTING_NUM_OF_PASSIVE_FISH; i++)
            add_one(new PassiveFish(get_random_position(0, POND_WIDTH-1, 0, POND_HEIGHT-1)));
        for (int i = 0; i < STARTING_NUM_OF_AGGRESSIVE_FISH; i++){
            Position fish_pos = get_random_position(0, POND_WIDTH-1, 0, POND_HEIGHT-1);
            int aggressive_fish_speed = DEFAULT_FISH_SPEED * aggressive_fish_speed_compare_to_passive;
            add_one(new AggresiveFish(fish_pos, aggressive_fish_speed, DEFAULT_FISH_ENERGY_SPOIL));

        }
    }

    @Override
    public void remove_useless() {
        for (PondObject fish_obj : this.get_all_objects()){
            Fish fish = (Fish) fish_obj;
            if (!fish.is_alive())
                this.remove_one(fish);
        }
    }

    private List<PondObject> get_fishes_by_type_name(String type_name){
        List<PondObject> passive_fishes = new ArrayList<>();
        for (PondObject fish : get_all_objects())
            if (Objects.equals(fish.type_name, type_name))
                passive_fishes.add(fish);
        return passive_fishes;
    }

    public List<PondObject> get_all_passive_fish(){
        return get_fishes_by_type_name(PASSIVE_FISH_NAME);
    }

    public List<PondObject> get_all_aggressive_fish(){
        return get_fishes_by_type_name(AGGRESSIVE_FISH_NAME);
    }

    public int get_number_of_passive_fish(){
        return get_all_passive_fish().size();
    }

    public int get_number_of_aggressive_fish(){
        return get_all_aggressive_fish().size();
    }

    private List<PondObject> find_seen_objects(List<PondObject> food, Fish fish){
        List<PondObject> seen_objects = new ArrayList<>();
        for (PondObject meal : food)
            if (fish.see_food(meal.position))
                seen_objects.add(meal);
        return seen_objects;
    }

    private void fish_move(List<PondObject> food, List<Fish> moving_fishes){
        for (Fish fish : moving_fishes){
            Position desire_pos = fish.desire_move((ArrayList<PondObject>) find_seen_objects(food, fish));
            fish.change_position(trim_desire_pos(desire_pos));
        }
    }

    private HashMap<Position, ArrayList<PondObject>> divide_by_pos(ArrayList<PondObject> food, ArrayList<Fish> fishes){
        ArrayList<PondObject> food_and_fishes = new ArrayList<>(food);
        food_and_fishes.addAll(fishes);

        HashMap<Position, ArrayList<PondObject>> objects_divided_by_pos = new HashMap<>();
        for (PondObject obj : food_and_fishes){
            if (!objects_divided_by_pos.containsKey(obj.position))
                objects_divided_by_pos.put(obj.position, new ArrayList<>());
            objects_divided_by_pos.get(obj.position).add(obj);
        }

        return objects_divided_by_pos;
    }

    private ArrayList<PondObject> eating_at_cell(ArrayList<PondObject> objects, String eater_type){
        ArrayList<PondObject> eaten_objects = new ArrayList<>();
        ArrayList<Fish> eaters = new ArrayList<>();
        int eaten_energy_val = 0;

        for (PondObject object : objects){
            if (Objects.equals(object.type_name, eater_type))
                eaters.add((Fish) object);
            else{
                eaten_objects.add(object);
                eaten_energy_val += object.energy;
            }
        }

        if (eaten_objects.size() == 0 || eaters.size() == 0)
            return new ArrayList<>();

        for (Fish eater : eaters)
            eater.energy += eaten_energy_val / eaters.size();

        return eaten_objects;
    }

    private ArrayList<PondObject> fish_eating(ArrayList<PondObject> food, ArrayList<Fish> fishes, String eater_type){
        ArrayList<PondObject> eaten_objects = new ArrayList<>();
        for (ArrayList<PondObject> objects_in_one_cell : divide_by_pos(food, fishes).values())
            eaten_objects.addAll(eating_at_cell(objects_in_one_cell, eater_type));
        return eaten_objects;
    }

    private List<Fish> cast_list_to_fish(List<PondObject> objects){
        List<Fish> fishes = new ArrayList<>();
        for (PondObject obj : objects)
            fishes.add((Fish) obj);
        return fishes;
    }

    public ArrayList<PondObject> passive_fish_move(ArrayList<PondObject> food) {
        fish_move(food, cast_list_to_fish(get_all_passive_fish()));
        return fish_eating(food, (ArrayList<Fish>) cast_list_to_fish(get_all_passive_fish()), PASSIVE_FISH_NAME);
    }

    public void aggressive_fish_move() {
        fish_move(get_all_passive_fish(), cast_list_to_fish(get_all_aggressive_fish()));

        ArrayList<PondObject> food = (ArrayList<PondObject>) get_all_passive_fish();
        ArrayList<Fish> fishes = (ArrayList<Fish>) cast_list_to_fish(get_all_aggressive_fish());
        ArrayList<PondObject> eaten_fishes = fish_eating(food, fishes, AGGRESSIVE_FISH_NAME);
        remove_many(eaten_fishes);
    }

    public void fish_breeding() {
        for (PondObject fish_obj : get_all_objects()){
            Fish fish = (Fish) fish_obj;
            if (fish.is_ready_to_breed())
                add_many(fish.breed());
        }

    }

    public void spoil_fish_energy() {
        for (PondObject fish_obj : get_all_objects()){
            Fish fish = (Fish) fish_obj;
            fish.energy -= fish.energy_spoil;
        }
    }
}
