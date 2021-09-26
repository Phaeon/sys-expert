package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Rule;

import java.util.ArrayList;
import java.util.List;

public class StrategyUtils {

    public static List<String> backChain(String but) throws Exception {
        List<String> file = new ArrayList<>(), facts = new ArrayList<>();
        file.add(but);

        while (file.size() != 0) {
            String fact = file.get(0);

            if (RuleBase.getRuleBase().stream().anyMatch(e -> e.getBody().contains(fact))) {
                // Listes des règles valables (déclenchables)
                List<Rule> rules = RuleBase.getRuleBase().stream().filter(e -> e.getBody().contains(fact)).toList();

                // Choisir la règle suivant les méta-règles
                if (rules.size() > 1) {
                    Rule R = null;

                    // Parcourir les règles selon les méta-règles choisies
                    for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                        R = MetaUtils.getRule(metaRule, facts);

                        if (R != null) {
                            file.addAll(R.getHead());
                            break;
                        }
                    }

                    // Si aucune règle n'a été choisie et que l'ensemble des méta-règles ont été parcourues
                    if (R == null)
                        throw new Exception("Il y a une ambiguïté dans le choix des règles. Recommencez en utilisant d'autres méta-règles.");

                } else
                    file.addAll(rules.get(0).getHead());
            } else {
                for (String s : FactBase.getFactBase().keySet())
                    System.out.println(s);

                if (FactBase.isAFact(fact)) {
                    facts.add(fact);
                    System.out.println("Nouveau fait");
                } else {
                    System.out.println("Dénombrable ?");
                    if (!FactList.isAFact(fact))
                        throw new Exception("Le fait est une erreur.");
                }
            }

            file.remove(0);
        }

        return facts;
    }

}
