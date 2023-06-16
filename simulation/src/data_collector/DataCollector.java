package data_collector;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import abstractions.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

public class DataCollector implements Constants {
    private final ArrayList<Map<String, Integer>> data_collection = new ArrayList<>();

    public void collect_one_cycle_data(Map<String, Integer> data_of_one_cycle){
        data_collection.add(data_of_one_cycle);
    }

    public void save_data_to_file(){
        try (PrintWriter writer = new PrintWriter("data.txt")) {
            writer.println("NumOfWorm\tNumOfLeaf\tNumOfAggressiveFish\tNumOfAlg\tNumOfPassiveFish\tCycle");
            for (Map<String, Integer> one_cycle_data : data_collection){
                for (Integer val : one_cycle_data.values())
                    writer.print(val + "\t");
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create_chart() {
        ArrayList<Integer> xData = new ArrayList<>(CYCLE_LIMIT);
        ArrayList<Integer> yData1 = new ArrayList<>(CYCLE_LIMIT);
        ArrayList<Integer> yData2 = new ArrayList<>(CYCLE_LIMIT);

        for (int i = 0; i < CYCLE_LIMIT; i++){
            xData.add(0);
            yData1.add(0);
            yData2.add(0);
        }

        for (int i = 0; i < CYCLE_LIMIT; i++){
            xData.set(i, i);
        }

        for (Map<String, Integer> one_cycle : data_collection){
            yData1.set(one_cycle.get("Cycle"), one_cycle.get("NumOfPassiveFish"));
            yData2.set(one_cycle.get("Cycle"), one_cycle.get("NumOfAggressiveFish"));
        }

        DefaultXYDataset dataset = new DefaultXYDataset();

        double[][] data1 = new double[2][xData.size()];
        double[][] data2 = new double[2][xData.size()];

        for (int i = 0; i < xData.size(); i++) {
            data1[0][i] = xData.get(i);
            data1[1][i] = yData1.get(i);

            data2[0][i] = xData.get(i);
            data2[1][i] = yData2.get(i);
        }

        dataset.addSeries("Ryby pasywne", data1);
        dataset.addSeries("Ryby agresywne", data2);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres populacji ryb", // tytuł wykresu
                "Indeks", // etykieta osi X
                "Wartość", // etykieta osi Y
                dataset, // dane
                PlotOrientation.VERTICAL, // orientacja wykresu
                true, // czy wyświetlać legendę
                true, // czy wyświetlać tooltips
                false // czy wyświetlać URL
        );

        ChartFrame frame = new ChartFrame("Wykres liniowy", chart);
        frame.pack();
        frame.setVisible(true);
        }

    public static void main(String[] args) {

    }

}
