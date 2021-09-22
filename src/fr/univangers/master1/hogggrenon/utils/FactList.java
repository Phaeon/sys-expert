package fr.univangers.master1.hogggrenon.utils;

import java.util.HashMap;
import java.util.Map;

public class FactList {

    public static Map<String, Boolean> baseDeFaits;

    public static void initializeFactList() {
        baseDeFaits = new HashMap<>();
    }

    public static void addFact(String fact, boolean value) {
        if (!isAFact(fact)) getFactList().put(fact, value);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact)) getFactList().remove(fact);
    }

    public static boolean isAFact(String fact) {
        return getFactList().containsKey(fact);
    }

    public static Map<String, Boolean> getFactList() {
        return baseDeFaits;
    }

}
