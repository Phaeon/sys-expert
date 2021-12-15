package fr.univangers.master1.hogggrenon.models;

import fr.univangers.master1.hogggrenon.Parser;

import java.util.List;

public class Rule {

    private final String head;
    private final String body;

    public Rule(String head, String body)  {
        this.head = head;
        this.body = body;
    }

    public boolean checkRule() {
        Parser p = new Parser(head);
        return p.validPostfix(p.infixToPostfix());
    }

    public String getHead() {
        return this.head;
    }

    public String getBody() {
        return this.body;
    }

}
