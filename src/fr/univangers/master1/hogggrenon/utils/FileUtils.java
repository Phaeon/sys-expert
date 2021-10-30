package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.*;

import java.io.*;
import java.util.*;

public class FileUtils {

    /**
     * Récupérer les données/faits d'un fichier CSV
     * @param fileName Nom du fichier
     * @return les données du fichier
     * @throws FileNotFoundException
     */
    public static List<Fact> getFactsFromCSV(String fileName) throws IOException {
        // TODO : Si le fichier est inexistant, ne pas stopper le programme
        List<Fact> data = new ArrayList<>();
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null)
        {
            String[] fact = line.split(";");

            if (fact.length > 3)
                throw new IOException("Il y a trop de champs dans votre fichier.");

            if (fact[0].equalsIgnoreCase("v")) {
                if (Parser.isAValidVariable(fact[1])) {
                    if (Parser.isNumeric(fact[2]))
                        data.add(new FactWithVar(fact[1], Float.parseFloat(fact[2])));
                    else if (Parser.isBoolean(fact[2]))
                        data.add(new FactWithVar(fact[1], Boolean.parseBoolean(fact[2])));
                    else if (Parser.isString(fact[2]))
                        data.add(new FactWithVar(fact[1], fact[2]));
                }
            } else if (fact[0].equalsIgnoreCase("p")) {
                if (fact[2].equalsIgnoreCase("true")
                        || fact[2].equalsIgnoreCase("false"))
                    data.add(new FactWithPremise(fact[1], Boolean.parseBoolean(fact[2])));
            } else
                throw new IOException("Vous devez avoir des variables ou des prémisses seulement.");
        }

        return data;
    }


    /**
     * Récupérer les données/règles d'un fichier CSV
     * @param fileName Nom du fichier
     * @return les données du fichier
     * @throws FileNotFoundException
     */
    public static List<Rule> getRulesFromCSV(String fileName) throws IOException {
        // TODO : Si le fichier est inexistant, ne pas stopper le programme
        List<Rule> data = new ArrayList<>();
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null)
        {
            String[] rule = line.split(";");

            if (rule.length > 2)
                throw new IOException("Il y a trop de champs dans votre fichier.");

            Parser P = new Parser(rule[0]);

            if (!P.validPostfix(P.infixToPostfix())
                    || !FactListUtils.isAFact(rule[1]))
                continue;

            Rule R = new Rule(rule[0], rule[1]);

            if (!data.contains(R))
                data.add(R);
        }

        return data;
    }

}
