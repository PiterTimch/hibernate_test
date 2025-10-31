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
    }

    public void BaseInsert () {
        var sessionFactory = HibernateHelper.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new CategoryEntity("Savage"));
        });
    }
}