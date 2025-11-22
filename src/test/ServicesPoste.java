package test;

import jproject.Client;
import jproject.Poste;

import javax.swing.*;
import java.awt.*;

public class ServicesPoste extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton rendezVousButton, guichetButton;

    public ServicesPoste(Poste selectedPoste, Client c) {
        setTitle("Services de la Poste - " + selectedPoste.getNom());
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 191, 0));
        JLabel titleLabel = new JLabel("Services disponibles au : " + selectedPoste.getNom());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(10, 36, 99));
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(0, 51, 102));

        rendezVousButton = new JButton("Prendre un rendez-vous");
        guichetButton = new JButton("Aller au guichet");

        customizeButton(rendezVousButton);
        customizeButton(guichetButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(rendezVousButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(guichetButton, gbc);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        rendezVousButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Redirection vers l'interface de rendez-vous...");
            dispose();
            new RendezVousInterface(selectedPoste,c).setVisible(true);
        });

        guichetButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Redirection vers l'interface guichet...");
            dispose();
            new GuichetInterface(selectedPoste,c).setVisible(true);
        });
    }

    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(240, 40));
        button.setBackground(new Color(255, 191, 0)); // Jaune moutarde
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        Poste p=new Poste(12,"sisirejeb","sayada",3);
        Client r=new Client();
        SwingUtilities.invokeLater(() -> new ServicesPoste(p,r).setVisible(true));
    }
}