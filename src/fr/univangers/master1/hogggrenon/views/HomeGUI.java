package fr.univangers.master1.hogggrenon.views;

import javax.swing.*;
import java.awt.*;

public class HomeGUI extends JFrame {

    public HomeGUI() {

        // Fenêtre du menu

        // Panel principal, regroupant différents autre Panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel d'en-tête
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Système expert de Jack et Guillaume");
        headingPanel.add(headingLabel);

        // Création du panel avec les contraites du layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        // Eléments du Panel
        JTextField userNameTxt = new JTextField(15);
        JButton button = new JButton("Domaine prédéfini");
        JButton button2 = new JButton("Nouveau domaine");

        String[] elements = { "Musique" };
        JComboBox<String> liste1 = new JComboBox<>(elements);

        JLabel liste = new JLabel("Domaine prédéfini : ");

        constr.insets = new Insets(5, 10, 5, 10);
        constr.anchor = GridBagConstraints.CENTER;
        constr.gridy = 1;
        constr.gridx = 0;
        constr.gridwidth = 1;

        panel.add(liste, constr);
        constr.gridx = 1;
        panel.add(liste1, constr);
        constr.gridwidth = 1;
        constr.gridx = 0;
        constr.gridy = 2;

        constr.insets = new Insets(25, 10, 10, 10); // Margins
        panel.add(button, constr);
        constr.gridx = 1;
        panel.add(button2, constr);
        constr.gridwidth = 2;
        constr.gridx = 0;
        constr.gridy = 3;

        // Choix d'un domaine prédéfini
        button.addActionListener(e -> {
            this.dispose();
        });

        // Choix d'un domaine à créer
        button2.addActionListener(e -> {
            this.dispose();
            new NewBaseGUI();
        });

        mainPanel.add(headingPanel);
        mainPanel.add(panel);

        // Création de la fenêtre
        this.setTitle("Système expert - Accueil");
        this.add(mainPanel);
        this.pack();
        this.setSize(550, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
