package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Fact;
import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.Rule;

import java.util.ArrayList;
import java.util.List;

public class StrategyUtils {

    public static List<String> backChain(List<String> but) throws Exception {
        List<String> file, facts = new ArrayList<>();
        file = new ArrayList<>(but);

        while (file.size() != 0) {
            String fact = file.get(0);

            if (RuleList.getRuleBase().stream().anyMatch(e -> e.getBody().contains(fact))) {


                // Listes des règles valables (déclenchables)
                List<Rule> rules = RuleList.getRuleBase().stream().filter(e -> {
                    Parser P = new Parser(e.getHead());
                    return e.getBody().contains(fact)
                            && P.valuateTree(P.createTree(P.infixToPostfix()));
                }).toList();

                for (Rule R : rules) {
                    System.out.println(R.getHead() + " -> " + R.getBody());
                    System.out.println(" ");
                }

                // Choisir la règle suivant les méta-règles
                if (rules.size() > 1) {
                    Rule R = null;

                    // Parcourir les règles selon les méta-règles choisies
                    for (MetaUtils.MetaRule metaRule : MetaUtils.getMetaRules()) {
                        R = MetaUtils.getRule(metaRule, facts);

                        if (R != null) {
                            Parser local = new Parser(R.getHead());
                            file.addAll(local.getFactsInExpression(local.infixToPostfix()));
                            break;
                        }
                    }

                    // Si aucune règle n'a été choisie et que l'ensemble des méta-règles ont été parcourues
                    if (R == null)
                        throw new Exception("Il y a une ambiguïté dans le choix des règles. Recommencez en utilisant d'autres méta-règles.");

                } else {
                    Parser local = new Parser(rules.get(0).getHead());
                    file.addAll(local.getFactsInExpression(local.infixToPostfix()));
                }
            } else {
                for (Fact s : FactBase.factBase)
                    System.out.println(s.getKey() + " -> " + s.getValue());

                System.out.println(fact);

                if (FactBase.isAFact(fact)) {
                    facts.add(fact);
                    System.out.println("Nouveau fait");
                } else {
                    System.out.println("Dénombrable ?");
                    if (!FactListUtils.isAFact(fact))
                        throw new Exception("Le fait est une erreur.");
                }
            }

            file.remove(0);
        }

        return facts;
    }

}
