package fr.univangers.master1.hogggrenon.views;

import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.utils.FactBase;
import fr.univangers.master1.hogggrenon.models.utils.FactListUtils;
import fr.univangers.master1.hogggrenon.models.utils.StrategyUtils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SystemGUI extends JFrame implements PropertyChangeListener {

    private final JTextArea trace;

    public SystemGUI(StrategyUtils strategies) {
        // Listeners
        strategies.addPropertyChangeListener(this);

        // Interface
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
                actionLabel = new JLabel("CHAÎNAGE", JLabel.CENTER),
                tracesLabel = new JLabel("Trace détaillée : ");

        JTextArea output = new JTextArea();
        output.setRows(10);
        output.setColumns(50);
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.trace = output;

        JButton addButton = new JButton("Ajouter \u2714"),
                removeButton = new JButton("Retirer \u2716"),
                actionButton = new JButton("Exécuter"),
                metaButton = new JButton("Méta-Règles"),
                terminate = new JButton("Terminer"),
                helpButton = new JButton("Aide");

        metaButton.setEnabled(false);

        ButtonGroup group = new ButtonGroup();

        JRadioButton bt1 = new JRadioButton("Avant (Largeur)"),
                bt2 = new JRadioButton("Avant (Profondeur)"),
                bt3 = new JRadioButton("Arrière");

        bt1.setBackground(new Color(51, 153, 255));
        bt2.setBackground(new Color(51, 153, 255));
        bt3.setBackground(new Color(51, 153, 255));

        bt1.setSelected(true);

        ButtonGroup group2 = new ButtonGroup();

        JRadioButton yesBtn = new JRadioButton("Oui"),
            noBtn = new JRadioButton("Non");

        yesBtn.setSelected(true);
        yesBtn.setBackground(Color.CYAN);
        noBtn.setBackground(Color.CYAN);

        group.add(bt1);
        group.add(bt2);
        group.add(bt3);

        group2.add(yesBtn);
        group2.add(noBtn);

        DefaultListModel<String> defA = new DefaultListModel<>(), defB = new DefaultListModel<>();
        JList<String> listFacts = new JList<>(defA),
                baseFacts = new JList<>(defB);

        List<String> str = new ArrayList<>();
        for (Fact f : FactListUtils.factList)
            str.add(f.getKey() + " -> " + f.getValue());

        defA.addAll(str);

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

            local.gridx = 5;

            mainPanel.add(removeButton, local);

            local.gridx = 0;
            local.gridy = 5;
            local.gridwidth = 7;

            mainPanel.add(outputLabel, local);

            local.gridy = 6;

            local.insets = new Insets(20, 0, 40 , 0);

            mainPanel.add(scroll, local);

            local.gridy = 7;
            local.gridx = 2;
            local.gridwidth = 2;
            local.insets = new Insets(10, 0, 10 , 15);
            local.anchor = GridBagConstraints.EAST;

            mainPanel.add(tracesLabel, local);

            local.gridx = 4;
            local.gridwidth = 1;
            local.anchor = GridBagConstraints.CENTER;

            mainPanel.add(yesBtn, local);

            local.gridx = 5;

            mainPanel.add(noBtn, local);

        }

        mainPanel.setPreferredSize(new Dimension(700, 600));
        mainPanel.setMinimumSize(new Dimension(700, 600));
        mainPanel.setMaximumSize(new Dimension(700, 600));

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

            local.gridy = 10;
            local.insets = new Insets(10, 0, 0, 0);

            actionPanel.add(helpButton, local);
        }

        // Button Listeners
        {
            addButton.addActionListener(e -> {
                String value = listFacts.getSelectedValue();

                if (!defB.contains(value) && !value.isEmpty()) {
                    FactBase.addFact(FactListUtils.getFact(value.split(" -> ")[0].trim()));
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

            yesBtn.addActionListener(e -> strategies.activateDetailedTrace());

            noBtn.addActionListener(e -> strategies.deactivateDetailedTrace());

            actionButton.addActionListener(e -> {
                StringBuilder error_builder = new StringBuilder();

                String[] goals = new String[]{};
                if (Objects.equals(goalField.getText(), ""))
                    error_builder.append("\nAucun but n'a été saisi");
                else {
                    goals = goalField.getText().split(",");
                    for (int i = 0; i < goals.length; i++) {
                        goals[i] = goals[i].trim();
                        if (!FactListUtils.isAFact(goals[i]))
                            error_builder.append("\nLe but ").append(goals[i]).append(" n'est pas un fait");
                    }
                }

                this.trace.setText("");
                this.trace.setFont(new Font(this.trace.getFont().getFontName(), Font.BOLD, this.getFont().getSize()));
                this.trace.setForeground(Color.BLACK);

                if (error_builder.isEmpty()) {
                    if (bt1.isSelected()) { // Chaînage avant (Largeur)
                        try {
                            strategies.frontChainWidth(Arrays.asList(goals));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (bt2.isSelected()) { // Chaînage avant (Profondeur)
                        try {
                            strategies.frontChainDepth(Arrays.asList(goals));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else { // Chaînage arrière
                        try {
                            strategies.backChain(Arrays.asList(goals));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else new InformationBox(InformationBox.BoxType.ERROR, "Opérations", error_builder.toString());
            });

            metaButton.addActionListener(e -> new MetaGUI());

            terminate.addActionListener(e -> System.exit(0));

            helpButton.addActionListener(e -> {
                String builder = """

                            - Base de faits : Vous avez la possibilité d'ajouter des faits inscrits
                            précédemments dans la base de faits. Il y a aussi la possibilité de
                            retirer ces faits.

                            - Chaînages : Vous avez la possibilité d'exécuter différents chaînages.
                            La base de faits et les buts sont pris en compte.
                            Vous pouvez saisir plusieurs buts en les séparant par une virgule.
                            
                            Dans le cas du chaînage avant en largeur et le chaînage arrière,
                            il peut être nécessaire de prendre en compte des méta-règles afin
                            d'éviter les ambiguïtés.
                            
                            Un champ texte est visible et affichera les étapes et les erreurs
                            rencontrés lors des chaînages.

                            Des bulles d'erreurs seront affichées en cas de problème ou avertissement.""";

                new InformationBox(InformationBox.BoxType.INFO, "Aide", builder);
            });
        }

        actionPanel.setPreferredSize(new Dimension(250, 650));
        actionPanel.setMinimumSize(new Dimension(250, 650));
        actionPanel.setMaximumSize(new Dimension(250, 650));

        actionPanel.setBackground(new Color(51, 153, 255));
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
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("trace"))
        {
            if (this.trace.getText().isEmpty())
                this.trace.setText(evt.getNewValue().toString());
            else
                this.trace.setText(this.trace.getText() + "\n" + evt.getNewValue().toString());
        }
        else if (evt.getPropertyName().equals("error"))
        {
            this.trace.setText(evt.getNewValue().toString());
            this.trace.setFont(new Font(this.trace.getFont().getFontName(), Font.BOLD, this.trace.getFont().getSize()));
            this.trace.setForeground(Color.RED);
        }
    }
}
