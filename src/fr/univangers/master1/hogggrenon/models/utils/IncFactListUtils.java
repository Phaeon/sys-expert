package fr.univangers.master1.hogggrenon.models.utils;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.Rule;
import fr.univangers.master1.hogggrenon.models.facts.FactWithPremise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IncFactListUtils {

    public static List<Fact> incFactList = new ArrayList<>();

    public static void initializeFactList() {
        incFactList = new ArrayList<>();
        incFactList.add(new FactWithPremise("INCOHERENT", true));
    }

    public static void addFact(Fact fact) {
        incFactList.add(fact);
    }

    public static void removeFact(String fact) {
        if (isAnIncFact(fact))
            for (Fact f : incFactList)
                if (Objects.equals(f.getKey(), fact)) {
                    incFactList.remove(f);
                    return;
                }
    }

    public static void removeFact(int index) {
        if (index < incFactList.size())
            incFactList.remove(index);
    }

    /**
     * Retourne true si le fait est un fait incohérent
     * @param varName Nom de la variable / prémisse
     * @return l'appartenance du fait à la base de faits incohérents
     */
    public static boolean isAnIncFact(String varName) {
        return incFactList.stream().anyMatch(e -> Objects.equals(e.getKey(), varName));
    }

    public static Fact getFact(String fact) {
        for (Fact f : incFactList)
            if (f.getKey().equals(fact))
                return f;

        return null;
    }

    public static boolean hasIncoherentRule(List<Rule> rules) {
        // Vérifier pour chaque règle si la tête ou la conclusion contient des faits incohérents
        for (Rule R : rules) {
            // Récupérer les faits de la tête
            Parser P = new Parser(R.getHead());
            for (String fact : P.getFactsInExpression(P.infixToPostfix()))
                if (IncFactListUtils.isAnIncFact(fact))
                    return true;

            // Vérifier le fait conclusion
            if (IncFactListUtils.isAnIncFact(R.getBody()))
                return true;
        }

        return false;
    }

}
