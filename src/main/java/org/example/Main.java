package org.example;

import org.example.entities.CategoryEntity;
import org.example.utils.HibernateHelper;

import javax.smartcardio.Card;

public class Main {
    public static void main(String[] args) {
        //BaseInsert();

        var session = HibernateHelper.getSession();

        try {
            var res = session.createSelectionQuery("from CategoryEntity", CategoryEntity.class).getResultList();
            res.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e);
        }

        // jdbc:postgresql://ep-wild-star-agpl8b2v-pooler.c-2.eu-central-1.aws.neon.tech/neondb?user=neondb_owner&password=npg_5NBwSrDKjdF1
    }

    public void BaseInsert () {
        var sessionFactory = HibernateHelper.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new CategoryEntity("Savage"));
        });
    }
}