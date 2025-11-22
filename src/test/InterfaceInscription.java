package test;
import Basedonner.Base;
import Basedonner.BaseProger;
import jproject.Client;
import jproject.Personne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

public class InterfaceInscription extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField cinField, nomField, prenomField, emailField, telephoneField;
    private JPasswordField passwordField;
    public InterfaceInscription() {
        setTitle("Inscription");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 51, 102)); // Bleu marine
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre
        JLabel titleLabel = new JLabel("Inscription Client");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel cinLabel = new JLabel("CIN :");
        styleLabel(cinLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(cinLabel, gbc);

        cinField = new JTextField(15);
        styleTextField(cinField);
        gbc.gridx = 1;
        mainPanel.add(cinField, gbc);

        JLabel nomLabel = new JLabel("Nom :");
        styleLabel(nomLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(nomLabel, gbc);

        nomField = new JTextField(15);
        styleTextField(nomField);
        gbc.gridx = 1;
        mainPanel.add(nomField, gbc);

        JLabel prenomLabel = new JLabel("Prénom :");
        styleLabel(prenomLabel);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(prenomLabel, gbc);

        prenomField = new JTextField(15);
        styleTextField(prenomField);
        gbc.gridx = 1;
        mainPanel.add(prenomField, gbc);

        JLabel emailLabel = new JLabel("Email :");
        styleLabel(emailLabel);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        styleTextField(emailField);
        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);
        JLabel telephoneLabel = new JLabel("Téléphone :");
        styleLabel(telephoneLabel);
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(telephoneLabel, gbc);

        telephoneField = new JTextField(15);
        styleTextField(telephoneField);
        gbc.gridx = 1;
        mainPanel.add(telephoneField, gbc);
        JLabel passwordLabel = new JLabel("Mot de passe :");
        styleLabel(passwordLabel);
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        styleTextField(passwordField);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Bouton s'inscrire
        JButton inscrireButton = new JButton("S'inscrire");
        inscrireButton.setBackground(new Color(255, 191, 0)); // Jaune moutarde
        inscrireButton.setForeground(Color.BLACK);
        inscrireButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(inscrireButton, gbc);

        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrire();
            }
        });
        add(mainPanel);
        setVisible(true);
    }
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
    }
    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createLineBorder(new Color(255, 191, 0), 2)); // Jaune moutarde
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    private void inscrire() {
        Client c=new Client();
        String cin = cinField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String motDePasse = new String(passwordField.getPassword());
        if (!c.verifier(cin)) {
            JOptionPane.showMessageDialog(this, "Le CIN doit contenir exactement 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!validateName(nom)){
            JOptionPane.showMessageDialog(this, "Le nom n\'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!validateName(prenom)){
            JOptionPane.showMessageDialog(this, "Le prenom n\'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!c.verfiernum(telephone)) {
            JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit étre tunisien .", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Personne p=new Personne();
        if (!p.validateEmail(email)) {
            JOptionPane.showMessageDialog(this, "L'email n'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println(motDePasse);
        if (motDePasse.length()==0){
            JOptionPane.showMessageDialog(this, "tu doit écrire le mot pass.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BaseProger b=new BaseProger("rejeb");
        if (b.existe_cin(cin)){
            JOptionPane.showMessageDialog(this, "tu as deja un compte\nverifier cin.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (b.existe_mail(email)){
            JOptionPane.showMessageDialog(this, "tu as deja un compte ", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        b.deconnection();
        if (storeInDatabase(cin, nom, prenom, email, telephone, motDePasse)) {
            JOptionPane.showMessageDialog(this, "Inscription réussie !");
            this.dispose();
            new LesPostesInterface(c);
        }

    }
    public boolean validateName(String name) {
        // Vérifie si le nom est valide selon les critères
        return name != null && name.matches("[A-Za-z]+( [A-Za-z]+)*");
    }
    private boolean storeInDatabase(String cin, String nom, String prenom, String email, String telephone, String motDePasse) {
            Client c=new Client(cin,email,motDePasse,nom,prenom,telephone);
            BaseProger b=new BaseProger("rejeb");
            boolean v= b.ajoutClient(c);
            b.deconnection();
            return v;
    }

    public static void main(String[] args) {
        new InterfaceInscription () ;
    }
}