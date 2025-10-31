package org.example.interfaces;

import org.example.entities.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryEntity> getCategoryList();
    void CreateCategory(CategoryEntity model);
    void UpdateCategory(CategoryEntity model);
    void DeleteCategory(int id);
}
