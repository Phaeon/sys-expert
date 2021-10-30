package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.utils.FactListUtils;
import fr.univangers.master1.hogggrenon.utils.nodes.AbstractNode;
import fr.univangers.master1.hogggrenon.utils.nodes.Leaf;
import fr.univangers.master1.hogggrenon.utils.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Parser {

    private final String expression;

    public Parser(String expression) {
        this.expression = expression.replaceAll("\"", "'");
    }

    public int prec(String c) {
        return switch (c) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1;
        };
    }

    public static boolean isAValidVariable(String s) {
        return s.matches("^[a-zA-Z]([a-zA-Z0-9])*$")
                && !isBoolean(s);
    }

    public static boolean isNumeric(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException ignored) { }

        return false;
    }

    public static boolean isBoolean(String s) {
        return s.equals("true") || s.equals("false");
    }

    public static boolean isString(String s) {
        return s.matches("'([^']|\\\')*'");
    }

    // Renvoie null en cas d'erreur (pas de chaîne ou chaîne mal formatée)
    public Stack<String> infixToPostfix() {
        String op = "&|~<>"; // TODO : TRAITER NEGATION
        String arith = "+*-/^";

        Stack<String> stack = new Stack<>();
        Stack<String> postfix = new Stack<>();
        StringBuilder builder = new StringBuilder();

        int index = 0;

        while (index < this.expression.length()) {
            if (this.expression.charAt(index) == '(')
                stack.add("(");

            else if (this.expression.charAt(index) == ')') {
                if (!builder.isEmpty()) {
                    postfix.add(String.valueOf(builder));
                    builder = new StringBuilder();
                }

                while (!stack.isEmpty()
                        && !stack.peek().equals("("))
                    postfix.add(stack.pop());

                stack.pop();
            }
            else if (arith.contains(this.expression.charAt(index) + ""))
            {
                if (!builder.isEmpty()) {
                    postfix.add(String.valueOf(builder));
                    builder = new StringBuilder();
                }

                while (!stack.isEmpty() &&
                        prec(String.valueOf(this.expression.charAt(index))) <= prec(stack.peek()))
                    postfix.add(stack.pop());

                stack.add(this.expression.charAt(index) + "");
            }

            else if (this.expression.substring(index).startsWith("<=") || this.expression.substring(index).startsWith(">=")
                    || this.expression.substring(index).startsWith("==") || this.expression.substring(index).startsWith("!="))
            {
                if (!builder.isEmpty()) {
                    postfix.add(String.valueOf(builder));
                    builder = new StringBuilder();
                }

                while (!stack.isEmpty() && !stack.peek().equals("(") && !op.contains(stack.peek()))
                    postfix.add(stack.pop());

                stack.add(this.expression.substring(index, index+2));
                index++;
            }

            else if (op.contains(this.expression.charAt(index) + ""))
            {
                if (!builder.isEmpty()) {
                    postfix.add(String.valueOf(builder));
                    builder = new StringBuilder();
                }

                while (!stack.isEmpty() && !stack.peek().equals("("))
                    postfix.add(stack.pop());

                stack.add(this.expression.charAt(index) + "");
            }

            // On considère que le nom de la variable a déjà été validée
            else if (Character.isLetterOrDigit(expression.charAt(index))
                    || expression.charAt(index) == '\''
                    || expression.charAt(index) == '.')
                builder.append(expression.charAt(index));

            index++;
        }

        if (!builder.isEmpty())
            postfix.add(String.valueOf(builder));

        while (!stack.isEmpty()) {
            if (stack.peek().equals("("))
                return null;
            postfix.add(stack.pop());
        }

        return postfix;
    }

    /**
     * Vérifie qu'une forme postfixe d'une
     * expression logique est correctement formée
     * @param postfix Expression postfixe
     * @return Expression valide
     */
    public boolean validPostfix(Stack<String> postfix)
    {

        String opLog = "&|", opCompSimple = "<>";
        String arith = "+*-/^";

        Stack<String> reversed = new Stack<>();
        Stack<String> aux = new Stack<>();

        int size = postfix.size();

        for (int i = 0; i < size; i++)
            reversed.push(postfix.pop());

        while (!reversed.isEmpty())
        {
            String current = reversed.pop();

            if (arith.contains(current)) // +, -, ...
            {
                if (aux.size() < 2)
                    return false;

                String first = aux.pop(), second = aux.pop();

                if (!isNumeric(first)
                        || !isNumeric(second))
                    return false;

                float f = Float.parseFloat(first) + Float.parseFloat(second);

                aux.push(String.valueOf(f));
            }
            else if (opLog.contains(current)) // &, |
            {
                if (aux.size() < 2)
                    return false;

                String first = aux.pop(), second = aux.pop();

                if (!isBoolean(first)
                        || !isBoolean(second))
                    return false;

                boolean b;
                if (current.equals("|"))
                    b = Boolean.parseBoolean(first) || Boolean.parseBoolean(second);
                else
                    b = Boolean.parseBoolean(first) && Boolean.parseBoolean(second);

                aux.push(String.valueOf(b));

            }
            else if (current.equals("==")
                    || current.equals("!="))
            {
                if (aux.size() < 2)
                    return false;

                String first = aux.pop(), second = aux.pop();

                if (isNumeric(first) && isNumeric(second)) {
                    if (current.equals("=="))
                        aux.push(String.valueOf(Float.parseFloat(first) == Float.parseFloat(second)));
                    else
                        aux.push(String.valueOf(Float.parseFloat(first) != Float.parseFloat(second)));
                } else if (isBoolean(first) && isBoolean(second)) {
                    if (current.equals("=="))
                        aux.push(String.valueOf(Boolean.parseBoolean(first) == Boolean.parseBoolean(second)));
                    else
                        aux.push(String.valueOf(Boolean.parseBoolean(first) != Boolean.parseBoolean(second)));
                } else if (isString(first) && isString(second)) {
                    if (current.equals("=="))
                        aux.push(String.valueOf(first.equals(second)));
                    else
                        aux.push(String.valueOf(!first.equals(second)));
                } else
                    return false;


            }
            else if (current.equals("<=")
                    || current.equals(">="))
            {
                if (aux.size() < 2)
                    return false;

                String first = aux.pop(), second = aux.pop();

                if (isNumeric(first) && isNumeric(second)) {
                    if (current.equals("<="))
                        aux.push(String.valueOf(Float.parseFloat(first) <= Float.parseFloat(second)));
                    else
                        aux.push(String.valueOf(Float.parseFloat(first) >= Float.parseFloat(second)));
                }
                else
                    return false;
            }
            else if (opCompSimple.contains(current)) // <, > ...
            {
                if (aux.size() < 2)
                    return false;

                String first = aux.pop(), second = aux.pop();

                if (isNumeric(first) && isNumeric(second)) {
                    if (current.equals("<"))
                        aux.push(String.valueOf(Float.parseFloat(first) < Float.parseFloat(second)));
                    else
                        aux.push(String.valueOf(Float.parseFloat(first) > Float.parseFloat(second)));
                }
                else
                    return false;

            }
            else // Operand
            {
                if (FactListUtils.getFact(current) != null)
                    aux.push(String.valueOf(Objects.requireNonNull(FactListUtils.getFact(current)).getValue()));
                else if (isBoolean(current) || isNumeric(current) || isString(current))
                    aux.push(current);
                else return false;
            }
        }

        return isBoolean(aux.peek())
                && aux.size() == 1;
    }


    /**
     * Vérifie qu'une forme postfixe d'une
     * expression logique est correctement formée
     * @param postfix Expression postfixe
     * @return Un arbre binaire
     */
    public AbstractNode createTree(Stack<String> postfix) {
        String opLog = "&|", opCompSimple = "<>";
        String arith = "+*-/^";

        Stack<String> reversed = new Stack<>();
        Stack<AbstractNode> tree = new Stack<>();

        int size = postfix.size();

        for (int i = 0; i < size; i++)
            reversed.push(postfix.pop());

        while (!reversed.isEmpty())
        {
            String current = reversed.pop();

            if (arith.contains(current)) // +, -, ...
            {
                AbstractNode first = tree.pop(), second = tree.pop();

                switch (current) {
                    case "+" -> tree.push(new Node<>(first, second, Node.NodeType.ADD));
                    case "-" -> tree.push(new Node<>(first, second, Node.NodeType.SUB));
                    case "*" -> tree.push(new Node<>(first, second, Node.NodeType.MULT));
                    case "/" -> tree.push(new Node<>(first, second, Node.NodeType.DIV));
                }
            }
            else if (opLog.contains(current)) // &, |
            {
                AbstractNode first = tree.pop(), second = tree.pop();

                if (current.equals("|"))
                    tree.push(new Node<>(first, second, Node.NodeType.OR));
                else
                    tree.push(new Node<>(first, second, Node.NodeType.AND));

            }
            else if (current.equals("==")
                    || current.equals("!="))
            {
                AbstractNode first = tree.pop(), second = tree.pop();

                if (current.equals("=="))
                    tree.push(new Node<>(first, second, Node.NodeType.EQU));
                else
                    tree.push(new Node<>(first, second, Node.NodeType.INEQU));
            }
            else if (current.equals("<=")
                    || current.equals(">="))
            {
                AbstractNode first = tree.pop(), second = tree.pop();

                if (current.equals("<="))
                    tree.push(new Node<>(first, second, Node.NodeType.LTE));
                else
                    tree.push(new Node<>(first, second, Node.NodeType.GTE));
            }
            else if (opCompSimple.contains(current)) // <, > ...
            {
                AbstractNode first = tree.pop(), second = tree.pop();

                if (current.equals("<"))
                    tree.push(new Node<>(first, second, Node.NodeType.LT));
                else
                    tree.push(new Node<>(first, second, Node.NodeType.GT));
            }
            else // Operand
            {
                if (FactListUtils.isAFact(current))
                    tree.push(new Leaf<>(Objects.requireNonNull(FactListUtils.getFact(current)).getValue()));
                else {
                    if (isNumeric(current))
                        tree.push(new Leaf<>(Float.parseFloat(current)));
                    else if (isBoolean(current))
                        tree.push(new Leaf<>(Boolean.parseBoolean(current)));
                    else
                        tree.push(new Leaf<>(current));
                }
            }
        }

        return tree.peek();
    }

    /**
     * Valuer une expression représentée par un arbre
     * @param root L'arbre binaire
     * @return La valuation de l'expression
     */
    public boolean valuateTree(AbstractNode root) {
        if (root instanceof Node) {
            switch (((Node<?>) root).getType()) {
                case AND -> {
                    return valuateTree(root.getLeftNode()) && valuateTree(root.getRightNode());
                }
                case OR -> {
                    return valuateTree(root.getLeftNode()) || valuateTree(root.getRightNode());
                }
                case GT -> {
                    return valuateValue(root.getLeftNode()) < valuateValue(root.getRightNode());
                }
                case GTE -> {
                    return valuateValue(root.getLeftNode()) <= valuateValue(root.getRightNode());
                }
                case LT -> {
                    return valuateValue(root.getLeftNode()) > valuateValue(root.getRightNode());
                }
                case LTE -> {
                    return valuateValue(root.getLeftNode()) >= valuateValue(root.getRightNode());
                }
                case EQU -> {
                    if (root.getLeftNode() instanceof Node && root.getRightNode() instanceof Node)
                        return valuateTree(root.getLeftNode()) == valuateTree(root.getRightNode());
                    else if (root.getLeftNode() instanceof Node && root.getRightNode() instanceof Leaf)
                    {
                        // Si un noeud est une feuille, il n'est possible que d'avoir un réel ou un booléen
                        // (Un string est comparé directement avec un string, donc obligatoirement une feuille)
                        Object o = ((Leaf<?>) root.getRightNode()).getValue();
                        if (o instanceof Float)
                            return Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                        else
                            return valuateTree(root.getLeftNode()) == valuateTree(root.getRightNode());
                    }
                    else if (root.getLeftNode() instanceof Leaf && root.getRightNode() instanceof Node)
                    {
                        Object o = ((Leaf<?>) root.getLeftNode()).getValue();
                        if (o instanceof Float)
                            return Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                        else
                            return valuateTree(root.getLeftNode()) == valuateTree(root.getRightNode());
                    }
                    else {
                        // Si les deux sont des feuilles, évaluer selon les types (vérifier la classe d'un seul
                        // objet si on considère que le postfixe a déjà été vérifié)

                        Object o = ((Leaf<?>) root.getLeftNode()).getValue();
                        Object o2 = ((Leaf<?>) root.getRightNode()).getValue();
                        if (o instanceof Boolean)
                            return Objects.equals(valuateTree(root.getLeftNode()), valuateTree(root.getRightNode()));
                        else if (o instanceof String)
                            return o.equals(o2);
                        else
                            return Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                    }
                }
                case INEQU -> {
                    if (root.getLeftNode() instanceof Node && root.getRightNode() instanceof Node)
                        return valuateTree(root.getLeftNode()) != valuateTree(root.getRightNode());
                    else if (root.getLeftNode() instanceof Node && root.getRightNode() instanceof Leaf)
                    {
                        // Si un noeud est une feuille, il n'est possible que d'avoir un réel ou un booléen
                        // (Un string est comparé directement avec un string, donc obligatoirement une feuille)
                        Object o = ((Leaf<?>) root.getRightNode()).getValue();
                        if (o instanceof Float)
                            return !Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                        else
                            return valuateTree(root.getLeftNode()) != valuateTree(root.getRightNode());
                    }
                    else if (root.getLeftNode() instanceof Leaf && root.getRightNode() instanceof Node)
                    {
                        Object o = ((Leaf<?>) root.getLeftNode()).getValue();
                        if (o instanceof Float)
                            return !Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                        else
                            return valuateTree(root.getLeftNode()) != valuateTree(root.getRightNode());
                    }
                    else {
                        Object o = ((Leaf<?>) root.getLeftNode()).getValue();
                        Object o2 = ((Leaf<?>) root.getRightNode()).getValue();
                        if (o instanceof Boolean)
                            return !Objects.equals(valuateTree(root.getLeftNode()), valuateTree(root.getRightNode()));
                        else if (o instanceof String)
                            return !o.equals(o2);
                        else
                            return !Objects.equals(valuateValue(root.getLeftNode()), valuateValue(root.getRightNode()));
                    }
                }
                default -> {
                    return false;
                }
            }
        } else {
            return (boolean) ((Leaf<?>) root).getValue();
        }
    }

    public Float valuateValue(AbstractNode root) {
        if (root instanceof Node) {
            switch (((Node<?>) root).getType()) {
                case ADD -> {
                    return valuateValue(root.getLeftNode()) + valuateValue(root.getRightNode());
                }
                case DIV -> {
                    return valuateValue(root.getLeftNode()) / valuateValue(root.getRightNode());
                }
                case SUB -> {
                    return valuateValue(root.getLeftNode()) - valuateValue(root.getRightNode());
                }
                case MULT -> {
                    return valuateValue(root.getLeftNode()) * valuateValue(root.getRightNode());
                }
                default -> {
                    return 0.0f;
                }
            }
        } else {
            return (Float) ((Leaf<?>) root).getValue();
        }
    }

    public List<String> getFactsInExpression(Stack<String> postfix)
    {
        List<String> facts = new ArrayList<>();
        Stack<String> aux = postfix;

        while (!aux.isEmpty())
        {
            String current = aux.pop();
            if (FactListUtils.isAFact(current))
                facts.add(current);
        }

        return facts;
    }



}

