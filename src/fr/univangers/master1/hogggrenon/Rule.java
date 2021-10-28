package fr.univangers.master1.hogggrenon;

import java.util.List;

public class Rule {

    private final String head;
    private final List<String> body;

    public Rule(String head, List<String> body)  {
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

    public List<String> getBody() {
        return this.body;
    }

}
