package fr.univangers.master1.hogggrenon.interfaces;

import fr.univangers.master1.hogggrenon.*;
import fr.univangers.master1.hogggrenon.utils.FactListUtils;
import fr.univangers.master1.hogggrenon.utils.FileUtils;
import fr.univangers.master1.hogggrenon.utils.RuleList;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class NewBaseGUI extends JFrame {

    private final DefaultListModel<String> facts;
    private final DefaultListModel<String> rules;

    /* TODO
        - Exceptions : Caractères non autorisés, Règle non ajoutable...
     */

    public NewBaseGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // En-tête
        JPanel headPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        {
            constr.insets = new Insets(20, 0, 0, 0);
            JLabel faits = new JLabel("FAITS ET RÈGLES");
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

        {
            // Eléments du corps
            JLabel importFactsLabel = new JLabel("Importer des faits : "),
                importRulesLabel = new JLabel("Importer des règles : "),
                inputFactVarLabel = new JLabel("Nouveau fait (avec variable)"),
                inputFactPremLabel = new JLabel("Nouveau fait (prémisse)"),
                inputRuleLabel = new JLabel("Nouvelle règle"),
                factListLabel = new JLabel("Liste des faits"),
                ruleListLabel = new JLabel("Base de règles"),
                arrowLabel = new JLabel("=>", JLabel.CENTER),
                equalLabel = new JLabel("=", JLabel.CENTER);

            JTextField factNameField = new JTextField("Variable...", 10),
                    factValueField = new JTextField("Valeur...", 10),
                ifRule = new JTextField("Si...", 10),
                thenRule = new JTextField("Alors...", 10),
                factField = new JTextField("Prémisse", 15);

            String[] values = {"True", "False"};
            JComboBox<String> factValues = new JComboBox<>(values);

            DefaultListModel<String> defF = new DefaultListModel<>(), defR = new DefaultListModel<>();
            JList<String> listFacts = new JList<>(defF),
                listRules = new JList<>(defR);

            this.facts = defF;
            this.rules = defR;

            JScrollPane scrollFacts = new JScrollPane(listFacts),
                scrollRules = new JScrollPane(listRules);

            scrollFacts.setPreferredSize(new Dimension(250, 90));
            scrollFacts.setMinimumSize(new Dimension(250, 90));
            scrollFacts.setMaximumSize(new Dimension(250, 90));

            scrollRules.setPreferredSize(new Dimension(250, 90));
            scrollRules.setMinimumSize(new Dimension(250, 90));
            scrollRules.setMaximumSize(new Dimension(250, 90));

            JButton importFile = new JButton("Importer"), importFile2 = new JButton("Importer"),
                checkFactVar = new JButton("Ajouter \u2714"), checkFactPrem = new JButton("Ajouter \u2714"), checkRule = new JButton("Ajouter \u2714"),
                crossFact = new JButton("Retirer \u2716"), crossRule = new JButton("Retirer \u2716");

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
                    if (listFacts.getSelectedIndex() != -1) removeFact(listFacts.getSelectedIndex());
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
                    if (listRules.getSelectedIndex() != -1) removeRule(listRules.getSelectedIndex());
                });
            }

            { // Panel en haut à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(0, 5, 30, 5);

                factUpdatePanel.add(importFactsLabel, constr);

                constr.gridx = 3;

                factUpdatePanel.add(importFile, constr);

                constr.gridx = 0;
                constr.gridy = 1;
                constr.gridwidth = 5;
                constr.insets = new Insets(5, 0, 0, 10);
                constr.anchor = GridBagConstraints.CENTER;

                factUpdatePanel.add(inputFactPremLabel, constr);

                constr.gridx = 0;
                constr.gridy = 2;
                constr.gridwidth = 3;

                factUpdatePanel.add(factField, constr);

                constr.gridx = 3;
                constr.gridwidth = 2;

                factUpdatePanel.add(factValues, constr);

                constr.gridy = 4;
                constr.gridx = 0;
                constr.gridwidth = 5;
                constr.anchor = GridBagConstraints.CENTER;

                factUpdatePanel.add(checkFactPrem, constr);

                constr.gridy = 5;
                constr.insets = new Insets(30, 0, 0, 10);

                factUpdatePanel.add(inputFactVarLabel, constr);

                constr.gridy = 6;
                constr.gridx = 0;
                constr.gridwidth = 2;
                constr.insets = new Insets(5, 0, 0, 10);

                factUpdatePanel.add(factNameField, constr);

                constr.gridx = 2;
                constr.gridwidth = 1;

                factUpdatePanel.add(equalLabel, constr);

                constr.gridx = 3;
                constr.gridwidth = 2;

                factUpdatePanel.add(factValueField, constr);

                constr.gridy = 7;
                constr.gridx = 0;
                constr.gridwidth = 5;
                constr.insets = new Insets(15, 0, 0, 10);

                factUpdatePanel.add(checkFactVar,constr);

                importFile.addActionListener(e -> {
                    try {
                        getFactFileData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                checkFactVar.addActionListener(e -> {
                    String fact = factNameField.getText().trim(), value = factValueField.getText().trim();

                    if (!FactListUtils.isAFact(fact)) {
                        if (Parser.isAValidVariable(fact)) {
                            if (Parser.isNumeric(value))
                                FactListUtils.addFact(new FactWithVar(fact, Float.parseFloat(value)));
                            else if (Parser.isBoolean(value))
                                FactListUtils.addFact(new FactWithVar(fact, Boolean.parseBoolean(value)));
                            else if (Parser.isString(value))
                                FactListUtils.addFact(new FactWithVar(fact, value));
                            else return;

                            this.facts.addElement(fact + " -> " + value);
                        }
                    }
                });

                checkFactPrem.addActionListener(e -> {
                    Fact fact = new FactWithPremise(factField.getText().trim(), Boolean.parseBoolean((String) factValues.getSelectedItem()));

                    if (!FactListUtils.isAFact(factField.getText().trim())) {
                        FactListUtils.addFact(fact);

                        this.facts.addElement(factField.getText().trim() + " -> " + Boolean.parseBoolean((String) factValues.getSelectedItem()));
                    }
                });
            }

            { // Panel en bas à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(0, 5, 30, 5);

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

                ruleUpdatePanel.add(arrowLabel, constr);

                constr.gridx = 2;

                ruleUpdatePanel.add(thenRule, constr);

                constr.gridx = 0;
                constr.gridy = 3;
                constr.gridwidth = 3;

                constr.insets = new Insets(15, 0, 0, 10);
                ruleUpdatePanel.add(checkRule, constr);

                importFile2.addActionListener(e -> {
                    try {
                        getRuleFileData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                checkRule.addActionListener(e -> {
                    String head = ifRule.getText().trim();
                    String body = thenRule.getText().trim();

                    Parser P = new Parser(head);

                    if (P.validPostfix(P.infixToPostfix())) {
                        if (!RuleList.isARule(new Rule(head, body))
                                && FactListUtils.isAFact(body)) {
                            RuleList.addRule(head, body);
                            this.rules.addElement(head + " -> " + body);
                            System.out.println("Règle : " + head);
                        }
                    }
                });
            }

            globalC.anchor = GridBagConstraints.CENTER;
            globalC.gridx = 0;
            globalC.gridy = 0;
            bodyPanel.add(factListPanel, globalC);
            globalC.gridy = 1;
            bodyPanel.add(factUpdatePanel, globalC);
            globalC.gridx = 1;
            globalC.gridy = 0;
            bodyPanel.add(ruleListPanel, globalC);
            globalC.gridy = 1;
            bodyPanel.add(ruleUpdatePanel, globalC);

            factListPanel.setPreferredSize(new Dimension(300, 250));
            factListPanel.setMinimumSize(new Dimension(300, 250));
            factListPanel.setMaximumSize(new Dimension(300, 250));

            ruleListPanel.setPreferredSize(new Dimension(300, 250));
            ruleListPanel.setMinimumSize(new Dimension(300, 250));
            ruleListPanel.setMaximumSize(new Dimension(300, 250));

            factUpdatePanel.setPreferredSize(new Dimension(450, 250));
            factUpdatePanel.setMinimumSize(new Dimension(450, 250));
            factUpdatePanel.setMaximumSize(new Dimension(450, 250));

            ruleUpdatePanel.setPreferredSize(new Dimension(450, 250));
            ruleUpdatePanel.setMinimumSize(new Dimension(450, 250));
            ruleUpdatePanel.setMaximumSize(new Dimension(450, 250));
        }

        mainPanel.add(bodyPanel);

        // Fin
        JPanel footerPanel = new JPanel(new GridBagLayout());
        constr = new GridBagConstraints();

        {
            constr.insets = new Insets(5, 0, 10, 0);
            JButton continueButt = new JButton("Suivant");
            footerPanel.add(continueButt, constr);

            continueButt.addActionListener(e -> {
                this.dispose();
                new SystemGUI(FactListUtils.factList);
            });
        }

        footerPanel.setPreferredSize(new Dimension(1000, 60));
        footerPanel.setMinimumSize(new Dimension(1000, 60));
        footerPanel.setMaximumSize(new Dimension(1000, 60));
        mainPanel.add(footerPanel);

        this.setTitle("Système expert - Saisie");
        this.add(mainPanel);
        this.pack();
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public void removeRule(int index) {
        RuleList.getRuleBase().remove(index);
        this.rules.remove(index);
    }

    public void removeFact(int index) {
        FactListUtils.removeFact(index);
        this.facts.remove(index);

        Iterator<Rule> iterator = RuleList.getRuleBase().iterator();

        while(iterator.hasNext())
        {
            Rule r = iterator.next();
            Parser P = new Parser(r.getHead());

            // Si un fait a été retiré de la règle, la règle ne sera plus valide
            // (La variable ne sera pas reconnue lors du parsing)
            if (!P.validPostfix(P.infixToPostfix())
                    || !FactListUtils.isAFact(r.getBody())) {
                int indBase = RuleList.getRuleBase().indexOf(r);
                iterator.remove();
                this.rules.remove(indBase);
            }
        }
    }


    public void getFactFileData() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            List<Fact> facts = FileUtils.getFactsFromCSV(selectedFile.getAbsolutePath());

            for (Fact f : facts) {
                if (!this.facts.contains(f.getKey() + " -> " + f.getValue())) {
                    this.facts.addElement(f.getKey() + " -> " + f.getValue());
                }
            }
        }
    }

    public void getRuleFileData() throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            List<Rule> rules = FileUtils.getRulesFromCSV(selectedFile.getAbsolutePath());

            for (Rule r : rules) {

                if (!this.rules.contains(r.getHead() + " -> " + r.getBody())
                        && r.checkRule()) {
                    RuleList.addRule(r.getHead(), r.getBody());
                    this.rules.addElement(r.getHead() + " -> " + r.getBody());
                }
            }
        }
    }

}
