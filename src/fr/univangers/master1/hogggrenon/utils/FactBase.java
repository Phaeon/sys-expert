package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Fact;

import java.util.*;

public class FactBase {

    public static List<Fact> factBase = new ArrayList<>();

    public static void initializeFactList() {
        factBase = new ArrayList<>();
    }

    public static void addFact(Fact fact) {
        factBase.add(fact);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact))
            for (Fact f : factBase)
                if (Objects.equals(f.getKey(), fact)) {
                    factBase.remove(f);
                    return;
                }
    }

    public static void removeFact(int index) {
        if (index < factBase.size())
            factBase.remove(index);
    }

    public static boolean isAFact(String varName) {
        return factBase.stream().anyMatch(e -> Objects.equals(e.getKey(), varName));
    }

    public static Fact getFact(String fact) {
        for (Fact f : factBase)
            if (f.getKey().equals(fact))
                return f;

        return null;
    }

}
