import dao.HQLUpiti;
import dao.SlobodniUpiti;
import dao.UpitiJava;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n Izaberite vrstu upita:");
            System.out.println("1️. Java upiti");
            System.out.println("2️. HQL upiti");
            System.out.println("3. Slobodni SQL upiti");
            System.out.println("4. Izlaz");
            System.out.print("Unesite broj opcije: ");
            int izbor = scanner.nextInt();

            if (izbor == 1) {
                UpitiJava.pokreni();
            } else if (izbor == 2) {
                HQLUpiti.pokreni();
            } else if (izbor == 3) {
                SlobodniUpiti.pokreni();
            } else if (izbor == 4) {
                System.out.println("Izlaz iz programa.");
                break;
            } else {
                System.out.println("Neispravan unos. Pokusajte ponovo.");
            }
        }
    }
}
