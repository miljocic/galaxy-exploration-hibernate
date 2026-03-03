package dao;

import model.Galaksija;
import model.Istrazivac;
import model.NebeskoTelo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class HQLUpiti {

    public static void pokreni() {



        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Scanner scanner = new Scanner(System.in);



        System.out.println("\n Izaberite HQL upit:");
        System.out.println("1️. Pronadji sva nebeska tela u galaksiji sa najvecim prosecnim precnikom (manjim od N)");
        System.out.println("2️. Proveri da li postoje dva nebeska tela koja se okrecu jedno oko drugog");
        System.out.println("3️. Pronadji najvisi nivo obrazovanja istrazivaca po ustanovi");
        System.out.println("4️. Identifikuj istrazivace koji imaju vise razlicitih uloga u timovima");
        System.out.println("5️. Pronadji istrazivace koji su poslali vise od 5 sumova tokom jedne misije");
        System.out.print(" Unesite broj opcije: ");
        int izbor = scanner.nextInt();
        scanner.nextLine();

        if (izbor == 1) {
            System.out.print(" Unesite maksimalni dozvoljeni prosecni precnik galaksije (N): ");
            double n = scanner.nextDouble();
            scanner.nextLine();
            pronadjiNebeskaTelaNajveceGalaksije(session, n);
        } else if (izbor == 2) {
            proveriMedjusobnuRotaciju(session);
        }
        else if (izbor == 3) {
            prikaziNajvisiNivoObrazovanja(session);
        }
        else if (izbor == 4) {
           // prikaziIstrazivaceSaVisestrukimUlogama(session);
        }
        else if (izbor == 5) {
            //prikaziIstrazivaceSaViskomSumova(session);
        }
        session.close();
    }

    private static void pronadjiNebeskaTelaNajveceGalaksije(Session session, double n) {
        Galaksija najvecaGalaksija = session.createQuery(
                        "FROM Galaksija " +
                                "WHERE prosecniPrecnik < :n " +
                                "ORDER BY prosecniPrecnik DESC", Galaksija.class)
                .setParameter("n", n)
                .setMaxResults(1)
                .uniqueResult();

        if (najvecaGalaksija == null) {
            System.out.println(" Nema galaksije sa prosecnim precnikom manjim od " + n);
            return;
        }

        System.out.println("\n Galaksija sa najvecim prosecnim precnikom < " + n + ": " + najvecaGalaksija.getIme());

        List<NebeskoTelo> nebeskaTela = session.createQuery(
                        "FROM NebeskoTelo WHERE galaksija.id = :galaksijaID", NebeskoTelo.class)
                .setParameter("galaksijaID", najvecaGalaksija.getId())
                .getResultList();

        if (nebeskaTela.isEmpty()) {
            System.out.println(" Ova galaksija nema nebeskih tela.");
        } else {
            System.out.println(" Nebeska tela u galaksiji " + najvecaGalaksija.getIme() + ":");
            for (NebeskoTelo telo : nebeskaTela) {
                System.out.println(" " + telo.getIme() + " (" + telo.getTip() + ")");
            }
        }
    }

    private static void proveriMedjusobnuRotaciju(Session session) {
        List<Object[]> medjusobnaRotacija = session.createQuery(
                        "SELECT nt1, nt2 " +
                                "FROM NebeskoTelo nt1 " +
                                "JOIN NebeskoTelo nt2 ON nt1.teloOkretanja.id = nt2.id " +
                                "WHERE nt2.teloOkretanja.id = nt1.id", Object[].class)
                .getResultList();

        boolean postojiRotacija = !medjusobnaRotacija.isEmpty();
        System.out.println("\n Da li postoje dva tela koja se okrecu jedno oko drugog? " + (postojiRotacija ? " Da" : " Ne"));

        if (postojiRotacija) {
            for (Object[] par : medjusobnaRotacija) {
                NebeskoTelo telo1 = (NebeskoTelo) par[0];
                NebeskoTelo telo2 = (NebeskoTelo) par[1];
                System.out.println(" " + telo1.getIme() + " " + telo2.getIme());
            }
        }

        List<Object[]> istiTipRotacija = session.createQuery(
                        "SELECT nt1, nt2 " +
                                "FROM NebeskoTelo nt1 " +
                                "JOIN NebeskoTelo nt2 ON nt1.teloOkretanja.id = nt2.id " +
                                "WHERE nt2.teloOkretanja.id = nt1.id AND nt1.tip = nt2.tip", Object[].class)
                .getResultList();

        boolean postojiIstiTipRotacija = !istiTipRotacija.isEmpty();
        System.out.println("\n Da li postoje tela istog tipa koja se okrecu jedno oko drugog? " + (postojiIstiTipRotacija ? " Da" : " Ne"));

        if (postojiIstiTipRotacija) {
            for (Object[] par : istiTipRotacija) {
                NebeskoTelo telo1 = (NebeskoTelo) par[0];
                NebeskoTelo telo2 = (NebeskoTelo) par[1];
                System.out.println(" " + telo1.getIme() + "  " + telo2.getIme() + " (" + telo1.getTip() + ")");
            }
        }
    }


    private static void prikaziNajvisiNivoObrazovanja(Session session) {
        List<Object[]> rezultati = session.createQuery(
                        "SELECT i.institucija.ime, MAX(i.nivoObrazovanja) " +
                                "FROM Istrazivac i " +
                                "WHERE i.nivoObrazovanja IN ('Osnovne', 'Master', 'Doktorat') " +
                                "GROUP BY i.institucija.ime", Object[].class)
                .getResultList();

        if (rezultati.isEmpty()) {
            System.out.println(" Nema istrazivaca sa validnim nivoom obrazovanja.");
        } else {
            System.out.println("\n Najvisi nivo obrazovanja istrazivaca po ustanovi:");
            for (Object[] rezultat : rezultati) {
                String institucija = (String) rezultat[0];
                String najvisiNivo = (String) rezultat[1];

                System.out.println(" " + institucija + " |  Najvisi nivo: " + najvisiNivo);
            }
        }
    }




}
