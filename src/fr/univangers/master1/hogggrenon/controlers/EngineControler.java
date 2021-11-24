package fr.univangers.master1.hogggrenon.controlers;

import fr.univangers.master1.hogggrenon.models.utils.*;
import fr.univangers.master1.hogggrenon.views.HomeGUI;

public class EngineControler {

    public EngineControler() {
        FactListUtils.initializeFactList(); // Initialisation de la liste de faits
        FactBase.initializeFactList(); // Initialisation de la base de faits
        RuleList.initializeRuleBase(); // Initialisation de la base de règles
        MetaUtils.initializeMetaRules(); // Initialisation des méta-règles
        IncFactListUtils.initializeFactList(); // Initialisation de la base de faits incohérents

        new HomeGUI();
    }
}
