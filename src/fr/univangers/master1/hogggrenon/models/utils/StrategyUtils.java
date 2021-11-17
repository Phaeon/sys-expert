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

    private StrategyUtils() {
        this.support = new PropertyChangeSupport(this);
    }

    public static synchronized StrategyUtils getInstance() {
        if (instance == null) {
            instance = new StrategyUtils();
        }

        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public List<Fact> backChain(List<String> but) throws Exception {
        List<String> file;
        List<Fact> facts = new ArrayList<>();
        file = new ArrayList<>(but);

        if (RuleList.hasIncFacts())
            throw new Exception("La base de règles contient des données incohérentes, opération impossible.");

        while (file.size() != 0) {
            String fact = file.get(0);

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

                    while (!rules.isEmpty())
                    {
                        Rule R = null;

                        // Parcourir les règles selon les méta-règles choisies
                        for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                            R = MetaUtils.getRule(rules, metaRule, facts);

                            if (R != null) {
                                Parser local = new Parser(R.getHead());
                                file.addAll(local.getFactsInExpression(local.infixToPostfix()));
                                rules.remove(R);
                                break;
                            }
                        }

                        // Si aucune règle n'a été choisie et que l'ensemble des méta-règles ont été parcourues
                        if (R == null) {
                            this.support.firePropertyChange("error", null, "Ambiguïté, veuillez changer vos méta-règles.");
                            return null;
                            // throw new Exception("Ambiguïté, veuillez changer vos méta-règles.");
                        }
                    }
                } else {
                    Parser local = new Parser(rules.get(0).getHead());
                    file.addAll(local.getFactsInExpression(local.infixToPostfix()));
                }
            } else {
                if (FactBase.isAFact(fact)) {
                    facts.add(FactBase.getFact(fact));
                } else {
                    if (!FactListUtils.isAFact(fact))
                        throw new Exception("Le fait est une erreur.");
                }
            }

            file.remove(0);
        }

        return facts;
    }

    public List<Fact> frontChainDepth(List<String> but) throws Exception {
        List<Fact> factBase = new ArrayList<>(FactBase.factBase);
        List<Rule> visitedRules = new ArrayList<>();

        boolean butNonTrouve = true;

        if (RuleList.hasIncFacts())
            throw new Exception("La base de règles contient des données incohérentes, opération impossible.");

        while (butNonTrouve) {

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

                // Parcourir les règles selon les méta-règles choisies
                for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                    R = MetaUtils.getRule(rules, metaRule, factBase);

                    if (R != null) {
                        factBase.add(FactListUtils.getFact(R.getBody()));
                        break;
                    }
                }

                this.support.firePropertyChange("error", null, "Ambiguïté, veuillez changer vos méta-règles.");
                if (R == null) {
                    this.support.firePropertyChange("error", null, "Ambiguïté, veuillez changer vos méta-règles.");
                    return null;
                    // throw new Exception("Ambiguïté, veuillez changer vos méta-règles.");
                }
                // Ajouter la règle déclenchée dans la liste de règles
                visitedRules.add(R);
            } else {
                factBase.add(FactListUtils.getFact(rules.get(0).getBody()));

                // Ajouter la règle déclenchée dans la liste de règles
                visitedRules.add(rules.get(0));
            }

            if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
                butNonTrouve = false;
        }

        return factBase;
    }

    public List<Fact> frontChainWidth(List<String> but) throws Exception {
        List<Fact> factBase = new ArrayList<>(FactBase.factBase);
        List<Rule> visitedRules = new ArrayList<>();

        boolean butNonTrouve = true;

        if (RuleList.hasIncFacts())
            throw new Exception("La base de règles contient des données incohérentes, opération impossible.");

        while (butNonTrouve) {

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
                factBase.add(FactListUtils.getFact(r.getBody()));

            // Ajouter la règle déclenchée dans la liste de règles
            visitedRules.addAll(rules);

            // Cas où un but a été ajouté dans la base de faits
            if (factBase.stream().anyMatch(e -> but.contains(e.getKey())))
                butNonTrouve = false;

        }

        return factBase;
    }

}
