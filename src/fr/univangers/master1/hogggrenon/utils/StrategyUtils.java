package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Rule;

import java.util.ArrayList;
import java.util.List;

public class StrategyUtils {

    public static String forwardChain() {

        return "";
    }

    public static List<String> backChain(String but) throws Exception {
        List<String> file = new ArrayList<>(), facts = new ArrayList<>();
        file.add(but);

        while (file.size() != 0) {
            String fact = file.get(0);

            if (RuleBase.getRuleBase().stream().anyMatch(e -> e.getBody().contains(fact))) {
                for (Rule R : RuleBase.getRuleBase().stream().filter(e -> e.getBody().contains(fact)).toList()) {
                    file.addAll(R.getHead());
                    // System.out.println("Ajout des faits en tête de règle dans la file.");
                }
            } else {
                if (FactBase.isAFact(fact)) {
                    facts.add(fact);
                    // System.out.println("Nouveau fait");

                } else {
                    // System.out.println("Dénombrable ?");
                    if (!FactList.isAFact(fact))
                        throw new Exception("Le fait est une erreur.");
                }
            }

            file.remove(0);
        }

        return facts;
    }

}
