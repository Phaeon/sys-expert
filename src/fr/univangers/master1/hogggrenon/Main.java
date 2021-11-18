package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.models.AbstractNode;
import fr.univangers.master1.hogggrenon.models.facts.FactWithPremise;
import fr.univangers.master1.hogggrenon.models.facts.FactWithVar;
import fr.univangers.master1.hogggrenon.models.nodes.Leaf;
import fr.univangers.master1.hogggrenon.models.nodes.Node;
import fr.univangers.master1.hogggrenon.views.HomeGUI;
import fr.univangers.master1.hogggrenon.models.utils.*;
import fr.univangers.master1.hogggrenon.views.InformationBox;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        /*FactListUtils.addFact(new FactWithVar("a", 3.0f));
        FactListUtils.addFact(new FactWithPremise("b", false));

        Parser P = new Parser("~(a == 5) & (b & ~(a == 9))"); // a 5 == ~ b ~ a 9 == & &

       for (String S : P.infixToPostfix())
           System.out.print(S + " ");

       if (P.valuateTree(P.createTree(P.infixToPostfix())))
           System.out.print("Hell Yes !");
       else System.out.print("Hell No !"); */


        FactListUtils.initializeFactList(); // Initialisation de la liste de faits
        FactBase.initializeFactList(); // Initialisation de la base de faits
        RuleList.initializeRuleBase(); // Initialisation de la base de règles
        MetaUtils.initializeMetaRules(); // Initialisation des méta-règles
        IncFactListUtils.initializeFactList(); // Initialisation de la base de faits incohérents

        new HomeGUI();
        /*
        FactList.addFact("FactWithVar 1", true);
        FactList.addFact("FactWithVar 2", true);
        FactList.addFact("FactWithVar 3", true);
        FactList.addFact("FactWithVar 4", true);
        FactList.addFact("FactWithVar 5", true);
        FactList.addFact("FactWithVar 6", true);
        FactList.addFact("FactWithVar 7", true);
        FactList.addFact("FactWithVar 8", true);
        FactList.addFact("FactWithVar 9", true);
        FactList.addFact("FactWithVar 10", true);


        FactBase.addFact("FactWithVar 5", true);
        FactBase.addFact("FactWithVar 6", true);
        FactBase.addFact("FactWithVar 9", true);

        RuleBase.addRule(Arrays.asList("FactWithVar 2", "FactWithVar 3"), Arrays.asList("FactWithVar 1"));
        RuleBase.addRule(Arrays.asList("FactWithVar 4"), Arrays.asList("FactWithVar 1"));
        RuleBase.addRule(Arrays.asList("FactWithVar 5"), Arrays.asList("FactWithVar 2"));
        RuleBase.addRule(Arrays.asList("FactWithVar 6", "FactWithVar 7"), Arrays.asList("FactWithVar 3"));
        RuleBase.addRule(Arrays.asList("FactWithVar 8", "FactWithVar 9"), Arrays.asList("FactWithVar 4"));
        RuleBase.addRule(Arrays.asList("FactWithVar 10"), Arrays.asList("FactWithVar 7"));

        List<String> facts = null;
        try {
            facts = Moteur.backChain("FactWithVar 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert facts != null;
        for (String s : facts)
            System.out.println(s + " "); */

    }
}
