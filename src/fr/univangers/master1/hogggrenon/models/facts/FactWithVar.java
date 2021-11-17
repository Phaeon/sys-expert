package fr.univangers.master1.hogggrenon.models.facts;

import fr.univangers.master1.hogggrenon.models.Fact;

public class FactWithVar implements Fact {

    private final String varName;
    private final Object value;

    public FactWithVar(String varName, Object value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.varName;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
