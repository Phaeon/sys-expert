package fr.univangers.master1.hogggrenon.views;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.controlers.EngineControler;
import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.Rule;
import fr.univangers.master1.hogggrenon.models.facts.FactWithPremise;
import fr.univangers.master1.hogggrenon.models.facts.FactWithVar;
import fr.univangers.master1.hogggrenon.models.utils.FactListUtils;
import fr.univangers.master1.hogggrenon.models.utils.IncFactListUtils;
import fr.univangers.master1.hogggrenon.models.utils.RuleList;
import fr.univangers.master1.hogggrenon.models.utils.StrategyUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class NewBaseGUI extends JFrame {

    private DefaultListModel<String> data;
    private int selectedButtonID = 1;

    private JPanel inputPanel;

   private EngineControler controler;

    public NewBaseGUI(EngineControler controler) {
        JPanel mainPanel = createMainFrame();

        this.controler = controler;

        this.setTitle("Système expert - Saisie");
        this.add(mainPanel);
        this.pack();
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // MAIN FRAME
    public JPanel createMainFrame() {
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints global = new GridBagConstraints();

        // Panels fixes
        JPanel leftMenuPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JPanel dataPanel = new JPanel(new GridBagLayout());

        // Panel dynamique
        JPanel inputPanel = createFactListPanel();
        this.inputPanel = inputPanel;

        GridBagConstraints constr;
        ButtonGroup tabs = new ButtonGroup();
        JRadioButton rb1 = new JRadioButton("Faits"),
                rb2 = new JRadioButton("Règles"),
                rb3 = new JRadioButton("Incohérences");

        // Menu de gauche
        {
            constr = new GridBagConstraints();

            JLabel tabsLabel = new JLabel("Onglets", JLabel.CENTER);

            rb1.setSelected(true);
            this.selectedButtonID = 1;
            tabs.add(rb1);
            tabs.add(rb2);
            tabs.add(rb3);
            rb1.setBackground(Color.LIGHT_GRAY);
            rb2.setBackground(Color.LIGHT_GRAY);
            rb3.setBackground(Color.LIGHT_GRAY);

            rb1.addActionListener(e -> {
                rightPanel.remove(1);

                global.gridx = 0;
                global.gridy = 1;
                global.insets = new Insets(0, 0, 0, 0);

                rightPanel.add(createFactListPanel(), global);
                this.repaint();
                this.revalidate();

                this.selectedButtonID = 1;

                this.data.removeAllElements();

                for (Fact F : FactListUtils.factList)
                    this.data.addElement(F.getKey() + " -> " + F.getValue());
            });

            rb2.addActionListener(e -> {
                rightPanel.remove(1);

                global.gridx = 0;
                global.gridy = 1;
                global.insets = new Insets(0, 0, 0, 0);

                rightPanel.add(createRulePanel(), global);
                this.repaint();
                this.revalidate();

                this.selectedButtonID = 2;

                this.data.removeAllElements();

                for (Rule R : RuleList.getRuleBase())
                    this.data.addElement(R.getHead() + " -> " + R.getBody());
            });

            rb3.addActionListener(e -> {
                rightPanel.remove(1);

                global.gridx = 0;
                global.gridy = 1;
                global.insets = new Insets(0, 0, 0, 0);

                rightPanel.add(createFactListPanel(), global);
                this.repaint();
                this.revalidate();

                this.selectedButtonID = 3;

                this.data.removeAllElements();

                for (Fact F : IncFactListUtils.incFactList)
                    this.data.addElement(F.getKey() + " -> " + F.getValue());
            });

            JButton helpButton = new JButton("?"),
                    abandonButton = new JButton("Quitter"),
                    nextButton = new JButton("Suivant");

            {
                constr.insets = new Insets(0, 0, 10, 0);
                constr.gridx = 0;
                constr.gridy = 0;
                constr.gridwidth = 1;

                leftMenuPanel.add(tabsLabel, constr);

                constr.gridy = 1;
                constr.anchor = GridBagConstraints.WEST;
                constr.insets = new Insets(10, 0, 0, 0);

                leftMenuPanel.add(rb1, constr);

                constr.gridy = 2;

                leftMenuPanel.add(rb2, constr);

                constr.gridy = 3;

                leftMenuPanel.add(rb3, constr);

                constr.gridy = 4;
                constr.anchor = GridBagConstraints.CENTER;
                constr.insets = new Insets(20, 0, 0, 0);

                leftMenuPanel.add(helpButton, constr);

                constr.gridy = 5;
                constr.insets = new Insets(50, 0, 0, 0);

                leftMenuPanel.add(abandonButton, constr);

                constr.gridy = 6;
                constr.insets = new Insets(5, 0, 0, 0);

                leftMenuPanel.add(nextButton, constr);

                abandonButton.addActionListener(e -> {
                    this.dispose();

                    FactListUtils.initializeFactList();
                    IncFactListUtils.initializeFactList();
                    RuleList.initializeRuleBase();

                    new HomeGUI(controler);
                });

                nextButton.addActionListener(e -> {
                    StringBuilder builder = new StringBuilder();
                    if (FactListUtils.factList.isEmpty())
                        builder.append("\n- Aucun fait n'a été saisi.");
                    if (RuleList.ruleBase.isEmpty())
                        builder.append("\n- Aucune règle n'a été saisie.");

                    if (builder.length() != 0)
                        new InformationBox(InformationBox.BoxType.ERROR, "", builder.toString());
                    else {
                        this.dispose();
                        new SystemGUI(controler, StrategyUtils.getInstance());
                    }
                });

                helpButton.addActionListener(e -> {

                    String builder = "- Faits : Vous pouvez saisir ou importer vos faits (variables ou prémisses) \n\n" +
                            "Une variable doit commencer par une lettre et peut être \n" +
                            "suivi de lettres et de chiffres. Vous pourrez définir les faits \n" +
                            "de la base à l'étape suivante.\n\n" +
                            "- Règles : Vous pouvez saisir ou importer vos règles avec comme tête, une \n" +
                            "expression logique, et comme conclusion un fait. Chaque fait doit être \n" +
                            "enregistré dans la liste des faits. \n\n" +
                            "Les connecteurs logiques valables : ~ (NOT), & (AND), | (OR) \n" +
                            "Les opérateurs arithmétiques disponibles : +, -, /, * \n" +
                            "Les opérateurs de comparaison disponible : <, <=, >, >=, ==, != \n" +
                            "Les valeurs possibles : nombres, booléens, faits (variables \n" +
                            "ou prémisses), chaîne de caractères (guillemets simples) \n\n" +
                            "Exemple : ((age >= 18) & nat == 'FR') \n\n" +
                            "- Incohérences : Vous pouvez saisir ou importer vos incohérences selon \n" +
                            "les mêmes modalités que les faits. Ceux-ci seront utilisés lors des \n" +
                            "chaînages pour vérifier que ceux-ci sont possibles. \n\n" +
                            "Des bulles d'erreurs seront affichées en cas de problème ou avertissement.";

                    new InformationBox(InformationBox.BoxType.INFO, "Aide", builder);
                });
            }
        }

        { // Data Panel

            JLabel listLabel = new JLabel("DONNÉES");

            DefaultListModel<String> defF = new DefaultListModel<>();
            JList<String> listData = new JList<>(defF);

            this.data = defF;

            for (Fact F : FactListUtils.factList)
                this.data.addElement(F.getKey() + " -> " + F.getValue());

            JScrollPane scrollFacts = new JScrollPane(listData);

            JButton cross = new JButton("Retirer \u2716");

            scrollFacts.setPreferredSize(new Dimension(400, 90));
            scrollFacts.setMinimumSize(new Dimension(400, 90));
            scrollFacts.setMaximumSize(new Dimension(400, 90));

            constr = new GridBagConstraints();

            constr.gridx = 0;
            constr.gridy = 0;
            constr.anchor = GridBagConstraints.CENTER;
            constr.insets = new Insets(5, 0, 5, 0);

            dataPanel.add(listLabel, constr);

            constr.gridy = 1;

            dataPanel.add(scrollFacts, constr);

            constr.gridy = 2;

            dataPanel.add(cross, constr);

            cross.addActionListener(e -> {
                if (rb1.isSelected())
                {
                    if (listData.getSelectedIndex() != -1)
                        controler.removeFact(this.data, listData.getSelectedIndex());

                } else if (rb2.isSelected()) {
                    if (listData.getSelectedIndex() != -1)
                        controler.removeRule(this.data, listData.getSelectedIndex());

                } else if (rb3.isSelected()) {
                    if (listData.getSelectedIndex() != -1)
                        controler.removeIncFact(this.data, listData.getSelectedIndex());
                }
            });

        } // Fin panel en haut à droite


        leftMenuPanel.setPreferredSize(new Dimension(200, 700));
        leftMenuPanel.setMinimumSize(new Dimension(200, 700));
        leftMenuPanel.setMaximumSize(new Dimension(200, 700));

        dataPanel.setPreferredSize(new Dimension(600, 200));
        dataPanel.setMinimumSize(new Dimension(600, 200));
        dataPanel.setMaximumSize(new Dimension(600, 200));

        inputPanel.setPreferredSize(new Dimension(600, 300));
        inputPanel.setMinimumSize(new Dimension(600, 300));
        inputPanel.setMaximumSize(new Dimension(600, 300));

        rightPanel.setPreferredSize(new Dimension(600, 700));
        rightPanel.setMinimumSize(new Dimension(600, 700));
        rightPanel.setMaximumSize(new Dimension(600, 700));

        // Ajouts des panels au main frame
        global.gridx = 0;
        global.gridy = 0;
        global.insets = new Insets(0, 0, 30, 0);

        rightPanel.add(dataPanel, global);

        global.gridy = 1;
        global.insets = new Insets(0, 0, 0, 0);

        rightPanel.add(inputPanel, global);

        global.gridy = 0;

        leftMenuPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
        mainPanel.add(leftMenuPanel, global);

        global.gridx = 1;

        mainPanel.add(rightPanel, global);
        leftMenuPanel.setBackground(Color.LIGHT_GRAY);

        return mainPanel;
    }

    // PANELS
    public JPanel createFactListPanel() {
        JPanel factPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr;

        // Eléments du corps
        JLabel importFactsLabel = new JLabel("Importer des faits : "),
                inputFactVarLabel = new JLabel("Nouveau fait (avec variable)"),
                inputFactPremLabel = new JLabel("Nouveau fait (prémisse)"),
                equalLabel = new JLabel("=", JLabel.CENTER);

        JTextField factNameField = new JTextField(10),
                factValueField = new JTextField(10),
                factField = new JTextField(15);

        factNameField.setToolTipText("Variable...");
        factValueField.setToolTipText("Valeur...");
        factField.setToolTipText("Prémisse...");

        String[] values = {"True", "False"};
        JComboBox<String> factValues = new JComboBox<>(values);

        JButton importFile = new JButton("Importer"),
                checkFactVar = new JButton("Ajouter \u2714"),
                checkFactPrem = new JButton("Ajouter \u2714");

        { // Panel de saisie pour les faits
            constr = new GridBagConstraints();

            constr.gridx = 0;
            constr.gridy = 0;
            constr.anchor = GridBagConstraints.WEST;
            constr.insets = new Insets(0, 5, 30, 5);

            factPanel.add(importFactsLabel, constr);

            constr.gridx = 3;

            factPanel.add(importFile, constr);

            constr.gridx = 0;
            constr.gridy = 1;
            constr.gridwidth = 5;
            constr.insets = new Insets(5, 0, 0, 10);
            constr.anchor = GridBagConstraints.CENTER;

            factPanel.add(inputFactPremLabel, constr);

            constr.gridx = 0;
            constr.gridy = 2;
            constr.gridwidth = 3;

            factPanel.add(factField, constr);

            constr.gridx = 3;
            constr.gridwidth = 2;

            factPanel.add(factValues, constr);

            constr.gridy = 4;
            constr.gridx = 0;
            constr.gridwidth = 5;
            constr.anchor = GridBagConstraints.CENTER;

            factPanel.add(checkFactPrem, constr);

            constr.gridy = 5;
            constr.insets = new Insets(30, 0, 0, 10);

            factPanel.add(inputFactVarLabel, constr);

            constr.gridy = 6;
            constr.gridx = 0;
            constr.gridwidth = 2;
            constr.insets = new Insets(5, 0, 0, 10);

            factPanel.add(factNameField, constr);

            constr.gridx = 2;
            constr.gridwidth = 1;

            factPanel.add(equalLabel, constr);

            constr.gridx = 3;
            constr.gridwidth = 2;

            factPanel.add(factValueField, constr);

            constr.gridy = 7;
            constr.gridx = 0;
            constr.gridwidth = 5;
            constr.insets = new Insets(15, 0, 0, 10);

            factPanel.add(checkFactVar,constr);

            importFile.addActionListener(e -> {
                try {
                    this.controler.getFactFileData(this.data);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            checkFactVar.addActionListener(e -> {
                String fact = factNameField.getText().trim(), value = factValueField.getText().trim();

                if (fact.isEmpty() || value.isEmpty())
                {
                    new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- Il manque des champs");
                    return;
                }

                if (selectedButtonID == 1) {
                    if (!FactListUtils.isAFact(fact)) {
                        if (Parser.isAValidVariable(fact)) {
                            if (Parser.isNumeric(value))
                                FactListUtils.addFact(new FactWithVar(fact, Float.parseFloat(value)));
                            else if (Parser.isBoolean(value))
                                FactListUtils.addFact(new FactWithVar(fact, Boolean.parseBoolean(value)));
                            else if (Parser.isString(value))
                                FactListUtils.addFact(new FactWithVar(fact, value));
                            else {
                                new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- Le fait n'est pas valide");
                                return;
                            }

                            this.data.addElement(fact + " -> " + value);
                        } else {
                            new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- La variable n'est pas valide");
                            return;
                        }

                        factNameField.setText("");
                        factValueField.setText("");
                    } else
                        new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- Le fait est déjà enregistrée");

                } else  if (selectedButtonID == 3) {
                    if (!IncFactListUtils.isAnIncFact(fact)) {
                        if (Parser.isAValidVariable(fact)) {
                            if (Parser.isNumeric(value))
                                IncFactListUtils.addFact(new FactWithVar(fact, Float.parseFloat(value)));
                            else if (Parser.isBoolean(value))
                                IncFactListUtils.addFact(new FactWithVar(fact, Boolean.parseBoolean(value)));
                            else if (Parser.isString(value))
                                IncFactListUtils.addFact(new FactWithVar(fact, value));
                            else {
                                new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- La valeur n'est pas valide");
                                return;
                            }

                            this.data.addElement(fact + " -> " + value);
                        } else {
                            new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- La variable n'est pas valide");
                            return;
                        }

                        factNameField.setText("");
                        factValueField.setText("");
                    } else
                        new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- L'incohérence est déjà enregistrée");

                }
            });

            checkFactPrem.addActionListener(e -> {
                Fact fact = new FactWithPremise(factField.getText().trim(), Boolean.parseBoolean((String) factValues.getSelectedItem()));

                if (Objects.equals(factField.getText(), ""))
                {
                    new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- Il manque des champs");
                    return;
                }

                if (this.selectedButtonID == 1) {
                    if (!FactListUtils.isAFact(factField.getText().trim())) {
                        FactListUtils.addFact(fact);

                        this.data.addElement(factField.getText().trim() + " -> " + Boolean.parseBoolean((String) factValues.getSelectedItem()));
                        factField.setText("");
                    } else
                        new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- La prémisse est déjà enregistrée");
                } else if (this.selectedButtonID == 3) {
                    if (!IncFactListUtils.isAnIncFact(factField.getText().trim())) {
                        IncFactListUtils.addFact(fact);

                        this.data.addElement(factField.getText().trim() + " -> " + Boolean.parseBoolean((String) factValues.getSelectedItem()));
                        factField.setText("");
                    } else
                        new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'un fait", "\n- L'incohérence est déjà enregistrée");
                }
            });
        }

        return factPanel;
    }

    public JPanel createRulePanel() {
        JPanel rulePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constr;

        JLabel importRulesLabel = new JLabel("Importer des règles : "),
                inputRuleLabel = new JLabel("Nouvelle règle"),
                arrowLabel = new JLabel("=>", JLabel.CENTER);

        JTextField ifRule = new JTextField(10),
                thenRule = new JTextField(10);

        ifRule.setToolTipText("Si...");
        thenRule.setToolTipText("Alors...");

        JButton importFile2 = new JButton("Importer"),
                checkRule = new JButton("Ajouter \u2714");

        { // Panel en bas à droite
            constr = new GridBagConstraints();

            constr.gridx = 0;
            constr.gridy = 0;
            constr.anchor = GridBagConstraints.WEST;
            constr.insets = new Insets(0, 5, 30, 5);

            rulePanel.add(importRulesLabel, constr);

            constr.gridx = 2;
            constr.gridwidth = 1;

            rulePanel.add(importFile2, constr);

            constr.gridx = 0;
            constr.gridy = 1;
            constr.gridwidth = 3;
            constr.anchor = GridBagConstraints.CENTER;
            constr.insets = new Insets(5, 0, 0, 10);

            rulePanel.add(inputRuleLabel, constr);

            constr.gridy = 2;
            constr.gridwidth = 1;

            rulePanel.add(ifRule, constr);

            constr.gridx = 1;

            rulePanel.add(arrowLabel, constr);

            constr.gridx = 2;

            rulePanel.add(thenRule, constr);

            constr.gridx = 0;
            constr.gridy = 3;
            constr.gridwidth = 3;

            constr.insets = new Insets(15, 0, 0, 10);
            rulePanel.add(checkRule, constr);

            importFile2.addActionListener(e -> {
                try {
                    this.controler.getRuleFileData(this.data);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            checkRule.addActionListener(e -> {
                String head = ifRule.getText().trim();
                String body = thenRule.getText().trim();

                Parser P = new Parser(head);

                if (P.validPostfix(P.infixToPostfix())) {
                    StringBuilder builder = new StringBuilder();

                    if (RuleList.isARule(new Rule(head, body)))
                        builder.append("\n- La règle est déjà existante");
                    if (P.getFactsInExpression(P.infixToPostfix()).contains(body))
                        builder.append("\n- La règle boucle");
                    if (!FactListUtils.isAFact(body) && !IncFactListUtils.isAnIncFact(body))
                        builder.append("\n- La conclusion de la règle n'est pas un fait");

                    if (builder.length() != 0)
                        new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'une règle", builder.toString());
                    else {
                        RuleList.addRule(head, body);
                        this.data.addElement(head + " -> " + body);

                        ifRule.setText("");
                        thenRule.setText("");
                    }
                } else {
                    new InformationBox(InformationBox.BoxType.ERROR, "Ajout d'une règle", "\n- La règle n'est pas valide");
                }
            });
        }

        return rulePanel;
    }
}
