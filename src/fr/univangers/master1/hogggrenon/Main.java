package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.interfaces.HomeGUI;
import fr.univangers.master1.hogggrenon.interfaces.MetaGUI;
import fr.univangers.master1.hogggrenon.interfaces.SystemGUI;
import fr.univangers.master1.hogggrenon.utils.*;
import fr.univangers.master1.hogggrenon.utils.nodes.AbstractNode;
import fr.univangers.master1.hogggrenon.utils.nodes.Leaf;
import fr.univangers.master1.hogggrenon.utils.nodes.Node;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        /*FactListUtils.addFact(new FactWithVar("a", 3.0f));

        Parser P = new Parser("(a == 5)");

        AbstractNode root = (P.createTree(P.infixToPostfix()));

        System.out.println(((Leaf<?>) root.getLeftNode()).getValue());
        System.out.println(((Node<?>) root).getType());
        System.out.println(((Leaf<?>) root.getRightNode()).getValue());

        System.out.println(P.valuateTree(root));*/


        // Initialiser les bases de faits et de règles
        FactListUtils.initializeFactList();
        FactBase.initializeFactList();
        RuleList.initializeRuleBase();
        MetaUtils.initializeMetaRules();

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
