package fr.univangers.master1.hogggrenon.models.nodes;

import fr.univangers.master1.hogggrenon.models.AbstractNode;

public class Leaf<V> implements AbstractNode {

    private final V value;

    public Leaf(V value) {
        this.value = value;
    }

    public V getValue() {
        return this.value;
    }

    @Override
    public AbstractNode getLeftNode() {
        return null;
    }

    @Override
    public AbstractNode getRightNode() {
        return null;
    }
}

