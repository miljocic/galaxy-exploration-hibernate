package util;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure()
                    .addAnnotatedClass(Galaksija.class)
                    .addAnnotatedClass(Misija.class)
                    .addAnnotatedClass(NebeskoTelo.class)
                    .addAnnotatedClass(MorfoloskiTip.class)
                    .addAnnotatedClass(Institucija.class)
                    .addAnnotatedClass(Istrazivac.class)
                    .addAnnotatedClass(KategorijaIstrazivaca.class)
                    .addAnnotatedClass(Uloga.class)
                    .addAnnotatedClass(MisijaIstrazivac.class)
                    .addAnnotatedClass(Sum.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
