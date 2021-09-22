package fr.univangers.master1.hogggrenon.interfaces;

import javax.swing.*;
import java.awt.*;

public class SystemGUI extends JFrame {

    // TODO : A REFAIRE
    public SystemGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));



        this.setTitle("Système expert");
        this.add(mainPanel);
        this.pack();
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
