package fr.univangers.master1.hogggrenon.utils.nodes;

public class Node<V> implements AbstractNode {

    public enum NodeType {
        AND, OR, NEG,
        GT, GTE, LT, LTE,
        EQU, INEQU,
        ADD, SUB, MULT, DIV
    }

    private final AbstractNode leftNode;
    private final AbstractNode rightNode;
    private final NodeType type;

    public Node(AbstractNode leftNode, AbstractNode rightNode, NodeType type) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.type = type;
    }

    @Override
    public AbstractNode getLeftNode() {
        return this.leftNode;
    }

    @Override
    public AbstractNode getRightNode() {
        return this.rightNode;
    }

    public NodeType getType() {
        return this.type;
    }
}
