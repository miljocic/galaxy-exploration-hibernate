package dao;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.*;

public class UpitiJava {

    public static void pokreni() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Izaberite Java upit:");
        System.out.println("1. Prosecna masa nebeskih tela u galaksijama");
        System.out.println("2️. Hijerarhija nebeskih tela");
        System.out.println("3. Prikaz istrazivaca iz odredjenog grada");
        System.out.println("4️. Prikaz istrazivaca koji su ucestvovali na izmedju N i M misija");
        System.out.println("5️. Analiza suma i misije sa najvise sumova istrazivaca");
        System.out.print(" Unesite broj opcije: ");
        int izbor = scanner.nextInt();
        scanner.nextLine();

        if (izbor == 1) {
            prikaziProsecnuMasu(session);
        } else if (izbor == 2) {
            System.out.print(" Unesite naziv nebeskog tela: ");
            String imeTela = scanner.nextLine();
            prikaziHijerarhiju(session, imeTela);
        }
        else if (izbor == 3) {
            System.out.print(" Unesite naziv grada: ");
            String nazivGrada = scanner.nextLine();
            prikaziIstrazivaceIzGrada(session, nazivGrada);
        }
        else if (izbor == 4) {
            System.out.print(" Unesite donju granicu (N): ");
            int n = scanner.nextInt();
            System.out.print(" Unesite gornju granicu (M): ");
            int m = scanner.nextInt();
            scanner.nextLine();
            //prikaziIstrazivaceSaMisijama(session, n, m);
        }
        else if (izbor == 5) {
            System.out.print(" Unesite sum (deo teksta): ");
            String sadrzajSuma = scanner.nextLine();
            //prikaziIstrazivaceSaViskomSumova(session);
        }
        session.close();
    }

    private static void prikaziProsecnuMasu(Session session) {
        var tx = session.beginTransaction();
        try {
            List<Galaksija> galaksije = session.createQuery("FROM Galaksija", Galaksija.class).getResultList();
            for (Galaksija galaksija : galaksije) {
                Set<NebeskoTelo> tela = galaksija.getNebeskaTela();
                if (tela == null || tela.isEmpty()) {
                    System.out.println("Galaksija: " + galaksija.getIme() + " | Prosecna masa: nema podataka");
                    continue;
                }

                OptionalDouble prosek = tela.stream().mapToDouble(NebeskoTelo::getMasa).average();
                System.out.println("Galaksija: " + galaksija.getIme() + " | Prosecna masa: " + prosek.orElse(0));
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Greska: " + e.getMessage());
        }
    }



    private static void prikaziHijerarhiju(Session session, String imeTela) {
        var tx = session.beginTransaction();
        try {
            List<NebeskoTelo> tela = session.createQuery("FROM NebeskoTelo", NebeskoTelo.class).getResultList();
            NebeskoTelo pocetnoTelo = tela.stream()
                    .filter(t -> t.getIme().equalsIgnoreCase(imeTela))
                    .findFirst()
                    .orElse(null);

            if (pocetnoTelo == null) {
                System.out.println("Telo sa imenom '" + imeTela + "' nije pronadjeno.");
            } else {
                System.out.println("Hijerarhija za telo: " + pocetnoTelo.getIme());
                prikaziRekurzivno(pocetnoTelo, 0);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Greska: " + e.getMessage());
        }
    }


    private static void prikaziRekurzivno(NebeskoTelo telo, int nivo) {

        System.out.println("\t".repeat(nivo) + telo.getIme() + " (" + telo.getTip() + ")");
        Set<NebeskoTelo> podredjena = telo.getPodredjenaTela();
        if (podredjena != null) {
            for (NebeskoTelo pod : podredjena) {
                prikaziRekurzivno(pod, nivo + 1);
            }
        }
    }

    private static void prikaziIstrazivaceIzGrada(Session session, String grad) {
        var tx = session.beginTransaction();
        try {
            List<Istrazivac> istrazivaci = session.createQuery("FROM Istrazivac", Istrazivac.class).getResultList();
            Set<Istrazivac> rezultat = new HashSet<>();

            for (Istrazivac i : istrazivaci) {
                boolean rodjenUGradu = grad.equalsIgnoreCase(i.getGradRodjenja());
                boolean radiUGradu = i.getInstitucija() != null && grad.equalsIgnoreCase(i.getInstitucija().getGrad());
                if (rodjenUGradu || radiUGradu) rezultat.add(i);
            }

            if (rezultat.isEmpty()) {
                System.out.println("Nema istrazivaca povezanih sa gradom: " + grad);
            } else {
                for (Istrazivac i : rezultat) {
                    System.out.println(i.getIme() + " " + i.getPrezime() +
                            " | Institucija: " + (i.getInstitucija() != null ? i.getInstitucija().getIme() : "N/A"));
                }
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Greska u transakciji: " + e.getMessage());
        }
    }

}
