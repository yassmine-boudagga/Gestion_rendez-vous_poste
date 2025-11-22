/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jproject;

import Basedonner.BaseProger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
//awel 7aja n3amrou table poste
public class Poste {
    private  int id;//imta3 ichniya
    private String nom;
    private String adress;
    private int nb_guichet;
    private ArrayList<Integer>tik=new ArrayList<Integer>();
    private ArrayList<Client> clientsrendezvous = new ArrayList<Client>();
    private ArrayList<Date> Rendezvous=new ArrayList<Date>();
    private int derticket;
    private int nombre_client_en_post;
    public ArrayList<Date> getRendezvous() {
        return Rendezvous;
    }
    public void setRendezvous(ArrayList<Date> rendezvous) {
        Rendezvous = rendezvous;
    }
    public ArrayList<Integer> getTik() {
        return tik;
    }
    public void setTik(ArrayList<Integer> tik) {
        this.tik = tik;
    }

    public int getNb_guichet() {
        return nb_guichet;
    }

    public int getDerticket() {
        return derticket;
    }
    public void setDerticket(int derticket) {
        this.derticket = derticket;
    }
    public int selectTik(int tie){
        System.out.println(tie);
        System.out.println("liste hfbhdf:"+this.tik.size());
        for (int i = 0; i < this.tik.size(); i++) {
            System.out.println(this.tik.get(i)==tie);
            if (this.tik.get(i)==tie){
                tie++;
            }
        }
        return tie;
    }
    public int selecttemp(int tie,int s){
        Collections.sort(this.tik);
        for (int i = 0; i < this.tik.size(); i++) {
            if (this.tik.get(i)==tie){
                s+=2;
            }
        }
        return s;
    }

    public ArrayList<Client> getClientsrendezvous() {
        return clientsrendezvous;
    }

    public void setClientsrendezvous(ArrayList<Client> clientsrendezvous) {
        this.clientsrendezvous = clientsrendezvous;
    }
    public int getNombre_client_en_post() {
        return nombre_client_en_post;
    }
    public void setNombre_client_en_post(int nombre_client_en_post) {
        this.nombre_client_en_post = nombre_client_en_post;
    }
    //mi ya5tar el poste nasna3 objet de type poste et remplir mel bd w nasna3 objet client bel les cordonnée eli da5alhom
    public Poste(int id,String nom, String adress, int nb_guichet) {
        this.id=id;
        this.nom = nom;
        this.adress = adress;
        this.nb_guichet = nb_guichet;
    }
    public Poste(){}
    //recherche de client qui ont la m date de rendezvous
    // Verifier la date choisi
    public boolean veriftemp(Date d ){
        return (d.getHours()<17 &&d.getHours()>=14)||(d.getHours()<12 && d.getHours()>=9);
    }
    public boolean verifdecalage(Date d){
        System.out.println(this.Rendezvous.size());
        for (int i = 0; i < this.Rendezvous.size(); i++) {
            if(Math.abs(d.getTime()-this.Rendezvous.get(i).getTime())<1200000){
                return false;
            }
        }
        return true;
    }
    public Client recherche(Client c){
        int i=0;
        while(i<clientsrendezvous.size()){
            if(clientsrendezvous.get(i).getDateTime().equals(c.getDateTime())){
                return clientsrendezvous.get(i);
            }
            else{
                i++;
            }
        }
        return null;
    }
    //suppression de rendezvous ki lwa9et yfout
    /*
    public void supprimerRendezvous(){
        LocalDateTime dateHeureMaintenant = LocalDateTime.now();
        for(int i=0;i<clientsrendezvous.size();i++){
            if(clientsrendezvous.get(i).getDateTime()==dateHeureMaintenant){
                clientsrendezvous.remove(i);
            }
        }
    }
    */
    //affiche les rendezvous reservé
    /*
    public void affichelisterendezvous(){
        for(int i=0;i<clientsrendezvous.size();i++){
            System.out.println(clientsrendezvous.get(i).getDateTime());
        }
    }*/
    //calcule de date dispo +20min
    /*
    public LocalDateTime calculedatedispo(Client c){
        LocalDateTime dateTime2=c.getDateTime();
        int time=dateTime2.getMinute()+20;
        //System.out.println("mininute"+time);
        int hour=dateTime2.getHour();
        //System.out.println("hour1="+hour);
        if(time>=60){
            hour+=(int)time/60;
            time=time%60;
        }
        if(hour<15 && hour>=12){
            hour=15;
            time=00;
        }
        dateTime2=LocalDateTime.of(2024, dateTime2.getMonthValue(), dateTime2.getDayOfMonth(), hour, time);
        return dateTime2;
    }*/
    //validation mt3 date calculée

    //calcule de date dispo -20minute
    /*
    public LocalDateTime calculedatedispo2(Client c){
        LocalDateTime dateTime2=c.getDateTime();
        int time=dateTime2.getMinute()-20;
        int hour=dateTime2.getHour();
        if(time<0){
            hour-=1;
            time=60+time;
        }
        if(hour<15 && hour>=12){
            hour=11;
            time=40;
        }
        dateTime2=LocalDateTime.of(2024, dateTime2.getMonthValue(), dateTime2.getDayOfMonth(), hour, time);
        return dateTime2;
    }*/
    //chercher les clients qui ont une heure < heure de client pour calculer num de ticket
     /*public int rechercher(Client c){
         int nb=0;
       for(int i=0;i<clientsguichet.size();i++){
           if(clientsguichet.get(i).getDateTime().getHour()<c.getDateTime().getHour()){
               nb++;
           }
       }
       return nb;}*/
    //la derniere ticket feli 9ablou
    /*
     public int chercherderniereticket(Client c){
         int num=0;
         for(int i=0;i<clientsguichet.size()-1;i++){
           if((clientsguichet.get(i).getDateTime().getHour()<c.getDateTime().getHour())&&(clientsguichet.get(i).getDateTime().getMinute()<c.getDateTime().getMinute())){
               if(clientsguichet.get(i).getNum()<clientsguichet.get(i+1).getNum()){
                   if(clientsguichet.get(i).getNum()!=c.getNum()){
                   num=clientsguichet.get(i).getNum();}
               }
               else{
                   if(clientsguichet.get(i).getNum()!=c.getNum()){
                        num=clientsguichet.get(i+1).getNum();
                    }
               }
           }
     }
         return num;}*/
    //est ce que ticket est reservée ou non
    //suppression de client de fil d'attente apres temps
    /*
    public void supprimerGuichet(){
        LocalDateTime dateHeureMaintenant = LocalDateTime.now();
        for(int i=0;i<clientsguichet.size();i++){
            if(clientsguichet.get(i).getDateTime().getHour()==dateHeureMaintenant.getHour()){
                if(clientsguichet.get(i).getDateTime().getMinute()<dateHeureMaintenant.getMinute()){
                    clientsguichet.remove(i);
                }
            }
        }
    }*/
    //insertion de client dans table rendezvous ou guichet
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }
    public void setNb_guichet(int nb_guichet) {
        this.nb_guichet = nb_guichet;
    }
    public boolean verifierHeureValide(String heure) {
        try {
            String[] parts = heure.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            if ((h >= 8 && h < 12) || (h >= 15 && h < 17) || (h == 17 && m == 0) || (h == 12 && m == 0)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Poste{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adress='" + adress + '\'' +
                ", nb_guichet=" + nb_guichet +
                '}';
    }

    public static void main(String[]args){

    }
}