package dao;

import model.Misija;
import model.NebeskoTelo;
import model.Istrazivac;
import model.Sum;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlobodniUpiti {

    public static void pokreni() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        obradiPodatakONebeskomTelu(session);
        session.close();
    }


    private static void obradiPodatakONebeskomTelu(Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(" Unesite podatke o nebeskom telu u formatu: ");
        String unos = scanner.nextLine();

        Pattern pattern = Pattern.compile(
                "planeta: ime = (.+), masa = ([^ ]+) (g|kg|t), zapremina = ([^ ]+) (km3|m3)");
        Matcher matcher = pattern.matcher(unos);

        if (!matcher.find()) {
            System.out.println(" Pogresan format unosa!");
            return;
        }

        String imePlanete = matcher.group(1).trim();
        String masaStr = matcher.group(2);
        String jedinicaMase = matcher.group(3);
        String zapreminaStr = matcher.group(4);
        String jedinicaZapremine = matcher.group(5);

        double masa, zapremina;

        try {
            masa = Double.parseDouble(masaStr);
            zapremina = Double.parseDouble(zapreminaStr);
        } catch (NumberFormatException e) {
            System.out.println("Primetno je da ste pod dejstvom alkohola");
            Transaction tx = session.beginTransaction();


            Sum sum = new Sum();
            sum.setPodatak(unos);
            sum.setMisija(session.get(Misija.class, 1));
            sum.setIstrazivac(session.get(Istrazivac.class, 1));
            sum.setTelo(session.get(NebeskoTelo.class, 1));

            session.persist(sum);
            tx.commit();
            return;
        }


        switch (jedinicaMase) {
            case "g": masa /= 1000; break;
            case "t": masa *= 1000; break;
            case "kg": break;
            default:
                System.out.println(" Nevalidna jedinica mere za masu!");
                return;
        }

        switch (jedinicaZapremine) {
            case "m3": zapremina *= 1e-9; break;
            case "km3": break;
            default:
                System.out.println(" Nevalidna jedinica mere za zapreminu!");
                return;
        }

        Transaction tx = session.beginTransaction();

        List<NebeskoTelo> tela = session.createQuery(
                        "FROM NebeskoTelo WHERE ime = :ime", NebeskoTelo.class)
                .setParameter("ime", imePlanete)
                .getResultList();

        if (tela.isEmpty()) {
            System.out.println(" Planeta '" + imePlanete + "' ne postoji u bazi!");


            Sum sum = new Sum();
            sum.setPodatak(unos);
            sum.setMisija(session.get(Misija.class, 1));
            sum.setIstrazivac(session.get(Istrazivac.class, 1));
            sum.setTelo(session.get(NebeskoTelo.class, 1));

            session.persist(sum);
            tx.commit();
            return;
        }


        NebeskoTelo telo = tela.get(0);
        telo.setMasa(masa);
        telo.setZapremina(zapremina);
        session.update(telo);

        tx.commit();
        System.out.println(" Podaci uspesno azurirani za planetu: " + telo.getIme());
    }
}

//planeta: ime = Zemlja, masa = 5.97 t, zapremina = 1.08321e12 m3