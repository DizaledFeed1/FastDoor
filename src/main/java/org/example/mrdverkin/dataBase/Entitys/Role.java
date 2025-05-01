package org.example.mrdverkin.dataBase.Entitys;

public enum Role {
    ROLE_SELLER,
    ROLE_MainInstaller,
    ROLE_ADMIN;

    public static boolean isValidRole(String role) {
        try {
            Role.valueOf(role); // Пытаемся преобразовать строку в роль
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
