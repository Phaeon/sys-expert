package fr.univangers.master1.hogggrenon.interfaces;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.Rule;
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
                inputFactLabel = new JLabel("Nouveau fait"),
                inputRuleLabel = new JLabel("Nouvelle règle"),
                factListLabel = new JLabel("Liste des faits"),
                ruleListLabel = new JLabel("Base de règles"),
                arrowLabel = new JLabel("=>", JLabel.CENTER),
                equalLabel = new JLabel("=", JLabel.CENTER);

            JTextField factNameField = new JTextField("Nom de variable...", 10),
                    factValueField = new JTextField("Valeur...", 10),
                ifRule = new JTextField("Si...", 10),
                thenRule = new JTextField("Alors...", 10);

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

                crossFact.addActionListener(e -> removeFact(listFacts.getSelectedIndex()));
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

                crossRule.addActionListener(e -> removeRule(listRules.getSelectedIndex()));
            }

            { // Panel en haut à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(0, 0, 20, 10);

                factUpdatePanel.add(importFactsLabel, constr);

                constr.gridx = 3;

                factUpdatePanel.add(importFile, constr);

                constr.gridx = 0;
                constr.gridy = 1;
                constr.gridwidth = 5;
                constr.insets = new Insets(5, 0, 0, 10);
                constr.anchor = GridBagConstraints.CENTER;

                factUpdatePanel.add(inputFactLabel, constr);

                constr.gridx = 0;
                constr.gridy = 2;
                constr.gridwidth = 2;
                constr.anchor = GridBagConstraints.WEST;

                factUpdatePanel.add(factNameField, constr);

                constr.gridx = 2;
                constr.gridwidth = 1;

                factUpdatePanel.add(equalLabel, constr);

                constr.gridx = 3;
                constr.gridwidth = 2;

                factUpdatePanel.add(factValueField, constr);

                constr.gridy = 3;
                constr.gridx = 0;
                constr.gridwidth = 5;
                constr.anchor = GridBagConstraints.CENTER;

                constr.insets = new Insets(15, 0, 0, 10);

                factUpdatePanel.add(checkFact,constr);

                importFile.addActionListener(e -> {
                    try {
                        getFactFileData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                checkFact.addActionListener(e -> {
                    addManualFact(factNameField.getText().trim(), factValueField.getText().trim());
                });
            }

            { // Panel en bas à droite
                constr = new GridBagConstraints();

                constr.gridx = 0;
                constr.gridy = 0;
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
                    String[] body = thenRule.getText().trim().split(",");

                    for (int i = 0; i < body.length; i++)
                        body[i] = body[i].trim();

                    addManualRule(head, Arrays.asList(body));
                });
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
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addManualFact(String fact, String value) {
        if (!FactListUtils.isAFact(fact)) {
            if (Parser.isAValidVariable(fact)) {
                if (Parser.isNumeric(value))
                    FactListUtils.addFact(fact, Float.parseFloat(value));
                else if (Parser.isBoolean(value))
                    FactListUtils.addFact(fact, Boolean.parseBoolean(value));
                else if (Parser.isString(value))
                    FactListUtils.addFact(fact, value);
                else return;

                this.facts.addElement(fact + " -> " + value);
            }
        }
    }

    public void addManualRule(String head, List<String> body) {
        Parser P = new Parser(head);

        for (String s : P.infixToPostfix())
            System.out.println(s);

        if (!P.validPostfix(P.infixToPostfix()))
            return;

        if (!RuleList.isARule(new Rule(head, body)))
        {
            RuleList.addRule(head, body);
            this.rules.addElement(head + " -> " + body);
        }
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
            if (!P.validPostfix(P.infixToPostfix())) {
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
            Map<String, Object> facts = FileUtils.getFactsFromCSV(selectedFile.getAbsolutePath());

            for (Map.Entry<String, Object> f : facts.entrySet()) {
                if (!this.facts.contains(f)) {
                    FactListUtils.addFact(f.getKey(), f.getValue());
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
                if (!this.rules.contains(r)
                        && r.checkRule()) {
                    RuleList.addRule(r.getHead(), r.getBody());
                    this.rules.addElement(r.getHead() + " -> " + r.getBody());
                }
            }
        }
    }

}
