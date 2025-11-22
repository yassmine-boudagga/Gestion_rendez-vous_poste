package test;

import Basedonner.BaseProger;
import jproject.Client;
import jproject.Personne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginInterface() {

        setTitle("Connexion");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel(new ImageIcon("images.png"));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(logoLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(0, 51, 102));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255,255,255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102)));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 51, 102)));
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);

        loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(255, 191, 0)); // Jaune moutarde
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        registerButton = new JButton("S'inscrire");
        registerButton.setBackground(new Color(255, 191, 0));
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationInterface();
            }
        });

        add(mainPanel);
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.length()==0 || password.length()==0) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Personne p=new Personne();
        if (!p.validateEmail(email)) {
            JOptionPane.showMessageDialog(this, "L'email n'est pas valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.length()==0){
            JOptionPane.showMessageDialog(this, "tu doit écrire le mot pass.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        BaseProger b=new BaseProger("rejeb");
        if (!b.existe_mail(email)) {
            JOptionPane.showMessageDialog(this, "email nexiste pas dans notre base.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
            b.deconnection();
            return;
        }
        if (!(b.getMotPass(email).equals(password))){
            JOptionPane.showMessageDialog(this, "pass word incorrect.", "password", JOptionPane.WARNING_MESSAGE);
            b.deconnection();
            return;
        }
        Client c=b.getClient(email,password);
        b.deconnection();
        JOptionPane.showMessageDialog(this, "Connexion réussie en tant que client !");
        dispose();
        SwingUtilities.invokeLater(() -> new LesPostesInterface(c));
    }

    private void openRegistrationInterface() {
        dispose();
        SwingUtilities.invokeLater(() -> new InterfaceInscription().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginInterface().setVisible(true));
    }
}