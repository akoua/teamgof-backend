package istic.m2.project.gofback.entities.enums;

public enum RoleType {
    CAVALIER(RoleConstant.CAVALIER),
    ADMIN(RoleConstant.ADMIN);
    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public static class RoleConstant {
        public static final String CAVALIER = "CAVALIER";
        public static final String ADMIN = "ADMIN";
    }

}
