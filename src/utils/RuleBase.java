package utils;

import java.util.ArrayList;
import java.util.List;

public class RuleBase {

    public static List<String> ruleBase;

    public static void initializeRuleBase() {
        ruleBase = new ArrayList<>();
    }

    public static void addRule(String head, List<String> body) {

    }

    public static void removeFact(String fact) {

    }

    public static boolean isAFact(String fact) {

    }

    public static List<String> getRuleBase() {
        return ruleBase;
    }

}
