package fr.univangers.master1.hogggrenon.models.utils;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.models.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleList {

    public static List<Rule> ruleBase;

    public static void initializeRuleBase() {
        ruleBase = new ArrayList<>();
    }

    public static void addRule(String head, String body) {
        Rule rule = new Rule(head, body);

        if (!isARule(rule))
            ruleBase.add(rule);
    }

    public static void removeRule(int index) {
        if (index < ruleBase.size())
            ruleBase.remove(index);
    }

    public static boolean isARule(Rule rule) {
        if (ruleBase.size() == 0) return false;

        for (Rule r : ruleBase) {
            if (r.getHead().equals(rule.getHead())
                    && r.getBody().equals(rule.getBody()))
                return true;
        }

        return false;
    }

    public static List<Rule> getRuleBase() {
        return ruleBase;
    }

    public static boolean hasIncFacts() {

        // Vérifier pour chaque règle si la tête ou la conclusion contient des faits incohérents
        for (Rule R : ruleBase) {
            // Récupérer les faits de la tête
            Parser P = new Parser(R.getHead());
            for (String fact : P.getFactsInExpression(P.infixToPostfix()))
                if (IncFactListUtils.isAnIncFact(fact))
                    return true;

            // Vérifier le fait conclusion
            if (IncFactListUtils.isAnIncFact(R.getBody()))
                return true;
        }

        return false;
    }

}
