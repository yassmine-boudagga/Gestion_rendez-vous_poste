/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jproject;

import java.util.Scanner;

/**
 *
 * @author GIS
 */
public class Jproject {

    
    
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        // TODO code application logic here
       Poste p=new Poste(12,"moknine","sdsd",4);
       Client c=new Client("14054545","rhfdppy@gmail.com","jhj","btgbgyf","jyukiy","20567230");
       Client c1=new Client("15254545","rdrgsy@gmail.com","jhj","btgbgyf","jyukiy","20567230");
       Client c2=new Client("14054645","rhhjkdy@gmail.com","jhj","btgbgyf","jyukiy","20567230");
       c.setA();
       c1.setA();
       c2.setA();
        /*System.out.println("si vous etes client tapez 0 sinon 1 !");
        int b;
        b = scanner.nextInt();
        if(b==0){
            System.out.println("Bienvenue cher(e) client(e)");
        System.out.println("si tu veux prendre rendez-vous dans un bureau tapez:1 ");
        System.out.println("si tu as besoin d'un guichet tapez: 2");
        int a=scanner.nextInt();*/
        
}}
