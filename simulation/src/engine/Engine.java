package engine;

import abstractions.Constants;
import abstractions.Handler;
import abstractions.PondObject;
import alg.AlgHandler;
import alg.LeafHandler;
import data_collector.DataCollector;
import fish.FishHandler;
import worm.WormHandler;

import java.util.*;

public class Engine implements Constants {
    public int cycle = 0;

    int food_freq_intensity;
    int aggressive_fish_speed_compare_to_passive;

    public WormHandler worm_handler;
    public AlgHandler alg_handler;
    public FishHandler fish_handler;
    public LeafHandler leaf_handler;
    public List<Handler> handlers;

    public DataCollector data_collector = new DataCollector();

    private int worm_create_freq;
    private int leaves_create_freq;

    private ArrayList<ArrayList<ArrayList<PondObject>>> create_empty_pond(){
        ArrayList<ArrayList<ArrayList<PondObject>>> pond = new ArrayList<>();

        for (int i = 0; i < POND_HEIGHT; i++){
            pond.add(new ArrayList<>());
            for (int j = 0; j < POND_WIDTH; j++)
                pond.get(i).add(new ArrayList<>());
        }

        return pond;
    }

    private void create_handlers(){
        worm_handler = new WormHandler();
        alg_handler = new AlgHandler();
        fish_handler = new FishHandler(aggressive_fish_speed_compare_to_passive);
        leaf_handler = new LeafHandler();
        handlers = Arrays.asList(worm_handler, alg_handler, fish_handler, leaf_handler);
    }

    public Engine(int aggressive_fish_speed_compare_to_passive, int food_freq_intensity){
        this.aggressive_fish_speed_compare_to_passive = aggressive_fish_speed_compare_to_passive;
        this.food_freq_intensity = food_freq_intensity;
    }

    private void add_all_objects_to_pond_representation(ArrayList<ArrayList<ArrayList<PondObject>>> pond){
        for (Handler handler : handlers){
            for (PondObject object : handler.get_all_objects()){
                int x = object.position.x;
                int y = object.position.y;
                pond.get(y).get(x).add(object);
            }
        }
    }

    private void print_pond_in_terminal(ArrayList<ArrayList<ArrayList<PondObject>>> pond){
        for (int i = 0; i < POND_HEIGHT; i++){
            for (int j = 0; j < POND_WIDTH; j++){
                System.out.print("[");
                for (PondObject object : pond.get(i).get(j))
                    System.out.print(object.toString() + " ");
                System.out.print("] ");
            }
            System.out.print("\n");
        }
    }

    public void print_pond(){
        ArrayList<ArrayList<ArrayList<PondObject>>> pond = create_empty_pond();
        add_all_objects_to_pond_representation(pond);
        print_pond_in_terminal(pond);
        System.out.println();
    }

    private void move_worms_and_leaves(){
        leaf_handler.move_all();
        worm_handler.move_all();
    }

    private void remove_objects_that_should_not_be_in_pond(){
        fish_handler.spoil_fish_energy();
        worm_handler.remove_useless();
        leaf_handler.remove_useless();
        fish_handler.remove_useless();
    }

    private void create_new_leaves(){
        leaf_handler.add_many(alg_handler.create_new_leaves());
    }

    private void create_new_leaves_and_worms(int cycle){
        if (cycle % worm_create_freq == 0)
            worm_handler.create_new_worms();
        if (cycle % leaves_create_freq == 0)
            create_new_leaves();
    }


    private void passive_fish_move(){
        ArrayList<PondObject> food = new ArrayList<>(leaf_handler.get_all_objects());
        food.addAll(worm_handler.get_all_objects());

        ArrayList<PondObject> eaten_objects = fish_handler.passive_fish_move(food);

        for (PondObject object : eaten_objects)
            if (Objects.equals(object.type_name, WORM_NAME))
                worm_handler.remove_one(object);
            else
                leaf_handler.remove_one(object);
    }


    private void collect_data_of_one_cycle(){
        Map<String, Integer> data_of_one_cycle = new HashMap<>();
        data_of_one_cycle.put("Cycle", cycle);
        data_of_one_cycle.put("NumOfAggressiveFish", fish_handler.get_number_of_aggressive_fish());
        data_of_one_cycle.put("NumOfPassiveFish", fish_handler.get_number_of_passive_fish());
        data_of_one_cycle.put("NumOfWorm", worm_handler.get_number_of_objects());
        data_of_one_cycle.put("NumOfAlg", alg_handler.get_number_of_objects());
        data_of_one_cycle.put("NumOfLeaf", leaf_handler.get_number_of_objects());
        data_collector.collect_one_cycle_data(data_of_one_cycle);
    }

    private void set_food_freq(){
        worm_create_freq = food_freq_intensity;
        leaves_create_freq = food_freq_intensity;
    }


    public void do_one_cycle(){
        move_worms_and_leaves();
        passive_fish_move();
        fish_handler.aggressive_fish_move();
        fish_handler.fish_breeding();
        remove_objects_that_should_not_be_in_pond();
        create_new_leaves_and_worms(cycle);
    }

    private void preparations_for_simulation(){
        set_food_freq();
        create_handlers();
    }

    private void ongoing_simulation(){
        do_one_cycle();
        print_pond();
        collect_data_of_one_cycle();
        cycle++;
    }

    private void observations_after_simulations(){
        data_collector.save_data_to_file();
        data_collector.create_chart();
    }

    public void run(){
        preparations_for_simulation();
        while (cycle < CYCLE_LIMIT)
            ongoing_simulation();
        observations_after_simulations();
    }
}