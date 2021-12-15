package fr.univangers.master1.hogggrenon.views;

import javax.swing.*;

public class InformationBox extends JOptionPane{

    public enum BoxType {
        INFO,
        WARNING,
        ERROR
    }

    public InformationBox(BoxType type, String title, String message) {

        switch (type) {
            case INFO:
                showMessageDialog(null, "Informations :\n\n" + message, title, INFORMATION_MESSAGE);
                break;
            case ERROR:
                showMessageDialog(null, "Erreurs repérées :\n" + message, title, ERROR_MESSAGE);
                break;
            case WARNING:
                showMessageDialog(null, "Failles repérées :\n" + message, title, WARNING_MESSAGE);
                break;
        }

    }

}
