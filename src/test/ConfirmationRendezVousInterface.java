package test;

import javax.swing.*;
import java.awt.*;

public class ConfirmationRendezVousInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private String selectedDate;

    public ConfirmationRendezVousInterface(String selectedDate) {
        this.selectedDate = selectedDate;

        // Configuration de la fenêtre
        setTitle("Confirmation de Rendez-vous");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau supérieur pour le titre
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 191, 0));
        JLabel titleLabel = new JLabel("Confirmez votre rendez-vous");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(10, 36, 99));
        topPanel.add(titleLabel);

        // Panneau central avec les informations et boutons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0, 51, 102));

        // Ajouter un grand espacement avant le message
        centerPanel.add(Box.createVerticalStrut(40));

        JLabel messageLabel = new JLabel("Rendez-vous pour le : " + selectedDate);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(0, 51, 102));
        centerPanel.add(messageLabel);

        // Ajouter un espacement plus grand entre le message et les boutons
        centerPanel.add(Box.createVerticalStrut(40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 51, 102));

        JButton confirmButton = new JButton("Confirmer");
        JButton cancelButton = new JButton("Annuler");

        customizeButton(confirmButton);
        customizeButton(cancelButton);

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        centerPanel.add(buttonPanel);

        centerPanel.add(Box.createVerticalStrut(40));

        // Ajouter le panel central à la fenêtre
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Actions des boutons
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Rendez-vous confirmé pour le " + selectedDate);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Rendez-vous annulé.");
            dispose();
        });
        initComponents();
    }
    private void initComponents() {
        JLabel label = new JLabel("Votre rendez-vous est confirmé pour le : " + selectedDate);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(new Color(0, 51, 102));
        add(label);
    }

    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(140, 40));
        button.setBackground(new Color(255, 191, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmationRendezVousInterface("2024-02-02").setVisible(true));
    }
}