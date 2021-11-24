package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.controlers.EngineControler;
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
        new EngineControler();
    }
}
