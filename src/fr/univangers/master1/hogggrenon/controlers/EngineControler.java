package fr.univangers.master1.hogggrenon.controlers;

import fr.univangers.master1.hogggrenon.Parser;
import fr.univangers.master1.hogggrenon.models.Fact;
import fr.univangers.master1.hogggrenon.models.Rule;
import fr.univangers.master1.hogggrenon.models.utils.*;
import fr.univangers.master1.hogggrenon.views.HomeGUI;
import fr.univangers.master1.hogggrenon.views.InformationBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class EngineControler {

    public EngineControler() {
        FactListUtils.initializeFactList(); // Initialisation de la liste de faits
        FactBase.initializeFactList(); // Initialisation de la base de faits
        RuleList.initializeRuleBase(); // Initialisation de la base de règles
        MetaUtils.initializeMetaRules(); // Initialisation des méta-règles
        IncFactListUtils.initializeFactList(); // Initialisation de la base de faits incohérents

        new HomeGUI(this);
    }

    public void removeRule(DefaultListModel<String> data, int index) {
        RuleList.removeRule(index);
        data.remove(index);
    }

    public void removeFact(DefaultListModel<String> data, int index) {
        if (FactListUtils.factList.get(index).getKey().equals("INCOHERENT")) {
            new InformationBox(InformationBox.BoxType.ERROR, "Faits", "\nCette incohérence par défaut ne peut être effacée des faits");
            return;
        }

        FactListUtils.removeFact(index);
        data.remove(index);

        Iterator<Rule> iterator = RuleList.getRuleBase().iterator();

        while(iterator.hasNext())
        {
            Rule r = iterator.next();
            Parser P = new Parser(r.getHead());

            // Si un fait a été retiré de la règle, la règle ne sera plus valide
            // (La variable ne sera pas reconnue lors du parsing)
            if (!P.validPostfix(P.infixToPostfix())
                    || !FactListUtils.isAFact(r.getBody())) {
                iterator.remove();
            }
        }
    }

    public void removeIncFact(DefaultListModel<String> data, int index) {
        if (!IncFactListUtils.incFactList.get(index).getKey().equals("INCOHERENT")) {
            IncFactListUtils.removeFact(index);
            data.remove(index);
        } else
            new InformationBox(InformationBox.BoxType.ERROR, "Incohérences", "\nCette incohérence par défaut ne peut être effacée");
    }


    public void getFactFileData(DefaultListModel<String> data) throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            if (!selectedFile.getName().endsWith(".csv") && !selectedFile.getName().endsWith(".txt"))
            {
                new InformationBox(InformationBox.BoxType.ERROR, "Fichiers", "\nVotre fichier doit être de format CSV ou TXT.");
                return;
            }

            List<Fact> facts = FileUtils.getFactsFromCSV(selectedFile.getAbsolutePath());

            for (Fact f : facts) {
                if (!data.contains(f.getKey() + " -> " + f.getValue())) {
                    FactListUtils.addFact(f);
                    data.addElement(f.getKey() + " -> " + f.getValue());
                }
            }
        }
    }

    public void getRuleFileData(DefaultListModel<String> data) throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            if (!selectedFile.getName().endsWith(".csv") && !selectedFile.getName().endsWith(".txt"))
            {
                new InformationBox(InformationBox.BoxType.ERROR, "Fichiers", "\nVotre fichier doit être de format CSV ou TXT.");
                return;
            }

            List<Rule> rules = FileUtils.getRulesFromCSV(selectedFile.getAbsolutePath());

            for (Rule r : rules) {
                if (!data.contains(r.getHead() + " -> " + r.getBody())
                        && r.checkRule()) {
                    RuleList.addRule(r.getHead(), r.getBody());
                    data.addElement(r.getHead() + " -> " + r.getBody());
                }
            }
        }
    }
}
