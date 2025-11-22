package Basedonner;
import java.util.Scanner;
public class fff {
        public static void comparaison(int x, int y) {
            if (x>y)
                System.out.println(x + " est plus grand que " + y);
            else
                System.out.println(y + " est plus grand que " + x); }
        public static void main(String[] args) {
            Scanner sc= new Scanner(System.in);
            int x = sc.nextInt();
            int y = sc.nextInt();
            comparaison(x, y); }}
