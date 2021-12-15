package fr.univangers.master1.hogggrenon.models.utils;

import fr.univangers.master1.hogggrenon.*;
import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.facts.FactWithPremise;
import fr.univangers.master1.hogggrenon.models.facts.FactWithVar;
import fr.univangers.master1.hogggrenon.models.Rule;
import fr.univangers.master1.hogggrenon.views.InformationBox;

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
        List<Fact> data = new ArrayList<>();
        String line;
        StringBuilder warning_output = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        boolean warning_existence = false;

        int index = 0;
        while ((line = br.readLine()) != null)
        {
            String[] fact = line.split(";");
            index++;

            if (line.equals(""))
                continue;

            if (fact.length != 3) {
                warning_existence = true;
                warning_output.append("\n\t- Il vous faut 3 champs à la ligne ").append(index);
            } else if (FactListUtils.isAFact(fact[1]))
            {
                warning_existence = true;
                warning_output.append("\n\t- Le fait ").append(fact[1]).append(" est déjà enregistré");
            }
            else {
                if (fact[0].equalsIgnoreCase("v")) {
                    if (Parser.isAValidVariable(fact[1])) {
                        if (Parser.isNumeric(fact[2]))
                            data.add(new FactWithVar(fact[1], Float.parseFloat(fact[2])));
                        else if (Parser.isBoolean(fact[2]))
                            data.add(new FactWithVar(fact[1], Boolean.parseBoolean(fact[2])));
                        else if (Parser.isString(fact[2]))
                            data.add(new FactWithVar(fact[1], fact[2]));
                        else
                        {
                            warning_existence = true;
                            warning_output.append("\n\t- La valeur ").append(fact[2]).append(", pour la variable ").append(fact[1]).append(", n'est pas valide");
                        }
                    } else {
                        warning_existence = true;
                        warning_output.append("\n\t- La variable ").append(fact[1]).append(" n'est pas valide");
                    }
                } else if (fact[0].equalsIgnoreCase("p")) {
                    if (fact[2].equalsIgnoreCase("true")
                            || fact[2].equalsIgnoreCase("false"))
                        data.add(new FactWithPremise(fact[1], Boolean.parseBoolean(fact[2])));
                    else {
                        warning_existence = true;
                        warning_output.append("\n\t- La prémisse ").append(fact[1]).append(" n'a pas de valeur booléenne");
                    }
                } else {
                    warning_existence = true;
                    warning_output.append("\n\t- La ligne ").append(index).append(" est mal formatée");
                }
            }

        }

        if (warning_existence)
            new InformationBox(InformationBox.BoxType.WARNING, "Fichier Faits", warning_output.toString());

        return data;
    }


    /**
     * Récupérer les données/règles d'un fichier CSV
     * @param fileName Nom du fichier
     * @return les données du fichier
     * @throws FileNotFoundException
     */
    public static List<Rule> getRulesFromCSV(String fileName) throws IOException {
        List<Rule> data = new ArrayList<>();
        String line;

        StringBuilder warning_output = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        boolean warning_existence = false;

        int index = 0;
        while ((line = br.readLine()) != null)
        {
            String[] rule = line.split(";");
            index++;

            if (line.equals(""))
                continue;

            if (rule.length != 2) {
                warning_existence = true;
                warning_output.append("\n\t- Il vous faut 2 champs à la ligne ").append(index);
                continue;
            }

            Parser P = new Parser(rule[0]);

            boolean local_warn = false;

            if (!FactListUtils.isAFact(rule[1])) {
                warning_existence = true;
                local_warn = true;
                warning_output.append("\n\t- La conclusion de la règle ").append(index).append(" n'est pas un fait valide");
            }

            if (P.getFactsInExpression(P.infixToPostfix()).contains(rule[1])) {
                warning_existence = true;
                local_warn = true;
                warning_output.append("\n\t- La règle ").append(index).append(" contient des faits invalides");
            }

            if (!P.validPostfix(P.infixToPostfix())) {
                warning_existence = true;
                local_warn = true;
                warning_output.append("\n\t- La règle ").append(index).append(" est mal formatée");
            }

            if (RuleList.isARule(new Rule(rule[0], rule[1]))) {
                warning_existence = true;
                local_warn = true;
                warning_output.append("\n\t- La règle ").append(index).append(" existe déjà");
            }

            if (!local_warn) {
                Rule R = new Rule(rule[0], rule[1]);

                if (!data.contains(R))
                    data.add(R);
            }

        }

        if (warning_existence)
            new InformationBox(InformationBox.BoxType.WARNING, "Fichier Règles", warning_output.toString());


        return data;
    }

}
