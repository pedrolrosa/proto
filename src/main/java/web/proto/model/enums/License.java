package web.proto.model.enums;

public enum License {
    MIT("MIT"),
    GNU("GNU"),
    EULA("EULA");

    private final String label;

    License(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
