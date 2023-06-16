package web.proto.model.enums;

public enum Area {
    TECHNOLOGY("Technology"),
    INVESTMENT("Investment"),
    AGRONOMY("Agronomy"),
    HEALTH("Health"),
    EDUCATION("Education"),
    MARKETING("Marketing");

    private final String label;

    Area(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

