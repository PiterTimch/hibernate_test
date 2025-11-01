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

                var existing = session.createSelectionQuery(
                                "from CategoryEntity where lower(name) = :name",
                                CategoryEntity.class
                        ).setParameter("name", model.getName().toLowerCase())
                        .getResultList();

                if (!existing.isEmpty()) {
                    System.out.println("Category with name '" + model.getName() + "' already exists.");
                    return;
                }

                session.persist(model);
                System.out.println("Category created: " + model.getName());
            });
        } catch (Exception e) {
            System.out.println("Error while creating category: " + e.getMessage());
        }
    }

    @Override
    public void UpdateCategory(CategoryEntity model) {
        var sessionFactory = HibernateHelper.getSessionFactory();
        try {
            sessionFactory.inTransaction(session -> {

                var existing = session.createSelectionQuery(
                                "from CategoryEntity where lower(name) = :name and id <> :id",
                                CategoryEntity.class
                        ).setParameter("name", model.getName().toLowerCase())
                        .setParameter("id", model.getId())
                        .getResultList();

                if (!existing.isEmpty()) {
                    System.out.println("Cannot update — category with name '" + model.getName() + "' already exists.");
                    return;
                }

                session.merge(model);
                System.out.println("Category updated: " + model.getName());
            });
        } catch (Exception e) {
            System.out.println("Error while updating category: " + e.getMessage());
        }
    }

    @Override
    public void DeleteCategory(int id) {
        var sessionFactory = HibernateHelper.getSessionFactory();
        try {
            sessionFactory.inTransaction(session -> {
                var toDelete = getCategoryList().stream() .filter(c -> c.getId() == id) .findFirst() .orElse(null);

                if (toDelete != null) {
                    session.remove(toDelete);
                    System.out.println("Category deleted with id: " + id);
                } else {
                    System.out.println("Category not found with id: " + id);
                }
            });
        } catch (Exception e) {
            System.out.println("Error while deleting category: " + e.getMessage());
        }
    }
}
