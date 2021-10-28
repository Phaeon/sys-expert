package fr.univangers.master1.hogggrenon;

public class Fact {

    private final String varName;
    private final Object value;

    public Fact(String varName, Object value) {
        this.varName = varName;
        this.value = value;
    }

    public String getVarName() {
        return this.varName;
    }

    public Object getValue() {
        return this.value;
    }
}
