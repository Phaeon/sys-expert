package fr.univangers.master1.hogggrenon.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactBase {

    public static Map<String, Boolean> baseDeFaits;

    public static void initializeFactBase() {
        baseDeFaits = new HashMap<>();
    }

    public static void addFact(String fact, boolean value) {
        if (!isAFact(fact)) getFactBase().put(fact, value);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact)) getFactBase().remove(fact);
    }

    public static boolean isAFact(String fact) {
        return getFactBase().containsKey(fact);
    }

    public static Map<String, Boolean> getFactBase() {
        return baseDeFaits;
    }

}
