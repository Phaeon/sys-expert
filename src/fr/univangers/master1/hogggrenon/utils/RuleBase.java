package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    public static List<Rule> ruleBase;

    public static void initializeRuleBase() {
        ruleBase = new ArrayList<>();
    }

    public static void addRule(List<String> head, List<String> body) {
        Rule rule = new Rule(head, body);

        if (!isARule(rule)) ruleBase.add(rule);
    }

    public static void removeRule(Rule rule) {
        if (isARule(rule)) ruleBase.remove(rule);
    }

    public static boolean isARule(Rule rule) {
        return ruleBase.contains(rule);
    }

    public static List<Rule> getRuleBase() {
        return ruleBase;
    }

}
