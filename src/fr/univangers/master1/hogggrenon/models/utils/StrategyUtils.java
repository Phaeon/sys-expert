package fr.univangers.master1.hogggrenon.models.utils;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.Rule;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class StrategyUtils {

    private final PropertyChangeSupport support;
    private static StrategyUtils instance;
    private boolean detailedTrace;

    private StrategyUtils() {
        this.detailedTrace = true;
        this.support = new PropertyChangeSupport(this);
    }

    public static synchronized StrategyUtils getInstance() {
        if (instance == null) {
            instance = new StrategyUtils();
        }

        return instance;
    }

    public void activateDetailedTrace() {
        this.detailedTrace = true;
    }

    public void deactivateDetailedTrace() {
        this.detailedTrace = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void backChain(List<String> but) throws Exception {
        List<String> file;
        List<Fact> facts = new ArrayList<>();
        file = new ArrayList<>(but);

        this.support.firePropertyChange("trace", null, "Chaînage arrière :\n");

        if (RuleList.hasIncFacts()) {
            this.support.firePropertyChange("error", null, "La base de règles contient des données incohérentes, opération impossible.");
            return;
        } else if (RuleList.ruleBase.stream().noneMatch(e -> but.contains(e.getBody()))) {
            this.support.firePropertyChange("error", null, "Le but ne pourra jamais être atteint avec les règles actuelles.");
            return;
        }

        int step = 1;
        while (file.size() != 0) {
            String fact = file.get(0);

            if (detailedTrace)
            {
                this.support.firePropertyChange("trace", null, "[ Étape " + step + " ]\n");
                this.support.firePropertyChange("trace", null, "Fait analysé : " + fact);
            }

            if (RuleList.getRuleBase().stream().anyMatch(e -> e.getBody().equals(fact))) {

                // Listes des règles valables (déclenchables)
                List<Rule> rules = RuleList.getRuleBase().stream().filter(e -> {
                    Parser P = new Parser(e.getHead());
                    return e.getBody().equals(fact)
                            && P.valuateTree(P.createTree(P.infixToPostfix()));
                }).toList();

                rules = new ArrayList<>(rules);

                // Choisir la règle suivant les méta-règles
                if (rules.size() > 1) {

                    if (detailedTrace)
                        this.support.firePropertyChange("trace", null, "Conflit détecté, analyse des méta-règles...");

                    while (!rules.isEmpty()) {
                        Rule R = null;

                        // Parcourir les règles selon les méta-règles choisies
                        for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                            R = MetaUtils.getRule(rules, metaRule, facts);

                            if (R != null) {
                                Parser local = new Parser(R.getHead());
                                file.addAll(local.getFactsInExpression(local.infixToPostfix()));
                                rules.remove(R);
                                if (detailedTrace) {
                                    String meta_name = metaRule.toString().substring(0, 1).toUpperCase() + metaRule.name().substring(1).toLowerCase();
                                    this.support.firePropertyChange("trace", null, "MR : " + meta_name + ", règle déclenchée : " + R.getHead() + " -> " + R.getBody());
                                    this.support.firePropertyChange("trace", null, "Faits ajoutés à l'analyse : " + local.getFactsInExpression(local.infixToPostfix()).toString());
                                }

                                break;
                            }
                        }

                        // Si aucune règle n'a été choisie et que l'ensemble des méta-règles ont été parcourues
                        if (R == null) {
                            this.support.firePropertyChange("error", null, "Ambiguïtés, veuillez changer vos méta-règles.");
                            return;
                        }
                    }
                } else {
                    Parser local = new Parser(rules.get(0).getHead());
                    file.addAll(local.getFactsInExpression(local.infixToPostfix()));

                    if (detailedTrace)
                    {
                        this.support.firePropertyChange("trace", null, "Aucun conflit, règle déclenchée : " + rules.get(0).getHead() + " -> " + rules.get(0).getBody());
                        this.support.firePropertyChange("trace", null, "Faits ajoutés à l'analyse : " + local.getFactsInExpression(local.infixToPostfix()).toString());
                    }

                }
            } else {
                if (FactBase.isAFact(fact)) {
                    if (detailedTrace)
                        this.support.firePropertyChange("trace", null, "Fait détecté dans la base de faits : " + fact);
                    facts.add(FactBase.getFact(fact));
                } else {
                    if (!FactListUtils.isAFact(fact)) {
                        if (detailedTrace)
                            this.support.firePropertyChange("error", null, "Le fait " + fact + " n'est pas dans la liste de faits.");
                        return;
                    } else {
                        if (detailedTrace)
                            this.support.firePropertyChange("trace", null, "Fait dénombrable : " + fact);
                    }
                }
            }

            if (detailedTrace) {
                StringBuilder builder = new StringBuilder();

                for (Fact F : facts)
                    builder.append(F.getKey()).append(" ");

                String output = "Faits de la base détectés à la fin de l'étape : ";
                output += builder;

                this.support.firePropertyChange("trace", null, output + "\n\n");
            }

            file.remove(0);
            step++;
        }

        if (!detailedTrace)
            this.support.firePropertyChange("trace", null, "Étapes nécessaires : " + (step - 1) + "\n");

        StringBuilder builder = new StringBuilder();

        for (Fact F : facts)
            builder.append(F.getKey()).append(" ");

        String output = "Faits qui concluent à votre but : ";
        output += builder;

        this.support.firePropertyChange("trace", null, output);
    }

    public void frontChainDepth(List<String> but) throws Exception {
        List<Fact> factBase = new ArrayList<>(FactBase.factBase);
        List<Rule> visitedRules = new ArrayList<>();

        boolean butNonTrouve = true;

        this.support.firePropertyChange("trace", null, "Chaînage avant (en profondeur) :\n");

        if (RuleList.hasIncFacts()) {
            this.support.firePropertyChange("error", null, "La base de règles contient des données incohérentes, opération impossible.");
            return;
        }

        int step = 1;
        while (butNonTrouve) {

            if (detailedTrace)
                this.support.firePropertyChange("trace", null, "[ Étape " + step + " ]\n");

            // Filtrer les règles non visitées qui ont tous les faits de l'en-tête dans la base de faits
            List<Rule> rules = RuleList.getRuleBase().stream().filter(rule -> {
                Parser P = new Parser(rule.getHead());
                List<Object> localF = new ArrayList<>();
                for (Fact f : factBase)
                    localF.add(f.getKey());

                return !visitedRules.contains(rule)
                        && localF.containsAll(P.getFactsInExpression(P.infixToPostfix()));
            }).toList();

            if (rules.size() == 0)
                butNonTrouve = false;

                // Choisir la règle suivant les méta-règles
            else if (rules.size() > 1) {
                Rule R = null;

                if (detailedTrace)
                    this.support.firePropertyChange("error", null, "Conflit détecté, analyse des méta-règles...");

                // Parcourir les règles selon les méta-règles choisies
                for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                    R = MetaUtils.getRule(rules, metaRule, factBase);

                    if (R != null) {
                        factBase.add(FactListUtils.getFact(R.getBody()));
                        if (detailedTrace) {
                            String meta_name = metaRule.toString().substring(0, 1).toUpperCase() + metaRule.name().substring(1).toLowerCase();
                            this.support.firePropertyChange("trace", null, "MR : " + meta_name + ", règle déclenchée : " + R.getHead() + " -> " + R.getBody());
                            this.support.firePropertyChange("trace", null, "Fait ajouté à l'analyse : " + R.getBody());
                        }
                        break;
                    }
                }

                if (R == null) {
                    this.support.firePropertyChange("error", null, "Ambiguïtés, veuillez changer vos méta-règles.");
                    return;
                }

                // Ajouter la règle déclenchée dans la liste de règles
                visitedRules.add(R);
            } else {
                factBase.add(FactListUtils.getFact(rules.get(0).getBody()));
                if (detailedTrace)
                    this.support.firePropertyChange("trace", null, "Fait ajouté à l'analyse : " + rules.get(0).getBody());

                // Ajouter la règle déclenchée dans la liste de règles
                visitedRules.add(rules.get(0));
            }

            if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
                butNonTrouve = false;

            if (detailedTrace) {
                StringBuilder builder = new StringBuilder();

                for (Fact F : factBase)
                    builder.append(F.getKey()).append(" ");

                String output = "La base de faits suite au chaînage en fin d'étape : ";
                output += builder;

                this.support.firePropertyChange("trace", null, output + "\n\n");
            }

            step++;
        }

        if (!detailedTrace)
            this.support.firePropertyChange("trace", null, "Étapes nécessaires : " + (step - 1) + "\n");

        StringBuilder builder = new StringBuilder();

        for (Fact F : factBase)
            builder.append(F.getKey()).append(" ");

        String output = "La base de faits suite au chaînage : ";
        output += builder;

        this.support.firePropertyChange("trace", null, output);

        if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
            this.support.firePropertyChange("trace", null, "Un but a été atteint.");
        else
            this.support.firePropertyChange("trace", null, "Un but n'a pas été atteint.");
    }

    public void frontChainWidth(List<String> but) throws Exception {
        List<Fact> factBase = new ArrayList<>(FactBase.factBase);
        List<Rule> visitedRules = new ArrayList<>();

        boolean butNonTrouve = true;

        this.support.firePropertyChange("trace", null, "Chaînage avant (en largeur) :\n");

        if (RuleList.hasIncFacts()) {
            this.support.firePropertyChange("error", null, "La base de règles contient des données incohérentes, opération impossible.");
            return;
        }

        int step = 1;
        while (butNonTrouve) {

            if (detailedTrace)
                this.support.firePropertyChange("trace", null, "[ Étape " + step + " ]\n");

            // Filtrer les règles non visitées qui ont tous les faits de l'en-tête dans la base de faits
            List<Rule> rules = RuleList.getRuleBase().stream().filter(rule -> {
                Parser P = new Parser(rule.getHead());
                List<Object> localF = new ArrayList<>();
                for (Fact f : factBase)
                    localF.add(f.getKey());

                return !visitedRules.contains(rule) && localF.containsAll(P.getFactsInExpression(P.infixToPostfix()));
            }).toList();

            if (rules.size() == 0)
                butNonTrouve = false;

            for (Rule r : rules)
            {
                factBase.add(FactListUtils.getFact(r.getBody()));
                if (detailedTrace)
                    this.support.firePropertyChange("trace", null, "Fait ajouté à l'analyse : " + r.getBody());
            }

            // Ajouter la règle déclenchée dans la liste de règles
            visitedRules.addAll(rules);

            // Cas où un but a été ajouté dans la base de faits
            if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
                butNonTrouve = false;

            if (detailedTrace) {
                StringBuilder builder = new StringBuilder();

                for (Fact F : factBase)
                    builder.append(F.getKey()).append(" ");

                String output = "La base de faits suite au chaînage en fin d'étape : ";
                output += builder;

                this.support.firePropertyChange("trace", null, output + "\n\n");
            }

            step++;
        }

        if (!detailedTrace)
            this.support.firePropertyChange("trace", null, "Étapes nécessaires : " + (step - 1) + "\n");

        StringBuilder builder = new StringBuilder();

        for (Fact F : factBase)
            builder.append(F.getKey()).append(" ");

        String output = "La base de faits suite au chaînage : ";
        output += builder;

        this.support.firePropertyChange("trace", null, output);

        if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
            this.support.firePropertyChange("trace", null, "Un but a été atteint.");
        else
            this.support.firePropertyChange("trace", null, "Un but n'a pas été atteint.");

    }

}
