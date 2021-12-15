package fr.univangers.master1.hogggrenon.models.facts;

import fr.univangers.master1.hogggrenon.models.Fact;

public class FactWithPremise implements Fact {

    private final String premise;
    private final boolean value;

    public FactWithPremise(String premise, boolean value) {
        this.premise = premise;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.premise;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
