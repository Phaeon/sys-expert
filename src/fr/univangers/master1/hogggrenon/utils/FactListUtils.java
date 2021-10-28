package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Fact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FactListUtils {

    public static List<Fact> factList = new ArrayList<>();

    public static void initializeFactList() {
        factList = new ArrayList<>();
    }

    public static void addFact(String varName, Object value) {
        Fact fact = new Fact(varName, value);

        factList.add(fact);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact))
            for (Fact f : factList)
                if (Objects.equals(f.getVarName(), fact)) {
                    factList.remove(f);
                    return;
                }
    }

    public static void removeFact(int index) {
        if (index < factList.size())
            factList.remove(index);
    }

    public static boolean isAFact(String varName) {
        return factList.stream().anyMatch(e -> Objects.equals(e.getVarName(), varName));
    }

    public static Fact getFact(String varName) {
        for (Fact fact : factList)
            if (Objects.equals(fact.getVarName(), varName))
                return fact;

        return null;
    }

}
