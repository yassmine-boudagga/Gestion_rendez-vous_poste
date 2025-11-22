package test;

import Basedonner.BaseProger;
import jproject.Client;
import jproject.Poste;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConfirmationGuichetInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel messageLabel;
    private JButton confirmerButton, annulerButton;

    public ConfirmationGuichetInterface(Poste p, Client c, Date d) {
        // Configuration de la fenêtre principale
        setTitle("Confirmation Guichet");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0, 51, 102));
        mainPanel.setLayout(new GridBagLayout());

        // Récupérer le numéro de ticket depuis la base de données
        int numTicket = getNumTicketFromClient(p,d);

        // Affichage des informations
        int def=d.getHours()-8;
        int tour=def*60+d.getMinutes();
        int numt=tour/2;
        numt=p.selectTik(numt);
        tour=p.selecttemp(numt=tour/2,tour);
        messageLabel = new JLabel("<html><center>Votre heure d'arrivée : <b>" + (tour/60+8)+":"+tour%60 + "</b><br>Votre numéro de ticket est : <b>" + numTicket + "</b></center></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.WHITE);

        confirmerButton = new JButton("Confirmer");
        annulerButton = new JButton("Annuler");

        customizeButton(confirmerButton);
        customizeButton(annulerButton);

        // Ajout des composants au panneau principal
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(messageLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(confirmerButton, gbc);

        gbc.gridx++;
        mainPanel.add(annulerButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Écouteur de bouton
        confirmerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Votre demande est confirmée.");
            enregistre(numTicket,p,c,d);
            dispose();
        });

        annulerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Votre demande a été annulée.");
            dispose();
            new GuichetInterface(p,c).setVisible(true);
        });
    }

    // Méthode pour récupérer le numéro de ticket d'un client
    public int getNumTicketFromClient(Poste p,Date d) {
        BaseProger b = new BaseProger("rejeb");
        b.suprimertiket();
        p.setTik(b.tikerReserver(p));
        System.out.println("tikeeeer "+p.getTik().size());
        b.deconnection();
        Date now=new Date();

        int def=d.getHours()-8;
        int tour=def*60+d.getMinutes();
        int numt=tour/2;
        numt=p.selectTik(numt);
        tour=p.selecttemp(numt,tour);
        System.out.println("num t"+numt);
        return numt;
        /*System.out.println("tour="+tour);
        int tik;
        if(tour>p.getDerticket()){
            tik=(p.getDerticket()-p.getNombre_client_en_post())+tour;
            System.out.println("p.getDerticket()-p.getNombre_client_en_post())+tour"+p.getDerticket()+"-"+p.getNombre_client_en_post()+"+"+tour);
            System.out.println("tik="+tik);
            tik=p.selectTik(tik);
            System.out.println("tik="+tik);
            return tik;
        }
        else {
            tik=p.getDerticket()+1;
            tik=p.selectTik(tik);
            return tik;
        }*/
    }
    public void enregistre(int tik,Poste p,Client c,Date d){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(d);
        String[]colonecondition={"id","cin"};
        String[]valeurcondition={String.valueOf(p.getId()),c.getCin()};
        String[]opp={"=","="};
        BaseProger b=new BaseProger("rejeb");
        if(b.nbr_ligne("reserver_tiker",colonecondition,valeurcondition,opp)==1){
            b.delete("reserver_tiker",colonecondition,valeurcondition,opp);
        }
        String[]val={String.valueOf(p.getId()),c.getCin(),String.valueOf(tik),dateString};
        b.insert("reserver_tiker",val);
        b.deconnection();
    }
    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 30));
        button.setBackground(new Color(255, 191, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        // Exemple d'appel en passant un identifiant client (cinClient)
        Client c=new Client("14077777","zoubeir@gmail.com","zoubeir","guetari","rejeb","23666666");
        Poste p=new Poste(5035,"sayada","doffanet",4);
        String dateString = "2024-12-19 9:59";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = formatter.parse(dateString);
            System.out.println("Date convertie : " + date);
            SwingUtilities.invokeLater(() -> new ConfirmationGuichetInterface(p,c,date).setVisible(true));
        } catch (ParseException e) {
            System.out.println("Format de date invalide : " + e.getMessage());
        }
    }
}