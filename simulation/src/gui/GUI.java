package gui;

import engine.Engine;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GUI {
    int food_freq_intensity;
    int aggressive_fish_speed_compare_to_passive;

    public void start_simulation_gui(){
        JFrame frame = new JFrame("Wykres populacji ryb");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 200));

        // Pobranie wymiarów ekranu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Ustawienie położenia okna na środku ekranu
        int windowWidth = frame.getPreferredSize().width;
        int windowHeight = frame.getPreferredSize().height;
        int windowX = (screenWidth - windowWidth) / 2;
        int windowY = (screenHeight - windowHeight) / 2;
        frame.setBounds(windowX, windowY, windowWidth, windowHeight);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Dodanie suwaka 1
        JPanel slider1Panel = new JPanel();
        JLabel slider1Label = new JLabel("Inensywnosc pokarmu: ");
        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        slider1.setMajorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1Panel.add(slider1Label);
        slider1Panel.add(slider1);

        // Dodanie suwaka 2
        JPanel slider2Panel = new JPanel();
        JLabel slider2Label = new JLabel("Szybkosc drapieznika: ");
        JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        slider2.setMajorTickSpacing(1);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2Panel.add(slider2Label);
        slider2Panel.add(slider2);

        // Dodanie przycisku "Rozpocznij symulację"
        JButton startButton = new JButton("Rozpocznij symulację");
        startButton.addActionListener(e -> {
            // Zamykanie okna po kliknięciu przycisku
            frame.dispose();
            food_freq_intensity = slider1.getValue();
            aggressive_fish_speed_compare_to_passive = slider2.getValue();
            Engine engine = new Engine(2, 3);
            engine.run();
        });

        // Dodanie komponentów do panelu
        panel.add(slider1Panel, BorderLayout.NORTH);
        panel.add(slider2Panel, BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);

        // Ustawienie orientacji etykiet
        slider1Label.setHorizontalAlignment(SwingConstants.CENTER);
        slider2Label.setHorizontalAlignment(SwingConstants.CENTER);

        // Dodanie panelu do okna
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

}

