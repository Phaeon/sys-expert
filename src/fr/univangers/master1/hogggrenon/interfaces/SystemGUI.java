package fr.univangers.master1.hogggrenon.interfaces;

import fr.univangers.master1.hogggrenon.utils.FactBase;
import fr.univangers.master1.hogggrenon.utils.FactList;
import fr.univangers.master1.hogggrenon.utils.StrategyUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.*;

public class SystemGUI extends JFrame {

    // TODO : A REFAIRE
    public SystemGUI(Map<String, Boolean> factList) {
        JPanel framePanel = new JPanel();
        framePanel.setLayout(new GridBagLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints global = new GridBagConstraints();

        // Élements
        JLabel listFactsLabel = new JLabel("Liste des faits", JLabel.CENTER),
                baseFactsLabel = new JLabel("Base de faits", JLabel.CENTER),
                goalLabel = new JLabel("BUTS", JLabel.RIGHT),
                arrowLabel = new JLabel("  =>  ", JLabel.CENTER),
                outputLabel = new JLabel("Sortie :", JLabel.CENTER),
                actionLabel = new JLabel("CHAÎNAGE", JLabel.CENTER);

        JTextArea output = new JTextArea();
        output.setRows(7);
        output.setColumns(50);
        output.setEditable(false);

        JButton addButton = new JButton("Ajouter \u2714"),
                removeButton = new JButton("Retirer \u2716"),
                actionButton = new JButton("Exécuter"),
                metaButton = new JButton("Méta-Règles"),
                terminate = new JButton("Terminer");

        metaButton.setEnabled(false);

        ButtonGroup group = new ButtonGroup();

        JRadioButton bt1 = new JRadioButton("Avant (Largeur)"),
                bt2 = new JRadioButton("Avant (Profondeur)"),
                bt3 = new JRadioButton("Arrière");

        bt1.setBackground(Color.CYAN);
        bt2.setBackground(Color.CYAN);
        bt3.setBackground(Color.CYAN);

        bt1.setSelected(true);

        group.add(bt1);
        group.add(bt2);
        group.add(bt3);

        DefaultListModel<String> defA = new DefaultListModel<>(), defB = new DefaultListModel<>();
        JList<String> listFacts = new JList<>(defA),
                baseFacts = new JList<>(defB);

        defA.addAll(factList.keySet());

        JScrollPane scrollFacts = new JScrollPane(listFacts),
                scrollBase = new JScrollPane(baseFacts);

        scrollFacts.setPreferredSize(new Dimension(250, 150));
        scrollFacts.setMinimumSize(new Dimension(250, 150));
        scrollFacts.setMaximumSize(new Dimension(250, 150));

        scrollBase.setPreferredSize(new Dimension(250, 150));
        scrollBase.setMinimumSize(new Dimension(250, 150));
        scrollBase.setMaximumSize(new Dimension(250, 150));

        JTextField goalField = new JTextField(12);

        {
            GridBagConstraints local = new GridBagConstraints();
            local.insets = new Insets(5, 5, 5, 5);

            local.gridx = 0;
            local.gridy = 0;
            local.gridwidth = 3;

            mainPanel.add(listFactsLabel, local);

            local.gridx = 4;

            mainPanel.add(baseFactsLabel, local);

            local.gridx = 0;
            local.gridy = 1;
            local.gridheight = 3;

            mainPanel.add(scrollFacts, local);

            local.gridx = 3;
            local.gridwidth = 1;

            mainPanel.add(arrowLabel, local);

            local.gridx = 4;
            local.gridy = 1;
            local.gridheight = 3;
            local.gridwidth = 3;

            mainPanel.add(scrollBase, local);

            local.gridx = 2;
            local.gridy = 4;
            local.gridwidth = 1;
            local.gridheight = 1;

            mainPanel.add(addButton, local);

            local.gridx = 6;

            mainPanel.add(removeButton, local);

            local.gridx = 0;
            local.gridy = 5;
            local.gridwidth = 7;

            mainPanel.add(outputLabel, local);

            local.gridy = 6;

            local.insets = new Insets(20, 0, 40 , 0);

            mainPanel.add(output, local);


        }

        mainPanel.setPreferredSize(new Dimension(700, 500));
        mainPanel.setMinimumSize(new Dimension(700, 500));
        mainPanel.setMaximumSize(new Dimension(700, 500));

        JPanel actionPanel = new JPanel(new GridBagLayout());

        {
            GridBagConstraints local = new GridBagConstraints();

            local.insets = new Insets(5, 0, 5, 0);
            local.gridx = 0;
            local.gridy = 0;

            actionPanel.add(actionLabel, local);

            local.gridy = 1;
            local.anchor = GridBagConstraints.WEST;

            actionPanel.add(bt1, local);

            local.gridy = 2;

            actionPanel.add(bt2, local);

            local.gridy = 3;

            actionPanel.add(bt3, local);

            local.gridy = 4;
            local.anchor = GridBagConstraints.CENTER;
            local.insets = new Insets(10, 0, 0, 0);

            actionPanel.add(actionButton, local);

            local.gridy = 5;
            local.insets = new Insets(60, 0, 0, 0);

            actionPanel.add(goalLabel, local);

            local.insets = new Insets(10, 0, 0, 0);
            local.gridy = 6;

            actionPanel.add(goalField, local);

            local.gridy = 8;

            actionPanel.add(metaButton, local);

            local.gridy = 9;
            local.insets = new Insets(50, 0, 0, 0);

            actionPanel.add(terminate, local);
        }

        // Button Listeners
        {
            addButton.addActionListener(e -> {
                String value = listFacts.getSelectedValue();

                if (!defB.contains(value)) {
                    FactBase.addFact(value, FactList.getFactList().get(value));
                    defB.addElement(value);
                }
            });

            removeButton.addActionListener(e -> {
                int index = baseFacts.getSelectedIndex();

                if (index >= 0) {
                    FactBase.removeFact(defB.getElementAt(index));
                    defB.remove(index);
                }
            });

            bt1.addActionListener(e -> metaButton.setEnabled(false));

            bt2.addActionListener(e -> metaButton.setEnabled(true));

            bt3.addActionListener(e -> metaButton.setEnabled(true));

            actionButton.addActionListener(e -> {
                if (bt1.isSelected()) { // Chaînage avant (Largeur)

                } else if (bt2.isSelected()) { // Chaînage avant (Profondeur)

                } else { // Chaînage arrière
                    try {
                        List<String> arriere = StrategyUtils.backChain(goalField.getText());

                        output.setText("Chaînage arrière : \nFaits de la base qui concluent à vtre but : " + arriere);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            metaButton.addActionListener(e -> {
                new MetaGUI();
            });

            terminate.addActionListener(e -> System.exit(0));
        }

        actionPanel.setPreferredSize(new Dimension(250, 500));
        actionPanel.setMinimumSize(new Dimension(250, 500));
        actionPanel.setMaximumSize(new Dimension(250, 500));

        actionPanel.setBackground(Color.CYAN);
        mainPanel.setBackground(Color.CYAN);

        global.gridx = 0;
        global.gridy = 0;
        framePanel.add(mainPanel, global);
        global.gridx = 1;
        framePanel.add(actionPanel, global);

        framePanel.setBackground(Color.CYAN);

        this.setTitle("Système expert");
        this.add(framePanel);
        this.pack();
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
