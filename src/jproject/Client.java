/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jproject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
public class Client extends Personne{//wanahi il post illi bach ya3mill faha rondivous
    private int num=-1;//ichnouwa dorou hathaya il variable
    private int a;//prend 0 ou 1 service du bureau ou guichet
    private Date dateTime ;//temps eli y7ebou houwa ya5thou
    private LocalDate datelocale = LocalDate.now();
    DateTimeFormatter timesql=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Scanner scanner =new Scanner(System.in);
    public Client(String cin, String email, String motdepasse, String nom, String prenom, String tel) {
        super(cin, email, motdepasse, nom, prenom, tel);
    }
    public Client(){}

    public Date getDateTime(){
        return this.dateTime;
    }
    public LocalDate getDatelocale() {
        return datelocale;
    }
    public void setDatelocale(LocalDate datelocale) {
        this.datelocale = datelocale;
    }
    public String convertDateFormatB(String inputDate) {
        try {
            // Définir le format d'entrée
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
            // Définir le format de sortie
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // Convertir la chaîne en LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
            // Reconvertir LocalDateTime en chaîne dans le nouveau format
            return dateTime.format(outputFormatter);
        } catch (Exception e) {
            // Gérer les erreurs si la chaîne n'est pas dans le bon format
            return "Format de date invalide : " + inputDate;
        }
    }
    public String convertDateFormatL(String inputDate) {
        try {
            // Définir le format d'entrée
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // Définir le format de sortie
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
            // Convertir la chaîne en LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
            // Reconvertir LocalDateTime en chaîne dans le nouveau format
            return dateTime.format(outputFormatter);
        } catch (Exception e) {
            // Gérer les erreurs si la chaîne n'est pas dans le bon format
            return "Format de date invalide : " + inputDate;
        }
    }
    @Override
    public String getCin() {
        return super.getCin();
    }
    @Override
    public String getPrenom() {
        return super.getPrenom();
    }
    @Override
    public String getTel() {
        return super.getTel();
    }
    @Override
    public String getEmail() {
        return super.getEmail();
    }
    @Override
    public String getMotdepasse() {
        return super.getMotdepasse();
    }
    @Override
    public String getNom() {
        return super.getNom();
    }
    @Override
    public void setPrenom(String prenom) {
        super.setPrenom(prenom);
    }
    @Override
    public void setCin(String cin) {
        super.setCin(cin);
    }
    @Override
    public void setMotdepasse(String motdepasse) {
        super.setMotdepasse(motdepasse);
    }
    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }
    public void setA(){
        System.out.println("si tu veux prendre rendez-vous dans un bureau tapez:1 ");
        System.out.println("si tu as besoin d'un guichet tapez: 2");
        this.a=scanner.nextInt();
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public int getNum() {
        return num;
    }
    public int getA() {
        return a;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void afficherDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm:ss");
        System.out.println("Date et heure : " + dateTime);
    }
    public static void main(String []arg){
        Client c=new Client("14025946","rejeb@gmail.com","jdhjfbd","guetari","rejeb","22222222");
        System.out.println("Date locale formatée : " + c.getDatelocale().format(c.timesql));
    }
}
