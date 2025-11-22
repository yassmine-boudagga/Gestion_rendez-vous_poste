/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jproject;

import java.util.regex.Pattern;

import static java.lang.Character.isDigit;


/**
 *
 * @author GIS
 */
public class Personne {
    protected String cin;
    protected String email;
    protected String nom;
    protected String prenom;
    protected String motdepasse;
    protected String tel;
    public boolean verifier(String cin){
        int i=0;
        if(cin.length()==8) {
            while(i<8){
                if(isDigit(cin.charAt(i))) {
                    i++;
                } 
                else {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
    public boolean verfiernum(String tel){
        if(tel.length()==8 &&(tel.charAt(0)=='5' || tel.charAt(0)=='2' || tel.charAt(0)=='9' || tel.charAt(0)=='4')) {
            for (int i = 1; i < 8; i++) {
                if(!(isDigit(tel.charAt(i)))) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
   public boolean recherche(String email){
       int i=1;
       while(i<email.length()){
           if(email.substring(i).equals("@gmail.com")){
               return true;
           }
           else{
               i++;
           }
       }
       return false;
   }
    public Personne(String cin, String email, String motdepasse, String nom, String prenom, String tel) {
        if(!(verifier(cin))){
            System.out.println("introduire cin valide !");
            /*entrer un autre cin et devient boucle xhile*/
        }
        else{
            this.cin = cin;
            if(!(validateEmail(email))){
                System.out.println("introduire email valide !");
                /*boucle while*/
            }
            else{
            this.email = email;
            this.motdepasse = motdepasse;
            this.nom = nom;
            this.prenom = prenom;
            if(!verfiernum(tel) ){
                System.out.println("introduire telephone valide !");
            }
            else{
              this.tel = tel;}}
        }
    }
    public Personne(){}
    public String getCin() {
        return cin;
    }
    public String getEmail() {
        return email;
    }
    public String getMotdepasse() {
        return motdepasse;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getTel() {
        return tel;
    }
    public void setCin(String cin) {
        if(!(verifier(cin))){
            System.out.println("introduire cin valide !");
            /*entrer un autre cin et devient boucle xhile*/
        }
        else{
        this.cin = cin;}
    }
    public void setEmail(String email) {
        if(!(recherche(email))){
                System.out.println("introduire email valide !");
                /*boucle while*/
            }
        else{
        this.email = email;}
    }
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setTel(String tel) {
         if(!verfiernum(tel) ){
                System.out.println("introduire telephone valide !");
            }
            else{
              this.tel = tel;}
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches()&& this.recherche(email);
    }
    @Override
    public String toString() {
        return "Personne{" + "cin=" + cin + ", email=" + email + ", motdepasse=" + motdepasse + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + '}';
    }
    public static void main(String[]arg){
        Personne p=new Personne("14025936","rejeb@gmail.com","rejebyy","rejeb","guetari","56827998");
        System.out.println(p.validateEmail("t@gmail.com"));
    }
}