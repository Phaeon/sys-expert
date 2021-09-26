package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.interfaces.HomeGUI;
import fr.univangers.master1.hogggrenon.interfaces.MetaGUI;
import fr.univangers.master1.hogggrenon.interfaces.SystemGUI;
import fr.univangers.master1.hogggrenon.utils.FactBase;
import fr.univangers.master1.hogggrenon.utils.FactList;
import fr.univangers.master1.hogggrenon.utils.MetaUtils;
import fr.univangers.master1.hogggrenon.utils.RuleBase;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // Initialiser les bases de faits et de règles
        FactList.initializeFactList();
        FactBase.initializeFactBase();
        RuleBase.initializeRuleBase();
        MetaUtils.initializeMetaRules();

        new HomeGUI();

        /*
        FactList.addFact("Fact 1", true);
        FactList.addFact("Fact 2", true);
        FactList.addFact("Fact 3", true);
        FactList.addFact("Fact 4", true);
        FactList.addFact("Fact 5", true);
        FactList.addFact("Fact 6", true);
        FactList.addFact("Fact 7", true);
        FactList.addFact("Fact 8", true);
        FactList.addFact("Fact 9", true);
        FactList.addFact("Fact 10", true);


        FactBase.addFact("Fact 5", true);
        FactBase.addFact("Fact 6", true);
        FactBase.addFact("Fact 9", true);

        RuleBase.addRule(Arrays.asList("Fact 2", "Fact 3"), Arrays.asList("Fact 1"));
        RuleBase.addRule(Arrays.asList("Fact 4"), Arrays.asList("Fact 1"));
        RuleBase.addRule(Arrays.asList("Fact 5"), Arrays.asList("Fact 2"));
        RuleBase.addRule(Arrays.asList("Fact 6", "Fact 7"), Arrays.asList("Fact 3"));
        RuleBase.addRule(Arrays.asList("Fact 8", "Fact 9"), Arrays.asList("Fact 4"));
        RuleBase.addRule(Arrays.asList("Fact 10"), Arrays.asList("Fact 7"));

        List<String> facts = null;
        try {
            facts = Moteur.backChain("Fact 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert facts != null;
        for (String s : facts)
            System.out.println(s + " "); */

    }
}
