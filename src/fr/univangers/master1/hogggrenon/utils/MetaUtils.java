package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Rule;

import java.util.*;

public class MetaUtils {

    public enum MetaRule {
        ASCENDANT,
        DESCENDANT,
        MOST_RECENT,
        MOST_SPECIFIC
    }

    private static List<MetaRule> metaRules = new ArrayList<>();

    public static void initializeMetaRules() {
        metaRules = new ArrayList<>();
    }

    public static boolean isAMetaRule(MetaRule rule) {
        return metaRules.contains(rule);
    }

    public static void addNewMetaRule(MetaRule rule){
        metaRules.add(rule);
    }

    public static void removeMetaRule(MetaRule rule) {
        if (isAMetaRule(rule))
            metaRules.remove(rule);
    }

    public static Rule getRule(MetaRule rule, List<String> facts) {
        switch (rule) {
            case ASCENDANT:
                return RuleList.getRuleBase().get(0);

            case DESCENDANT:
                return RuleList.getRuleBase().get(RuleList.getRuleBase().size());

            case MOST_SPECIFIC:
                int maxElements = -1;
                Rule r = null;

                for (Rule R : RuleList.getRuleBase())
                {
                    if (R.getHead().length() > maxElements) {
                        r = R;
                        maxElements = R.getHead().length();
                    } else if (R.getHead().length() == maxElements) {
                        return null;
                    }
                }

                return r;

            case MOST_RECENT:
                if (facts.isEmpty())
                    return null;

                List<Rule> ruleList = RuleList.getRuleBase();

                for (int i = facts.size() - 1; i >= 0; i--) {

                    // Filtrer les règles dont la tête contient le fait le plus récent
                    int finalI = i;
                    ruleList = ruleList.stream().filter(e -> e.getHead().contains(facts.get(finalI))).toList();

                    // Si aucune règle à elle seule respecte la méta-règle (donc aucune règle unique ayant le plus de faits récents)
                    if (ruleList.isEmpty())
                        return null;

                    // S'il ne reste qu'une règle, la choisir
                    if (ruleList.size() == 1)
                        return ruleList.get(0);
                }

                return null;

            default:
                return null;
        }
    }

    public static List<MetaRule> getMetaRules() {
        return metaRules;
    }

}
