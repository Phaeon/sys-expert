package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.utils.FactBase;
import fr.univangers.master1.hogggrenon.utils.RuleBase;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Initialiser les bases de faits et de règles
        FactBase.initializeFactBase();
        RuleBase.initializeRuleBase();

        // Tests
        String fact1 = "Test 1", fact2 = "Fact 2", factFalse = "Bad Fact";

        FactBase.addFact(fact1);
        FactBase.addFact(fact2);

        RuleBase.addRule(fact1, Arrays.asList(fact1, fact2));
        RuleBase.addRule(fact2, Arrays.asList(fact1, factFalse));

        for (Rule r : RuleBase.getRuleBase())
            if (!r.checkRule()) {
                System.err.println("ERROR: Une règle n'est pas valide.");
                return;
            }

        System.out.println("Vos règles sont valides.");

    }

}
