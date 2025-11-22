package test;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Basedonner.BaseProger;
import com.toedter.calendar.JDateChooser;
import jproject.Client;
import jproject.Poste;

public class RendezVousInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable reservedDatesTable;
    private JButton selectDateButton, checkDateButton;
    private JDateChooser dateChooser;
    public RendezVousInterface(Poste p,Client c) {

        setTitle("Prendre un Rendez-vous");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 51, 102));
        JLabel titleLabel = new JLabel("Dates réservées", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(255, 191, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);
        reservedDatesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(reservedDatesTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel dateLabel = new JLabel("Choisissez une date pour votre rendez-vous:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(dateLabel);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        centerPanel.add(dateChooser);
        checkDateButton = new JButton("Vérifier la disponibilité");
        centerPanel.add(checkDateButton);
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        checkDateButton.addActionListener(e -> checkDateAvailability(p,c));
        loadReservedDates(p);
    }

    // Charger les dates réservées dans la table
    private void loadReservedDates(Poste p) {
        BaseProger b=new BaseProger("rejeb");
        b.suprimerrendevous();
        String []T=b.getRendevousPost(p);
        b.deconnection();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Date Réservée"}, 0);
        for(int i=0;i<T.length;i++){
            model.addRow(new Object[]{T[i]});
        }
        reservedDatesTable.setModel(model);
    }
    private void checkDateAvailability(Poste p,Client c) {
        Date selectedDate = dateChooser.getDate();
        BaseProger b=new BaseProger("rejeb");
        p.setRendezvous(b.list_Redevou(p));
        if (selectedDate == null) {
            b.deconnection();
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date.");
            return;
        }
        if (selectedDate.before(new Date())) {
            b.deconnection();
            JOptionPane.showMessageDialog(this, "La date doit être supérieure à la date actuelle.");
            return;
        }
        if(!p.veriftemp(selectedDate)){
            b.deconnection();
            JOptionPane.showMessageDialog(this, "desolé cher(e) client(e) nous sommes ouvert entre 9 a 12 heure et de 14 heure a 17 heure.");
            return;
        }
        if(!p.verifdecalage(selectedDate)){
            b.deconnection();
            JOptionPane.showMessageDialog(this, "desolé cher(e) client(e) mais il faux qu'il y'a au moins un decalage 20 min entre les rendez-vous ");
            return;
        }
        String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(selectedDate);
        String[]colonecondition={"id","cin"};
        String[]valeurcondition={String.valueOf(p.getId()),c.getCin()};
        String[]opp={"=","="};
        if(b.nbr_ligne("redevou",colonecondition,valeurcondition,opp)==1){
            b.updaterendevou(p,c,formattedDate);
        }
        else {
            b.insertRendivou(c.getCin(), p.getId(), formattedDate);
        }
        b.deconnection();
        new ConfirmationRendezVousInterface(formattedDate).setVisible(true);
        dispose();
    }
    public static void main(String[] args) {
        Poste d=new Poste(5035,"sisirejeb","sayada",3);
        Client c=new Client();
        SwingUtilities.invokeLater(() -> new RendezVousInterface(d,c).setVisible(true));
    }
}