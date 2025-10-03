package org.example.mrdverkin.dataBase.Entitys;

public enum Role {
    ROLE_SELLER("0"),
    ROLE_MAIN_INSTALLER("1"),
    ROLE_ADMIN("2");

    private final String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Role fromName(String name) {
        for (Role role : Role.values()) {
            if (role.name().equals(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role name: " + name);
    }

    public static boolean isValidRole(String role) {
        try {
            fromName(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
