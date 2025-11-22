package Basedonner;
import jproject.Client;
import jproject.Poste;
import test.ConfirmationGuichetInterface;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class BaseProger extends Base {
    public BaseProger(String user){
        super("service_post",user,"");
    }
    public BaseProger(){}
    public void insertRendivou(int id,String cin,LocalDateTime da){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd à HH:mm:ss");
        String []op={"="};
        String []col={"cin"};
        String []valcon={cin};
        int a=super.nbr_ligne("client",col,valcon,op);
        if (a<0){
            System.out.println("erreur de saisir de valeur");
        } else if (a==0) {
            System.out.println("le client il nexiste pas");
        }
        else{
            String []T={String.valueOf(id),cin,da.format(formatter)};
            super.insert("rendevou",T);
        }
    }
    public void afficher_Client(){
        super.affichage("client");
    }
    public void affiche_client_rendevou(){
        super.requetAffichage("SELECT c.cin,c.nom,c.prenom,c.tel,c.email,c.adress,r.date_rendevou,p.nom FROM post p,client c,rendevou r where  p.N_post=r.N_post AND c.cin=r.cin;");
    }
    public ArrayList<Integer> tikerReserver(Poste p){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            LocalDateTime dateTime = LocalDateTime.now(); // La date et heure actuelles
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
            String dateString = dateTime.format(formatter);
            String deb=dateString+"00:00:00";
            String fin=dateString+"23:59:59";
            ArrayList<Integer> l=new ArrayList<Integer>();
            try {
                Statement statement=connection.createStatement();
                System.out.println("SELECT * FROM reserver_tiker WHERE date <\'"+fin+"\' AND date >=\'"+deb+"\' AND id="+String.valueOf(p.getId())+";");
                ResultSet resultSet=statement.executeQuery("SELECT * FROM reserver_tiker WHERE date <\'"+fin+"\' AND date >=\'"+deb+"\' AND id=\'"+String.valueOf(p.getId())+"\';");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int a;
                while (resultSet.next()){
                    a=resultSet.getInt(3);
                    l.add(a);
                }
                resultSet.close();
                statement.close();
                return l;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public boolean ajoutClient(Client c){
        String []op={"="};
        String []col={"cin"};
        String []valcon={c.getCin()};
        int a=super.nbr_ligne("client",col,valcon,op);
        if (a<0){
            System.out.println("erreur de saisir de valeur");
            return false;
        } else if (a==0) {
            String[]t={c.getCin(),c.getNom(),c.getPrenom(),c.getEmail(),c.getMotdepasse(),c.getTel()};
            return super.insert("client",t);
        }
        else{
            System.out.println("le client il est déja existance");
            return false;
        }
    }
    public void insertRendivou(String cin,int N_post,String dat){
        String []op={"="};
        String []col={"cin"};
        String []valcon={String.valueOf(cin)};
        int a=super.nbr_ligne("client",col,valcon,op);
        if (a<0){
            System.out.println("erreur de saisir de valeur");
        } else if (a==0) {
            System.out.println("le cin il nexiste pas ");
        }
        else{
            String[]t={String.valueOf(N_post),String.valueOf(cin),dat};
            super.insert("redevou",t);
        }
    }
    public Client getClient(String mail,String motpass){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            String []op={"=","="};
            String []col={"mail","motpass"};
            String []valcon={mail,motpass};
            int a=super.nbr_ligne("client",col,valcon,op);
            if (a<0){
                System.out.println("erreur de saisir de valeur");
                return null;
            } else if (a==0) {
                System.out.println("le client il nest pas existance faire une inscription");
            }
            else{
                try {
                    Statement statement=connection.createStatement();
                    StringBuilder requet=new StringBuilder("select * from client where mail=? and motpass=?;");
                    PreparedStatement prepare=connection.prepareStatement(requet.toString());
                    prepare.setObject(1,mail);
                    prepare.setObject(2,motpass);
                    ResultSet resultSet=prepare.executeQuery();
                    // 4. Récupération des métadonnées
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                    String []T=new String[columnCount];
                    if (resultSet.next()){
                        for (int i = 1; i <=columnCount ; i++) {
                            T[i-1]=resultSet.getString(i);
                        }
                        Client c=new Client(T[0],T[3],T[4],T[1],T[2],T[5]);
                        resultSet.close();
                        statement.close();
                        return c;
                    }
                    resultSet.close();
                    statement.close();
                }
                catch (SQLException e){
                    System.out.println("erreur sss laffichage");
                    System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                    System.out.println("Une erreur SQL s'est produite.");
                    System.out.println("Message : " + e.getMessage());
                    System.out.println("SQLState : " + e.getSQLState());
                    System.out.println("Code d'erreur : " + e.getErrorCode());
                    return null;
                }
            }
            return null;
        }
    }
    public ArrayList<Client> listClient_Redevou(Poste p){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            ArrayList<Client> l=new ArrayList<Client>();
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT c.cin,c.nom,c.prenom,c.mail,c.motpass,c.tel,r.redevou FROM client c,redevou r WHERE c.cin=r.cin AND id=\'"+p.getId()+"\';");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                String []T=new String[columnCount];
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        T[i-1]=resultSet.getString(i);
                    }
                    Client c=new Client(T[0],T[3],T[4],T[1],T[2],T[5]);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        Date date = formatter.parse(T[6]);
                        System.out.println("Date convertie : " + date);
                        c.setDateTime(date);
                    } catch (ParseException e) {
                        System.out.println("Format de date invalide : " + e.getMessage());
                    }
                    l.add(c);
                }
                resultSet.close();
                statement.close();
                return l;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public ArrayList<Date> list_Redevou(Poste p){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            ArrayList<Date> l=new ArrayList<Date>();
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT redevou FROM redevou  WHERE id="+p.getId()+";");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                String t;
                // Convertir la chaîne de caractères en date
                while (resultSet.next()) {
                    t = resultSet.getString(1);
                    try {
                        // Définir le format attendu pour la chaîne de caractères
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        // Convertir la chaîne de caractères en date
                        Date date = sdf.parse(t);
                        l.add(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Erreur de conversion de la chaîne en date");
                    }
                }
                resultSet.close();
                statement.close();
                return l;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public ArrayList<Client> listClient_guicher(){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            ArrayList<Client> l=new ArrayList<Client>();
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT c.cin,c.nom,c.prenom,c.mail,c.motpass,c.tel FROM client c,reserver_tiker t WHERE c.cin=t.cin;");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                String []T=new String[columnCount];
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        T[i-1]=resultSet.getString(i);
                    }
                    Client c=new Client(T[0],T[3],T[4],T[1],T[2],T[5]);
                    l.add(c);
                }
                resultSet.close();
                statement.close();
                return l;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public boolean existe_mail(String mail){
        String []op={"="};
        String []col={"mail"};
        String []valcon={mail};
        int a=super.nbr_ligne("client",col,valcon,op);
        if (a<0){
            System.out.println("erreur de saisir de valeur");
        } else if (a==0) {
            System.out.println("limail nexista pas");
            return false;
        }
        else{
            System.out.println("le client il est déja existance");
            return true;
        }
        return false;
    }
    public String getMotPass(String mail){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT motpass FROM client WHERE mail=\'"+mail+"\';");
                    // 4. Récupération des métadonnées// Nombre de colonnes
                String Password=null;
                if (resultSet.next()){
                    Password=resultSet.getString(1);
                }
                resultSet.close();
                statement.close();
                return Password;
            }
                catch (SQLException e){
                    System.out.println("erreur laffichage");
                    System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                    System.out.println("Une erreur SQL s'est produite.");
                    System.out.println("Message : " + e.getMessage());
                    System.out.println("SQLState : " + e.getSQLState());
                    System.out.println("Code d'erreur : " + e.getErrorCode());
                    return null;
                }
            }
    }
    public boolean existe_cin(String cin){
        String []op={"="};
        String []col={"cin"};
        String []valcon={cin};
        int a=super.nbr_ligne("client",col,valcon,op);
        if (a<0){
            System.out.println("erreur de saisir de valeur");
        } else if (a==0) {
            System.out.println("cin nexista pas");
            return false;
        }
        else{
            System.out.println("le client il est déja existance");
            return true;
        }
        return false;
    }
    public Poste[] getPoste(){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT * FROM post;");
                // 4. Récupération des métadonnées
                int l=this.nbr_ligne("post");
                Poste[]T=new Poste[l];
                int i=0;
                while (resultSet.next()){
                    T[i]=new Poste(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
                    i++;
                }
                resultSet.close();
                statement.close();
                return T;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public String[] getRendevousPost(Poste p){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT redevou FROM redevou  WHERE  id="+p.getId()+";");
                // 4. Récupération des métadonnées
                ArrayList<String>l=new ArrayList<String>();
                while (resultSet.next()){
                    l.add(resultSet.getString(1));
                }
                String[]T=new String[l.size()];
                for (int i = 0; i < l.size(); i++) {
                    T[i]=l.get(i);
                }
                resultSet.close();
                statement.close();
                return T;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public void suprimerrendevous(){
        String[]col={"redevou"};
        String[]op={"<"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        String[]val={formatter.format(d)};
        System.out.println(formatter.format(d));
        String []colc={"id","cin"};
        String []opp={"=","="};
        String [][]t=super.tableaffichage("redevou",col,val,op);
        if (t == null) {
            System.out.println("aucun suprimer");
        }
        else {
            for (int i = 0; i < t.length; i++) {
                String[] T = {t[i][0], t[i][1]};
                super.delete("redevou", colc, T, opp);
            }
        }
    }
    public void suprimertiket(){
        String[]col={"date"};
        String[]op={"<"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        String[]val={formatter.format(d)};
        System.out.println(formatter.format(d));
        String []colc={"id","cin"};
        String []opp={"=","="};
        String [][]t=super.tableaffichage("reserver_tiker",col,val,op);
        if (t == null) {
            System.out.println("tableau aucun case suprimer");
        }
        else {
            for (int i = 0; i < t.length; i++) {
                String[] T = {t[i][0], t[i][1]};
                super.delete("reserver_tiker", colc, T, opp);
            }
        }
    }
    public void updaterendevou(Poste p,Client c,String date){
        String[]colonecondition={"id","cin"};
        String[]valeurcondition={String.valueOf(p.getId()),c.getCin()};
        String[]val={date};
        String[]col={"redevou"};
        super.update("redevou",colonecondition,valeurcondition,col,val);
    }
    public static void main(String[] args){
        BaseProger b=new BaseProger("rejeb");
        Client c=new Client("14025946","rejebktari@gmail.com","relebdd","guetari","rejeb","26827198");
        Poste p=new Poste(5035,"dsds","sdsd",3);
        String [][] t=b.tableaffichage("tiker");
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                System.out.print(t[i][j]+" ");
            }
            System.out.println();
        }
        ArrayList<Integer>l=b.tikerReserver(p);
        System.out.println(l.size());
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
        b.deconnection();
    }
}