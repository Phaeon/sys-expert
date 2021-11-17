package fr.univangers.master1.hogggrenon.models.utils;

import fr.univangers.master1.hogggrenon.models.Fact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FactListUtils {

    public static List<Fact> factList = new ArrayList<>();

    public static void initializeFactList() {
        factList = new ArrayList<>();
    }

    public static void addFact(Fact fact) {
        factList.add(fact);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact))
            for (Fact f : factList)
                if (Objects.equals(f.getKey(), fact)) {
                    factList.remove(f);
                    return;
                }
    }

    public static void removeFact(int index) {
        if (index < factList.size())
            factList.remove(index);
    }

    public static boolean isAFact(String varName) {
        return factList.stream().anyMatch(e -> Objects.equals(e.getKey(), varName));
    }

    public static Fact getFact(String fact) {
        for (Fact f : factList)
            if (f.getKey().equals(fact))
                return f;

        return null;
    }

}
