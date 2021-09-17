package fr.univangers.master1.hogggrenon;

import fr.univangers.master1.hogggrenon.interfaces.HomeGUI;
import fr.univangers.master1.hogggrenon.interfaces.NewBaseGUI;
import fr.univangers.master1.hogggrenon.utils.FactBase;
import fr.univangers.master1.hogggrenon.utils.FileUtils;
import fr.univangers.master1.hogggrenon.utils.RuleBase;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // Initialiser les bases de faits et de règles
        FactBase.initializeFactBase();
        RuleBase.initializeRuleBase();

        new HomeGUI();

        /*JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            // List<String> facts = FileUtils.getFactsFromCSV(selectedFile.getAbsolutePath());
            List<Rule> rules = FileUtils.getRulesFromCSV(selectedFile.getAbsolutePath());

            for (Rule rule : rules) {
                System.out.print("IF : ");
                for (String s : rule.getHead())
                    System.out.print(s + " ");

                System.out.println("");

                System.out.print("THEN : ");
                for (String s : rule.getBody())
                    System.out.print(s + " ");

                System.out.println("");
            }
        }*/
    }
}
