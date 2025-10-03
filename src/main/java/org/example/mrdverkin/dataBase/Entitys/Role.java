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

    public static Role fromCode(String code) {
        for (Role role : Role.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role code: " + code);
    }

    public static boolean isValidRole(String role) {
        try {
            fromCode(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
