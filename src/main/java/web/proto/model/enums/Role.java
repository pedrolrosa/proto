package web.proto.model.enums;

public enum Role {
    USER("User"),
    COMPANY("Company"),
    ADM("Admin");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

