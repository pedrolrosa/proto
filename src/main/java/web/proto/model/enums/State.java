package web.proto.model.enums;

public enum State {
    START("Start"),
    IN_PROGRESS("InProgress"),
    COMPLETE("Complete"),
    IN_WORKING("InWorking"),
    CANCELED("Canceled");

    private final String label;

    State(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

