package test;
import Basedonner.BaseProger;
import jproject.Client;
import jproject.Poste;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class GuichetInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField heureField;
    private JLabel dateLabel, dernierTicketLabel, clientsEnAttenteLabel;
    private JButton verificationButton;
    private String date=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    public GuichetInterface(Poste p, Client c) {
        // Configuration de la fenêtre principale
        setTitle("Interface Guichet");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 51, 102));
        mainPanel.setLayout(new GridBagLayout());

        dateLabel = new JLabel("Date : " + date);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setForeground(Color.WHITE);

        JLabel heureLabel = new JLabel("Heure d'arrivée (HH:MM) :");
        heureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        heureLabel.setForeground(Color.WHITE);

        heureField = new JTextField(10);

        dernierTicketLabel = new JLabel("Dernier ticket traité : ");
        dernierTicketLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dernierTicketLabel.setForeground(Color.WHITE);

        clientsEnAttenteLabel = new JLabel("Nombre de clients en attente : ");
        clientsEnAttenteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        clientsEnAttenteLabel.setForeground(Color.WHITE);

        verificationButton = new JButton("Vérification");
        customizeButton(verificationButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(dateLabel, gbc);

        gbc.gridy++;
        mainPanel.add(heureLabel, gbc);

        gbc.gridy++;
        mainPanel.add(heureField, gbc);

        gbc.gridy++;
        mainPanel.add(dernierTicketLabel, gbc);

        gbc.gridy++;
        mainPanel.add(clientsEnAttenteLabel, gbc);

        gbc.gridy++;
        mainPanel.add(verificationButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        verificationButton.addActionListener(e -> {
            String heure = heureField.getText();
            String b=this.date+" "+heure;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            Date da=new Date();
            try {
                da = formatter.parse(b);
                c.setDateTime(da);
            } catch (ParseException ea) {
                System.out.println("Format de date invalide : " + ea.getMessage());
            }
            if (da.before(new Date())) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une heure valide.");
            } else if (p.verifierHeureValide(heure)) {
                dispose();
                new ConfirmationGuichetInterface(p,c,da).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une heure valide (entre 08:00-12:00 ou 15:00-17:00).");
            }
        });


        mettreAJourInformations(p);
    }
    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(new Color(255, 191, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    private void mettreAJourInformations(Poste p) {
        BaseProger b=new BaseProger("rejeb");
        String [][] t=b.tableaffichage("tiker");
        p.setTik(b.tikerReserver(p));
        Collections.sort(p.getTik());
        b.deconnection();
        String c;
        if (p.getTik().size() ==0) {
            c="aucun ticket traité";
        }
        else{
            c=String.valueOf(p.getTik().get(0));
        }
        /*String dernierTicket=new String(),clientsEnAttente=new String();

                p.setDerticket(Integer.parseInt(p));
                p.setNombre_client_en_post(p.getDerticket()- Integer.parseInt(t[i][1]));
                clientsEnAttente=String.valueOf(p.getNombre_client_en_post());
        */
        // Mise à jour des labels
        dernierTicketLabel.setText("Dernier ticket a traiter : " + c);
        clientsEnAttenteLabel.setText("clients en attente : " + p.getTik().size());
    }
    public static void main(String[] args) {
        Client c=new Client("14077777","zoubeir@gmail.com","zoubeir","guetari","rejeb","23666666");
        Poste p=new Poste(5050,"LAMTA","doffanet",4);
        SwingUtilities.invokeLater(() -> new GuichetInterface(p,c).setVisible(true));
    }
}