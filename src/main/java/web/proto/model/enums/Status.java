package web.proto.model.enums;

public enum Status {

	ATIVO("Active"),
	INATIVO("Inactive");
	
	private String descricao;
	
	private Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
