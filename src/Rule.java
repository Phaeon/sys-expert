import utils.FactBase;

import java.util.List;

public class Rule {

    private String head;
    private List<String> body;

    public Rule(String head, List<String> body) {
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

    public String getHead() {
        return this.head;
    }

}
