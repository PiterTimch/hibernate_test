package org.example;

import org.example.entities.CategoryEntity;
import org.example.services.CategoryService;
import org.example.utils.HibernateHelper;

import javax.smartcardio.Card;

public class Main {
    public static void main(String[] args) {
        CategoryService cs = new CategoryService();

        cs.CreateCategory(new CategoryEntity("Фрукти"));

        CategoryEntity toUpdate = cs.getCategoryList().stream()
                .filter(c -> c.getName().equals("Фрукти"))
                .findFirst()
                .orElse(null);

        if (toUpdate != null) {
            toUpdate.setName("Нова назва");
            cs.UpdateCategory(toUpdate);
        }

    }
}