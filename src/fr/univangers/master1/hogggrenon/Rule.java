package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.utils.FactBase;

import java.util.List;

public class Rule {

    private List<String> head;
    private List<String> body;

    public Rule(List<String> head, List<String> body)  {
        this.head = head;
        this.body = body;
    }

    public boolean checkRule() {
        for (String s : body) {
            if (!FactBase.isAFact(s))
                return false;
        }

        return true;
    }

    public List<String> getHead() {
        return this.head;
    }

    public List<String> getBody() {
        return this.body;
    }

}
