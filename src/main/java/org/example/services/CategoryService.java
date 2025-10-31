package org.example.services;

import org.example.entities.CategoryEntity;
import org.example.interfaces.ICategoryService;
import org.example.utils.HibernateHelper;

import java.util.List;

public class CategoryService implements ICategoryService {

    @Override
    public List<CategoryEntity> getCategoryList() {
        var session = HibernateHelper.getSession();
        try {
            return session.createSelectionQuery("from CategoryEntity", CategoryEntity.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error while fetching categories: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public void CreateCategory(CategoryEntity model) {
        var sessionFactory = HibernateHelper.getSessionFactory();
        try {
            sessionFactory.inTransaction(session -> {
                session.persist(model);
            });
            System.out.println("Category created: " + model.getName());
        } catch (Exception e) {
            System.out.println("Error while creating category: " + e.getMessage());
        }
    }

    @Override
    public void UpdateCategory(CategoryEntity model) {
        var sessionFactory = HibernateHelper.getSessionFactory();
        try {
            sessionFactory.inTransaction(session -> {
                session.merge(model);
            });
            System.out.println("Category updated: " + model.getName());
        } catch (Exception e) {
            System.out.println("Error while updating category: " + e.getMessage());
        }
    }

    @Override
    public void DeleteCategory(int id) {

    }

}
