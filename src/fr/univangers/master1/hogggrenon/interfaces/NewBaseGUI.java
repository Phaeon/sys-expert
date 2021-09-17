package fr.univangers.master1.hogggrenon.interfaces;

import javax.swing.*;
import java.awt.*;

public class NewBaseGUI extends JFrame {

    public NewBaseGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // En-tête
        JPanel headPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        {
            constr.insets = new Insets(20, 0, 0, 0);
            JLabel faits = new JLabel("Base de faits et de règles");
            headPanel.add(faits, constr);
        }

        headPanel.setPreferredSize(new Dimension(1000, 40));
        headPanel.setMinimumSize(new Dimension(1000, 40));
        headPanel.setMaximumSize(new Dimension(1000, 40));
        mainPanel.add(headPanel);

        // Corps
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridBagLayout());

        GridBagConstraints globalC = new GridBagConstraints();

        { // Eléments du corps
            JLabel importFactsLabel = new JLabel("Importer des faits : "),
                importRulesLabel = new JLabel("Importer des règles : "),
                inputFactLabel = new JLabel("Nouveau fait"),
                inputRuleLabel = new JLabel("Nouvelle règle"),
                factListLabel = new JLabel("Liste des faits"),
                ruleListLabel = new JLabel("Liste des règles");

            JTextField factField = new JTextField("Votre fait...", 15),
                ifRule = new JTextField("Si...", 7),
                thenRule = new JTextField("Alors...", 7);

            String[] values = {"TRUE", "FALSE"};
            JComboBox<String> factValue = new JComboBox<>(values);

            JList<String> listFacts = new JList<>(),
                listRules = new JList<>();

            JScrollPane scrollFacts = new JScrollPane(listFacts),
                scrollRules = new JScrollPane(listRules);

            scrollFacts.setPreferredSize(new Dimension(250, 90));
            scrollFacts.setMinimumSize(new Dimension(250, 90));
            scrollFacts.setMaximumSize(new Dimension(250, 90));

            scrollRules.setPreferredSize(new Dimension(250, 90));
            scrollRules.setMinimumSize(new Dimension(250, 90));
            scrollRules.setMaximumSize(new Dimension(250, 90));

            JButton importFile = new JButton("Importer"), importFile2 = new JButton("Importer"),
                checkFact = new JButton("Ajouter"), checkRule = new JButton("Ajouter"),
                crossFact = new JButton("X Retirer"), crossRule = new JButton("X Retirer");

            JPanel factListPanel = new JPanel(new GridBagLayout()),
                    ruleListPanel = new JPanel(new GridBagLayout()),
                    factUpdatePanel = new JPanel(new GridBagLayout()),
                    ruleUpdatePanel = new JPanel(new GridBagLayout());

            { // Panel en haut à gauche
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.CENTER;
                constr.insets = new Insets(5, 0, 5, 0);

                factListPanel.add(factListLabel, constr);

                constr.gridy = 1;

                factListPanel.add(scrollFacts, constr);

                constr.gridy = 2;

                factListPanel.add(crossFact, constr);

                crossFact.addActionListener(e -> {
                    // TODO : Ajouter un listener au bouton de retrait
                    /*
                        Retirer le fait tout en étudiant et supprimant les règles utilisant le fait.
                     */
                });
            }

            { // Panel en bas a gauche
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.CENTER;
                constr.insets = new Insets(5, 0, 5, 0);

                ruleListPanel.add(ruleListLabel, constr);

                constr.gridy = 1;

                ruleListPanel.add(scrollRules, constr);

                constr.gridy = 2;

                ruleListPanel.add(crossRule, constr);

                crossRule.addActionListener(e -> {
                    // TODO : Ajouter un listener au bouton de retrait
                });
            }

            { // Panel en haut à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(0, 0, 20, 10);

                factUpdatePanel.add(importFactsLabel, constr);

                constr.gridx = 1;

                factUpdatePanel.add(importFile, constr);

                constr.gridx = 0;
                constr.gridy = 1;
                constr.gridwidth = 2;
                constr.insets = new Insets(5, 0, 0, 10);
                constr.anchor = GridBagConstraints.CENTER;

                factUpdatePanel.add(inputFactLabel, constr);

                constr.gridx = 0;
                constr.gridy = 2;
                constr.gridwidth = 1;
                constr.anchor = GridBagConstraints.WEST;

                factUpdatePanel.add(factField, constr);

                constr.gridx = 1;

                factUpdatePanel.add(checkFact, constr);
            }

            { // Panel en bas à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.gridwidth = 2;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(0, 0, 20, 10);

                ruleUpdatePanel.add(importRulesLabel, constr);

                constr.gridx = 2;
                constr.gridwidth = 1;

                ruleUpdatePanel.add(importFile2, constr);

                constr.gridx = 0;
                constr.gridy = 1;
                constr.gridwidth = 3;
                constr.anchor = GridBagConstraints.CENTER;
                constr.insets = new Insets(5, 0, 0, 10);

                ruleUpdatePanel.add(inputRuleLabel, constr);

                constr.gridy = 2;
                constr.gridwidth = 1;

                ruleUpdatePanel.add(ifRule, constr);

                constr.gridx = 1;

                ruleUpdatePanel.add(thenRule, constr);

                constr.gridx = 2;

                ruleUpdatePanel.add(checkRule, constr);
            }



            globalC.anchor = GridBagConstraints.CENTER;
            globalC.gridx = 0;
            globalC.gridy = 0;
            bodyPanel.add(factListPanel, globalC);
            globalC.gridy = 1;
            bodyPanel.add(ruleListPanel, globalC);
            globalC.gridx = 1;
            globalC.gridy = 0;
            bodyPanel.add(factUpdatePanel, globalC);
            globalC.gridy = 1;
            bodyPanel.add(ruleUpdatePanel, globalC);

            factListPanel.setPreferredSize(new Dimension(350, 200));
            factListPanel.setMinimumSize(new Dimension(350, 200));
            factListPanel.setMaximumSize(new Dimension(350, 200));

            ruleListPanel.setPreferredSize(new Dimension(350, 200));
            ruleListPanel.setMinimumSize(new Dimension(350, 200));
            ruleListPanel.setMaximumSize(new Dimension(350, 200));

            factUpdatePanel.setPreferredSize(new Dimension(450, 200));
            factUpdatePanel.setMinimumSize(new Dimension(450, 200));
            factUpdatePanel.setMaximumSize(new Dimension(450, 200));

            ruleUpdatePanel.setPreferredSize(new Dimension(450, 200));
            ruleUpdatePanel.setMinimumSize(new Dimension(450, 200));
            ruleUpdatePanel.setMaximumSize(new Dimension(450, 200));
        }

        mainPanel.add(bodyPanel);

        this.setTitle("Système expert");
        this.add(mainPanel);
        this.pack();
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
