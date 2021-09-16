package fr.univangers.master1.hogggrenon.utils;

import fr.univangers.master1.hogggrenon.Rule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    /**
     * Récupérer les données/faits d'un fichier CSV
     * @param fileName Nom du fichier
     * @return les données du fichier
     * @throws FileNotFoundException
     */
    public static List<String> getFactsFromCSV(String fileName) throws FileNotFoundException {
        // TODO : Si le fichier est inexistant, ne pas stopper le programme
        List<String> data = new ArrayList<>();

        Scanner sc = new Scanner(new File(fileName));
        sc.useDelimiter(";");

        while (sc.hasNext()) {
            data.add(sc.next());
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
