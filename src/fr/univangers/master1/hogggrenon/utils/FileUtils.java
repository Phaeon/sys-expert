package fr.univangers.master1.hogggrenon.utils;

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
    public static Map<String, Boolean> getFactsFromCSV(String fileName) throws IOException {
        // TODO : Si le fichier est inexistant, ne pas stopper le programme
        Map<String, Boolean> data = new HashMap<>();
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while ((line = br.readLine()) != null)
        {
            String[] fact = line.split(";");

            if (fact.length > 2) throw new IOException("Il y a trop de champs dans votre fichier.");

            data.put(fact[0], Boolean.parseBoolean(fact[1]));
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
            List<String> head = new ArrayList<>(), body = new ArrayList<>();
            String[] lineData = line.split(delim);

            if (lineData.length > 2) throw new IOException("Il y a trop de champs dans votre fichier.");

            Scanner sc = new Scanner(lineData[0]);
            sc.useDelimiter(",");

            while (sc.hasNext())
                head.add(sc.next());

            sc = new Scanner(lineData[1]);
            sc.useDelimiter(",");

            while (sc.hasNext())
                body.add(sc.next());

            data.add(new Rule(head, body));

        }

        return data;
    }

}
