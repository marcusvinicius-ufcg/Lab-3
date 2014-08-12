package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

/**
 * Classe Representa um Local
 * @author Marcus Vinicius
 *
 */
@Entity
public class Local {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Required
	private String nome;

	@Required
	private String descricao;

	@Required
	private Integer capacidade;

	public Local(String nome, String descricao, Integer capacidade) {
		super();
		isSetNome(nome);
		isSetDescricao(descricao);
		isSetCapacidade(capacidade);
	}

	public Local() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		isSetNome(nome);
	}
	
	private void isSetNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		isSetDescricao(descricao);
	}
	
	private void isSetDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		isSetCapacidade(capacidade);
	}
	
	private void isSetCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || !(obj instanceof Local)) {
			return false;
		}
		
		Local other = (Local) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equalsIgnoreCase(other.descricao)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equalsIgnoreCase(other.nome)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Local: " + nome + ", " + descricao + ", Capacidade: " + capacidade;
	}
	
}
