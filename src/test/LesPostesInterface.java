package test;
import Basedonner.BaseProger;
import jproject.Client;
import jproject.Poste;

import javax.swing.*;
import java.awt.*;
public class LesPostesInterface {
    public LesPostesInterface(Client c) {
        openSelectionDialog(c);
    }
    private void openSelectionDialog(Client c) {
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridBagLayout());
        dialogPanel.setPreferredSize(new Dimension(500, 550));
        dialogPanel.setBackground(new Color(0, 51, 102));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Choisir une poste :", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        dialogPanel.add(label, gbc);

        BaseProger b=new BaseProger("rejeb");
        Poste[]postes=b.getPoste();
        b.deconnection();
        String[]Tnom=new String[postes.length];
        for (int i = 0; i < postes.length; i++) {
            Tnom[i]=postes[i].getNom();
        }
        JComboBox<String> postesComboBox = new JComboBox<>(Tnom);
        postesComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        postesComboBox.setBackground(Color.WHITE);
        postesComboBox.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        dialogPanel.add(postesComboBox, gbc);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(255, 191, 0));
        okButton.setForeground(Color.BLACK);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(255, 191, 0));
        cancelButton.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        dialogPanel.add(okButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        dialogPanel.add(cancelButton, gbc);

        JDialog dialog = new JDialog();
        dialog.setTitle("Liste des Postes");
        dialog.setModal(true);
        dialog.getContentPane().add(dialogPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        okButton.addActionListener(e -> {
            String selectedPoste = (String) postesComboBox.getSelectedItem();
            Poste s=new Poste(12,"d","ee",1);
            for (int i = 0; i < postes.length; i++) {
                if (postes[i].getNom().equals(selectedPoste)){
                    s=postes[i];
                    break;
                }
            }
            dialog.dispose();
            new ServicesPoste(s,c).setVisible(true);
        });
        cancelButton.addActionListener(e -> {
            dialog.dispose();
            System.exit(0);
        });
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        Client a=new Client();
        new LesPostesInterface(a);
    }
}
