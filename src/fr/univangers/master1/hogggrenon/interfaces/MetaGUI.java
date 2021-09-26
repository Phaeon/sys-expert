package fr.univangers.master1.hogggrenon.interfaces;

import fr.univangers.master1.hogggrenon.utils.MetaUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MetaGUI extends JFrame {

    public MetaGUI() {

        Map<String, MetaUtils.MetaRule> meta = new HashMap<>();

        meta.put("Règles dans l'ordre croissant", MetaUtils.MetaRule.ASCENDANT);
        meta.put("Règles dans l'ordre décroissant", MetaUtils.MetaRule.DESCENDANT);
        meta.put("Règles les plus spécifiques", MetaUtils.MetaRule.MOST_SPECIFIC);
        meta.put("Faits les plus récents", MetaUtils.MetaRule.MOST_RECENT);

        JPanel framePanel = new JPanel();
        framePanel.setLayout(new GridBagLayout());

        GridBagConstraints global = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Méta-règles", JLabel.CENTER),
            metaAvailableLabel = new JLabel("Métà-règles disponibles", JLabel.CENTER),
            listOfMetaLabel = new JLabel("Métà-règles choisies", JLabel.CENTER),
            description = new JLabel("Choisissez les méta-règles que vous souhaitez implémenter, dans l'ordre.");

        JButton addButton = new JButton("Ajouter \u2714"),
                removeButton = new JButton("Retirer \u2716"),
                terminate = new JButton("Sauvegarder");

        DefaultListModel<String> defA = new DefaultListModel<>(), defB = new DefaultListModel<>();
        JList<String> listAvailable = new JList<>(defA),
                listChosen = new JList<>(defB);

        defA.addAll(meta.keySet());
        for (String rule : meta.keySet())
            if (MetaUtils.isAMetaRule(meta.get(rule)))
                defB.addElement(rule);

        JScrollPane scrollLeft = new JScrollPane(listAvailable),
                scrollRight = new JScrollPane(listChosen);

        scrollLeft.setPreferredSize(new Dimension(270, 150));
        scrollLeft.setMinimumSize(new Dimension(270, 150));
        scrollLeft.setMaximumSize(new Dimension(270, 150));

        scrollRight.setPreferredSize(new Dimension(270, 150));
        scrollRight.setMinimumSize(new Dimension(270, 150));
        scrollRight.setMaximumSize(new Dimension(270, 150));

        {
            global.gridx = 0;
            global.gridy = 0;
            global.gridwidth = 4;
            global.insets = new Insets(10, 10, 20, 10);

            framePanel.add(titleLabel, global);

            global.gridy = 1;
            global.gridwidth = 2;
            global.insets.top = 5;

            framePanel.add(metaAvailableLabel, global);

            global.gridx = 2;

            framePanel.add(listOfMetaLabel, global);

            global.gridx = 0;
            global.gridy = 2;

            framePanel.add(scrollLeft, global);

            global.gridx = 2;

            framePanel.add(scrollRight, global);

            global.gridx = 0;
            global.gridy = 3;

            framePanel.add(addButton, global);

            global.gridx = 2;

            framePanel.add(removeButton, global);

            global.gridwidth = 4;
            global.gridx = 0;
            global.gridy = 4;
            global.insets.bottom = 20;

            framePanel.add(description, global);

            global.gridy = 5;

            framePanel.add(terminate, global);
        }

        // Listeners
        {
            addButton.addActionListener(e -> {
                String value = listAvailable.getSelectedValue();

                if (!defB.contains(value))
                {
                    MetaUtils.addNewMetaRule(meta.get(value));
                    defB.addElement(value);
                }
            });

            removeButton.addActionListener(e -> {
                String value = listChosen.getSelectedValue();

                if (defB.contains(value))
                {
                    MetaUtils.removeMetaRule(meta.get(value));
                    defB.removeElement(value);
                }
            });

            terminate.addActionListener(e -> this.setVisible(false));
        }



        this.setTitle("Système expert - Méta-Règles");
        this.add(framePanel);
        this.pack();
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}
