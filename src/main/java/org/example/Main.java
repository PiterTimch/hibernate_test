package org.example;

import org.example.entities.CategoryEntity;
import org.example.services.CategoryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final CategoryService cs = new CategoryService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> showCategories();
                case "2" -> addCategory();
                case "3" -> editCategory();
                case "0" -> {
                    System.out.println("Вихід з програми...");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуй ще раз.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n===== Меню категорій =====");
        System.out.println("1 Переглянути всі категорії");
        System.out.println("2 Додати нову категорію");
        System.out.println("3 Редагувати / Видалити категорію");
        System.out.println("0 Вихід");
        System.out.print("Обери дію: ");
    }

    private static void showCategories() {
        List<CategoryEntity> categories = cs.getCategoryList();
        if (categories.isEmpty()) {
            System.out.println("\n(порожньо — ще немає категорій)");
            return;
        }

        System.out.println("\nСписок категорій:");
        System.out.println("+----+----------------------+----------------------+");
        System.out.println("| ID | Назва                | Дата створення       |");
        System.out.println("+----+----------------------+----------------------+");

        for (CategoryEntity c : categories) {
            System.out.printf("| %-2d | %-20s | %-20s |\n",
                    c.getId(),
                    c.getName(),
                    c.getDateCreated() != null ? c.getDateCreated() : "-");
        }

        System.out.println("+----+----------------------+----------------------+\n");
    }

    private static void addCategory() {
        System.out.println("\nДодавання нової категорії");

        String name;
        while (true) {
            System.out.print("Введіть назву (мін. 2 символи)(або 0 для виходу): ");
            name = sc.nextLine().trim();

            if (name.equals("0")) return;

            if (name.length() < 2) {
                System.out.println("Назва занадто коротка!");
                continue;
            }

            final String finalName = name;

            boolean exists = cs.getCategoryList().stream()
                    .anyMatch(c -> c.getName().equalsIgnoreCase(finalName));

            if (exists) {
                System.out.println("Категорія з такою назвою вже існує!");
                continue;
            }

            break;
        }

        cs.CreateCategory(new CategoryEntity(name));
    }

    private static void editCategory() {
        showCategories();

        System.out.print("Введіть ID категорії для редагування (або 0 для виходу): ");
        String idInput = sc.nextLine().trim();
        if (idInput.equals("0")) return;

        int id;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("Невірний ID!");
            return;
        }

        CategoryEntity cat = cs.getCategoryList().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (cat == null) {
            System.out.println("Категорію не знайдено!");
            return;
        }

        System.out.println("\nРедагуємо категорію: " + cat.getName());
        System.out.println("1 Змінити назву");
        System.out.println("2 Видалити");
        System.out.println("0 Назад");
        System.out.print("Обери дію: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1" -> renameCategory(cat);
            case "2" -> deleteCategory(cat);
            default -> System.out.println("Повертаємось у головне меню.");
        }
    }

    private static void renameCategory(CategoryEntity cat) {
        String newName;
        while (true) {
            System.out.print("Введіть нову назву: ");
            newName = sc.nextLine().trim();

            if (newName.length() < 2) {
                System.out.println("Назва занадто коротка!");
                continue;
            }

            final String finalName = newName;

            boolean exists = cs.getCategoryList().stream()
                    .anyMatch(c -> c.getName().equalsIgnoreCase(finalName) && c.getId() != cat.getId());

            if (exists) {
                System.out.println("Категорія з такою назвою вже існує!");
                continue;
            }

            break;
        }

        cat.setName(newName);
        cs.UpdateCategory(cat);
    }

    private static void deleteCategory(CategoryEntity cat) {
        System.out.print("Ви впевнені, що хочете видалити '" + cat.getName() + "'? (y/n): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            cs.DeleteCategory(cat.getId());
        } else {
            System.out.println("Скасовано.");
        }
    }
}
