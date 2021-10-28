package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.Rule;

import java.io.*;
import java.util.*;

public class FileUtils {

    /**
     * Récupérer les données/faits d'un fichier CSV
     * @param fileName Nom du fichier
     * @return les données du fichier
     * @throws FileNotFoundException
     */
    public static Map<String, Object> getFactsFromCSV(String fileName) throws IOException {
        // TODO : Si le fichier est inexistant, ne pas stopper le programme
        Map<String, Object> data = new HashMap<>();
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null)
        {
            String[] fact = line.split(";");

            if (fact.length > 2) throw new IOException("Il y a trop de champs dans votre fichier.");

            if (Parser.isAValidVariable(fact[0])) {
                if (Parser.isNumeric(fact[1]))
                    data.put(fact[0], Float.parseFloat(fact[1]));
                else if (Parser.isBoolean(fact[1]))
                    data.put(fact[0], Boolean.parseBoolean(fact[1]));
                else if (Parser.isString(fact[1]))
                    data.put(fact[0], fact[1]);
            }
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
        String line = "", delim = ";";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null)
        {
            StringBuilder head = new StringBuilder();
            List<String> body = new ArrayList<>();
            String[] lineData = line.split(delim);

            if (lineData.length > 2) throw new IOException("Il y a trop de champs dans votre fichier.");

            Parser P = new Parser(lineData[0]);

            if (!P.validPostfix(P.infixToPostfix()))
                continue;

            head.append(lineData[0]);

            Scanner sc = new Scanner(lineData[1]);
            sc.useDelimiter(",");

            while (sc.hasNext())
                body.add(sc.next());

            data.add(new Rule(head.toString(), body));

        }

        return data;
    }

}
