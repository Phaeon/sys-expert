package utils;

import java.util.ArrayList;
import java.util.List;

public class FactBase {

    public static List<String> baseDeFaits;

    public static void initializeFactBase() {
        baseDeFaits = new ArrayList<>();
    }

    public static void addFact(String fact) {
        if (!isAFact(fact)) baseDeFaits.add(fact);
    }

    public static void removeFact(String fact) {
        if (isAFact(fact)) baseDeFaits.remove(fact);
    }

    public static boolean isAFact(String fact) {
        return baseDeFaits.contains(fact);
    }

    public static List<String> getFactBase() {
        return baseDeFaits;
    }

}
