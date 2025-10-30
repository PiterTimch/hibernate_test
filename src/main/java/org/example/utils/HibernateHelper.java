package org.example.utils;

import org.example.entities.CategoryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateHelper {
    private static SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(CategoryEntity.class)
                    .buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("Factory error:" + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
    public static void shutdown() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
