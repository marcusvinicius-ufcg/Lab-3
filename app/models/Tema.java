package models;

public enum Tema {

	ARDUINO(0, "Arduino"), DESAFIOS(2,
			"Desafios"), ELETRONICA(4, "Eletrônica"), PROGRAMACAO(1, "Programação"), WEB(3, "Web");

	private final String descricao;

	private final Integer tipo;

	private Tema(Integer tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
