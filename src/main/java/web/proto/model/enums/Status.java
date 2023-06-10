package web.proto.model.enums;

public enum Status {

	ACTIVE("Active"),
	INACTIVE("Inactive");
	
	private String description;
	
	private Status(String description) {
		this.description = description;
	}
	
	public String getDescricao() {
		return description;
	}
}
